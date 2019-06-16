package com.rkylin.crawler.engine.flood.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect implements Serializable {

	private static final long serialVersionUID = 1L;

	private String driver = "com.mysql.jdbc.Driver";
	
	//TaskHost List:
	//测试数据库
//	private String url = "jdbc:mysql://106.2.3.52";
	//生产数据库
//	private String url = "jdbc:mysql://103.249.252.21";
	private String url = "jdbc:mysql://106.2.1.39";
//	private String url = "jdbc:mysql://103.235.222.216";
	
	private String user = "root";
//	private String password = "mysql0411";
	//106.2.1.39
	private String password = "HXlEVWBWg0zd5fXbrRuPA";
	private Connection connection = null;
	private ResultSet resultSet = null;

	public DBConnect() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ResultSet querySql(String sql) {
		try {
			this.connection = DriverManager.getConnection(url, user, password);
			this.resultSet = this.connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.resultSet;
	}

	public int updataSql(String sql) {
		int result = -1;
		try {
			this.connection = DriverManager.getConnection(url, user, password);
			result = this.connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
