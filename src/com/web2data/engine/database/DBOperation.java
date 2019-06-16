package com.web2data.engine.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

public class DBOperation {

	private static final transient Logger logger = Logger.getLogger(DBOperation.class);

	public static void createTable(Connection conn, String tableName, String templateTableName) throws SQLException {

		String sql = "CREATE TABLE IF NOT EXISTS `%s` LIKE %s";
		sql = String.format(sql, tableName, templateTableName);
		upd(conn, sql, null);
	}

	public static <T> T find(Connection conn, String sql, Class<T> clazz, Object[] params) throws SQLException {
		
		logger.info(conn.getClass());
		QueryRunner run = new QueryRunner();
		try {
			return run.query(conn, sql, new BeanHandler<T>(clazz), params);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static <T> List<T> query(Connection conn, String sql, Class<T> clazz, Object[] params) throws SQLException {

		List<T> list;

		QueryRunner run = new QueryRunner();
		try {
			list = run.query(conn, sql, new BeanListHandler<T>(clazz), params);
		} catch (SQLException e) {
			throw e;
		}

		return list;
	}

	public static Long insert(Connection conn, String sql, Object[] params) throws SQLException {

		Long seq = -1L;

		QueryRunner runner = new QueryRunner();
		try {
			seq = runner.insert(conn, sql, new ScalarHandler<Long>(), params);
		} catch (SQLException e) {
			throw e;
		}

		return seq;
	}

	public static int delete(Connection conn, String sql, Object[] params) throws SQLException {

		return upd(conn, sql, params);
	}

	
	public static int update(Connection conn, String sql, Object[] params) throws SQLException {

		return upd(conn, sql, params);
	}

	public static int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {

		int[] cnt = null;

		QueryRunner runner = new QueryRunner();
		try {
			cnt = runner.batch(conn, sql, params);
		} catch (SQLException e) {
			throw e;
		}

		return cnt;
	}

	private static int upd(Connection conn, String sql, Object[] params) throws SQLException {

		int cnt = 0;

		QueryRunner runner = new QueryRunner();
		try {
			cnt = runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw e;
		}

		return cnt;
	}

	public static <T> void insert(Connection conn, String sql, Class<T> clazz, Object[] params) throws SQLException {

		ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
		QueryRunner runner = new QueryRunner();
		try {
			runner.insert(conn, sql, handler, params);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static long find(Connection conn, String sql, Object[] params) throws SQLException {

		QueryRunner run = new QueryRunner();
		Long count = -1L;
		try {
			count = run.query(conn, sql, new ScalarHandler<Long>(), params);
			return count;
		} catch (SQLException e) {
			throw e;
		}
	}
}
