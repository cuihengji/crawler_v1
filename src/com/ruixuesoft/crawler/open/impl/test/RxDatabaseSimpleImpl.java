package com.ruixuesoft.crawler.open.impl.test;


import com.ruixuesoft.crawler.open.impl.RxDatabaseImpl;
import com.web2data.engine.database.ConnectionInfo;

public class RxDatabaseSimpleImpl extends RxDatabaseImpl {

	public RxDatabaseSimpleImpl(String ip, int port, String schema, String username, String password) {
		
		super();
		
		ConnectionInfo conInfo  = new ConnectionInfo();
		conInfo.setIp(ip);
		conInfo.setPort(port);
		conInfo.setSchema(schema);
		conInfo.setUsername(username);
		conInfo.setPassword(password);
		//2018.03.08 数据库支持ORACL后的临时补丁
		conInfo.setDatabaseType("MYSQL");
		this.conInfo = conInfo;
	}
	
	//databaseType ORACLE or MYSQL
	//oracleServiceType SID or SERVICE_NAME
	//oracleServiceValue 例如 ORCL
	public RxDatabaseSimpleImpl(String ip, int port, String schema, String username, String password, String databaseType, String oracleServiceType, String oracleServiceValue) {
		
		super();
		
		ConnectionInfo conInfo  = new ConnectionInfo();
		conInfo.setIp(ip);
		conInfo.setPort(port);
		conInfo.setSchema(schema);
		conInfo.setUsername(username);
		conInfo.setPassword(password);
		conInfo.setDatabaseType(databaseType);
		conInfo.setOracleSericeType(oracleServiceType);
		conInfo.setOracleSericeValue(oracleServiceValue);
		
		this.conInfo = conInfo;
	}
}
