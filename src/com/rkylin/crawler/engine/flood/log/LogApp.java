package com.rkylin.crawler.engine.flood.log;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.web2data.engine.database.ConnectionInfo;
import com.web2data.engine.database.ConnectionManager;
import com.web2data.engine.database.DBOperation;
import com.web2data.system.config.entity.log_rule_master;
import com.web2data.system.config.entity.log_session_x;

public class LogApp {

	private static final transient Logger logger = Logger.getLogger(LogApp.class);

	public static void log(int crawlerId, int sessId, int appSeq, int scenarioIndex, int ruleIndex, int account_index, int app_task_seq, String fileName,
			String methodName, int code, String name, String message,
			String ref1, String ref2, String ref3, String ref4, String ref5,
			Connection con) {

		long datasMaster = 0;
		String sql = "SELECT count(1) FROM log_rule_master WHERE app_seq = ? AND scenario_index = ? AND rule_index = ?";
		try {
			
			datasMaster = DBOperation.find(con, sql, new Object[] { appSeq, scenarioIndex, ruleIndex});
		} catch (SQLException e) {
			logger.error("log_rule_master检索异常!", e);
		}

		String tableName = "log_rule_" + appSeq + "_" + scenarioIndex + "_" + ruleIndex;
		
		if (datasMaster == 0) {

			sql = "insert into `log_rule_master`("
					+ "`crawler_host_seq`, `app_seq`, `scenario_index`, `rule_index`, `table_name`,"
					+ " `last_transfered_seq`, `create_time`, `update_time`)"
					+ " values(?, ?, ?, ?, ?, ?, now(),now())";
			try {

				Object[] params = { crawlerId, appSeq, scenarioIndex, ruleIndex, tableName, -1 };

				DBOperation.insert(con, sql, log_rule_master.class, params);
			} catch (SQLException e) {
				logger.error("log_session_master插入异常!", e);
			}
		}

		sql = "create table if not exists %s like %s";
		sql = String.format(sql, tableName, "log_rule_x_y_z");

		try {
			DBOperation.update(con, sql, null);
		} catch (SQLException e) {
			logger.error("创建表log_session_x插入异常!", e);
		}

		sql = "insert into %s ("
				+ "`session_index`, `account_index`,  `app_task_seq`, `create_time`, `file`,"
				+ "`method`, `code`, `name`, `ref1`, `ref2`, `ref3`, `ref4`, `ref5`, `comment`)"
				+ " values(?, ?, ?, now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		sql = String.format(sql, tableName);

		try {
			Object[] params = { sessId, account_index, app_task_seq, fileName, methodName, code, name, 
					ref1, ref2, ref3, ref4, ref5, message };
			DBOperation.insert(con, sql, log_session_x.class, params);
		} catch (SQLException e) {
			logger.error("插入log_rule_x_y_z插入异常!", e);
		}
	}
	
	
	public static void main(String[] args) {
		
		ConnectionInfo localConectionInfo = new ConnectionInfo();
		localConectionInfo.setIp("103.235.224.167");
		localConectionInfo.setSchema("crawler_v2");
		localConectionInfo.setPort(3306);
		localConectionInfo.setUsername("root");
		localConectionInfo.setPassword("mysql0411");
		Connection localDbCon = null;
		
		try {
			
			localDbCon = ConnectionManager.openConnection(localConectionInfo);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        log(1, 1, 45, 1, 1, -1, 100, "fileName1", "methodName1", 100, "name"," message", "ref1", "ref2", "ref3", "ref4", "ref5", localDbCon);
		
	}
}
