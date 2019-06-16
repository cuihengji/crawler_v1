package com.rkylin.crawler.engine.flood.log;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.model.TimeWrapper;
import com.web2data.engine.database.DBOperation;

public class LogSession {

	private static final transient Logger logger = Logger
			.getLogger(LogSession.class);

	public static void log(int crawlerId, int sessId, String fileName,
			String methodName, int code, String name, TimeWrapper methodExecStartTime, String message,
			String ref1, String ref2, String ref3, String ref4, String ref5,
			Connection con) {

//		String tableName = "log_session_" + sessId;
//
//		long datasMaster = 0;
//		String sql = "SELECT count(1) FROM log_session_master WHERE crawler_host_seq = ? AND session_index = ?";
//		try {
//			
//			datasMaster = DBOperation.find(con, sql, new Object[] { crawlerId, sessId });
//		} catch (SQLException e) {
//			logger.error("log_session_master检索异常!", e);
//		}
//
//		if (datasMaster == 0) {
//
//			sql = "insert into `log_session_master`("
//					+ "`crawler_host_seq`, `session_index`, `table_name`,"
//					+ " `last_transfered_seq`, `create_time`, `update_time`)"
//					+ " values(?, ?, ?, ?, now(),now())";
//			try {
//
//				Object[] params = { crawlerId, sessId, tableName, -1 };
//
//				DBOperation.insert(con, sql, log_session_master.class, params);
//			} catch (SQLException e) {
//				logger.error("log_session_master插入异常!", e);
//			}
//		}
//
//		sql = "create table if not exists %s like %s";
//		sql = String.format(sql, tableName, "log_session_x");
//
//		try {
//			DBOperation.update(con, sql, null);
//		} catch (SQLException e) {
//			logger.error("创建表log_session_x插入异常!", e);
//		}
//
//		sql = "insert into %s ("
//				+ "`create_time`, `file`,"
//				+ "`method`, `code`, `millseconds`, `name`, `ref1`, `ref2`, `ref3`, `ref4`, `ref5`, `comment`)"
//				+ " values(now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//		sql = String.format(sql, tableName);
//		
//		long millseconds = -1;
//		
//		long currentTimeMillis = System.currentTimeMillis();
//		if (methodExecStartTime.getCurrentTime() != -1) {
//			millseconds = currentTimeMillis - methodExecStartTime.getCurrentTime();
//			//把方法执行的最后时间作为下一次计算的开始时间.
//	    	logger.debug("------------ LogSession curent time: " + methodExecStartTime + "　执行时间:　" +millseconds+ " methodName: "+ methodName);
//
//		}
//		    
//		try {
//			Object[] params = { fileName, methodName, code, millseconds, name, 
//					ref1, ref2, ref3, ref4, ref5, message };
//			DBOperation.insert(con, sql, log_session_x.class, params);
//		} catch (SQLException e) {
//			logger.error("插入log_session_x插入异常!", e);
//		}
//		
//		methodExecStartTime.setCurrentTime(System.currentTimeMillis());
	}
	
	
//	public static void main(String[] args) {
//		
//		ConnectionInfo localConectionInfo = new ConnectionInfo();
//		localConectionInfo.setIp("103.235.224.167");
//		localConectionInfo.setSchema("crawler_v2");
//		localConectionInfo.setPort(3306);
//		localConectionInfo.setUsername("root");
//		localConectionInfo.setPassword("mysql0411");
//		Connection localDbCon = null;
//		
//		try {
//			
//			localDbCon = ConnectionManager.openConnection(localConectionInfo);
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		LogSession.log(1, 1, "CrawlerRunner", "run", 601, "name", "message", "ref1", "ref2", "ref3", "ref4", "ref5", localDbCon);
//	}
}
