package com.web2data.engine.database;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.web2data._global.SessionThreadLocal;
import com.web2data.engine.core.SessionManager;

public class DatabaseInfra {

	private DatabaseInfra() {}
	
	
	public static DatabaseInfra newInstance(int sessionType, int sessionIndex) {
		return new DatabaseInfra();
	}
	
	
	public static DatabaseInfra getInstance() {
		//return new DatabaseInfra();
		
		DatabaseInfra result = SessionManager.getTheSession(SessionThreadLocal.getSessionType(), 
				SessionThreadLocal.getSessionIndex())._DatabaseInfra;
		if ( result == null ) {
			result = new DatabaseInfra();
		}
		return result;
	}
	
	// 释放资源
	public void releaseDatabaseResource() throws Exception {
		if ( _getDefaultDatabaseConnection != null && !_getDefaultDatabaseConnection.isClosed() )
			_getDefaultDatabaseConnection.close();
		_getDefaultDatabaseConnection = null;
		
		if ( _getDefaultMySQLDatabaseConnection != null && !_getDefaultMySQLDatabaseConnection.isClosed() )
			_getDefaultMySQLDatabaseConnection.close();
		_getDefaultMySQLDatabaseConnection = null;
		
		if ( _getDefaultDatabaseQueryRunner != null )
			DbUtils.closeQuietly( _getDefaultDatabaseQueryRunner.getDataSource().getConnection() );
		_getDefaultDatabaseQueryRunner = null;
		
		if ( _getDefaultMySQLDatabaseQueryRunner != null )
			DbUtils.closeQuietly( _getDefaultMySQLDatabaseQueryRunner.getDataSource().getConnection() );
		_getDefaultMySQLDatabaseQueryRunner = null;		

	}
	
	// java.sql.Connection
	
	private java.sql.Connection _getDefaultDatabaseConnection = null;
	
	//  RDB ( DefaultMySQL > DefaultOracle > DefaultMicrosoftSQLServer )
	public java.sql.Connection getDefaultDatabaseConnection() throws Exception {
		if ( _getDefaultDatabaseConnection == null || _getDefaultDatabaseConnection.isClosed() )
			_getDefaultDatabaseConnection 
				= ConnectionManager.openConnection("106.2.13.227", 3306, "root", "mysql0411", "huahai");
		return _getDefaultDatabaseConnection;
	}
	
	private java.sql.Connection _getDefaultMySQLDatabaseConnection = null;
	
	public java.sql.Connection getDefaultMySQLDatabaseConnection() throws Exception {
		if ( _getDefaultMySQLDatabaseConnection == null || _getDefaultMySQLDatabaseConnection.isClosed() )
			_getDefaultMySQLDatabaseConnection 
				= ConnectionManager.openConnection("106.2.13.227", 3306, "root", "mysql0411", "huahai");
		return _getDefaultMySQLDatabaseConnection;
	}
	
	
	public java.sql.Connection getMySQLDatabaseConnectionByIndex( int i ) {
		return null;
	}
	
	public java.sql.Connection getDefaultOracleDatabaseConnection() {
		return null;
	}
	public java.sql.Connection getOracleDatabaseConnectionByIndex( int i ) {
		return null;
	}
	
	public java.sql.Connection getDefaultMicrosoftSQLServerDatabaseConnection() {
		return null;
	}
	public java.sql.Connection getMicrosoftSQLServerDatabaseConnectionByIndex( int i ) {
		return null;
	}
	
	
	//
	private org.apache.commons.dbutils.QueryRunner _getDefaultDatabaseQueryRunner = null;
	
	// org.apache.commons.dbutils.QueryRunner
	public org.apache.commons.dbutils.QueryRunner getDefaultDatabaseQueryRunner() throws Exception {
		
		if ( _getDefaultDatabaseQueryRunner != null )
			return _getDefaultDatabaseQueryRunner;
		
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setIp("106.2.13.227");
		connectionInfo.setPort(3306);
		connectionInfo.setUsername("root");
		connectionInfo.setPassword("mysql0411");
		connectionInfo.setSchema("huahai");
		connectionInfo.setDatabaseType("MYSQL");
		
		DataSource dataSource = ConnectionManager.getDataSource2(connectionInfo);

		_getDefaultDatabaseQueryRunner = new QueryRunner(dataSource);

		return _getDefaultDatabaseQueryRunner;
	}
	
	
	//
	private org.apache.commons.dbutils.QueryRunner _getDefaultMySQLDatabaseQueryRunner = null;
	
	public org.apache.commons.dbutils.QueryRunner getDefaultMySQLDatabaseQueryRunner() throws Exception {
		if ( _getDefaultMySQLDatabaseQueryRunner != null )
			return _getDefaultMySQLDatabaseQueryRunner;
		
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setIp("106.2.13.227");
		connectionInfo.setPort(3306);
		connectionInfo.setUsername("root");
		connectionInfo.setPassword("mysql0411");
		connectionInfo.setSchema("huahai");
		connectionInfo.setDatabaseType("MYSQL");
		
		DataSource dataSource = ConnectionManager.getDataSource2(connectionInfo);

		_getDefaultMySQLDatabaseQueryRunner = new QueryRunner(dataSource);

		return _getDefaultMySQLDatabaseQueryRunner;
	}
	
	public org.apache.commons.dbutils.QueryRunner getMySQLDatabaseQueryRunnerByIndex( int i ) {
		return null;
	}
	
	public org.apache.commons.dbutils.QueryRunner getDefaultOracleDatabaseQueryRunner() {
		return null;
	}
	public org.apache.commons.dbutils.QueryRunner getOracleDatabaseQueryRunnerByIndex( int i ) {
		return null;
	}
	
	public org.apache.commons.dbutils.QueryRunner getDefaultMicrosoftSQLServerDatabaseQueryRunner() {
		return null;
	}
	public org.apache.commons.dbutils.QueryRunner getMicrosoftSQLServerDatabaseQueryRunnerByIndex( int i ) {
		return null;
	}
	
	// MongoDatabase
//	public com.mongodb.client.MongoDatabase getDefaultMongoDatabaseConnection() {
//		return null;
//	}
//	public com.mongodb.client.MongoDatabase getMongoDatabaseConnectionByIndex( int i ) {
//		return null;
//	}
	
}
