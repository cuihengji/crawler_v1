package com.rkylin.crawler.engine.flood.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DBScriptGenerator {

	public static void main(String[] args) {

		DBScriptGenerator.testQuerySql();
	}
	
	private static boolean schemaTest(String schema) {
		String pattern = "user(\\d+)";
		//Pattern r = Pattern.compile(pattern);
		
		return Pattern.matches(pattern, schema);
	}
	
	private static boolean tableName(String name) {
//		String pattern = "app(\\d+)_(.*)_task";
		String pattern = "app_data_host";
		return Pattern.matches(pattern, name);
	}
	
	public static void testQuerySql() {

		String schemaListSql = "SELECT `SCHEMA_NAME` FROM `information_schema`.`SCHEMATA`";
		DBConnect schemaConnection = new DBConnect();
		ResultSet schemaResultSet =  schemaConnection.querySql(schemaListSql);
		List<String> schemaList = new ArrayList<>();
		List<String>tableList = new ArrayList<>();
		try {
			while(schemaResultSet.next()) {
				String schemaName = schemaResultSet.getString("SCHEMA_NAME");
				
				if(schemaTest(schemaName)) {
					schemaList.add(schemaName);
					//System.out.println("schemaName->"+schemaName);
				}
			}
			schemaConnection.close();
			
			String tableName =" select table_name from information_schema.tables where table_schema=";
			for(String schemaName:schemaList) {
				String newTableNameSql = tableName +"'"+schemaName +"'";
				DBConnect connect = new DBConnect();
				ResultSet rs = connect.querySql(newTableNameSql);
				while(rs.next()) {
					String taskTableName = rs.getString("table_name");
					//System.out.println("tableName->"+name);
					if(tableName(taskTableName)) {
						tableList.add(schemaName+"."+taskTableName);
						System.out.println("alter table "+schemaName+"."+taskTableName+" "
								+ "add column database_type varchar(20) default 'MYSQL', "
								+ "add column oracle_service_type varchar(20), "
								+ "add column oracle_service_value varchar(100); ");
					}
				}
				connect.close();
			}
			
//			for (String schemaOutput : schemaList) {
//				System.out.println("alter table "+schemaOutput+".rule_source_code" + " add column script_language char(10) default 'JAVA';");
//			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
