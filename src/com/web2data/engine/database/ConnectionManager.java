package com.web2data.engine.database;

//import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.web2data.system.config.entity.app_data_host;

public class ConnectionManager {
	
	private static final transient Logger logger = Logger.getLogger(ConnectionManager.class);
	
//    private static ConcurrentHashMap<String, ComboPooledDataSource> dataSourcePoolMap = new ConcurrentHashMap<String, ComboPooledDataSource>();
    
    private static ConcurrentHashMap<String, BasicDataSource> dataSourcePoolMap = new ConcurrentHashMap<String, BasicDataSource>();

	public static Connection openConnection(String ip, int port,String userName, String password, String schema)throws ClassNotFoundException, SQLException {
		
		String mysqlUrl = buildMySQLJdbcUrl(ip, port, userName, password, schema);
		Class.forName("com.mysql.jdbc.Driver");
		
		Properties properties = new Properties();
		properties.put("connectTimeout", "300000");
		
		return DriverManager.getConnection(mysqlUrl,properties);
	}

	public static Connection openConnection(ConnectionInfo info) throws ClassNotFoundException, SQLException {

		String url = buildMySQLJdbcUrl(info.getIp(), 
								  info.getPort(),
								  info.getUsername(), 
								  info.getPassword(), 
								  info.getSchema());
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Properties properties = new Properties();
		properties.put("connectTimeout", "3600000");
		
		return DriverManager.getConnection(url,properties);
	}
	
	
//	public static Connection getConnection( ConnectionInfo info) throws ClassNotFoundException, SQLException {
//		
//		Connection connection = null;
//        String key = info.getIp() + "_" + info.getPort() + "_" + info.getSchema();
//        
//		if ( dataSourcePoolMap.containsKey( key ) ) {
//
//			ComboPooledDataSource pooledDS = dataSourcePoolMap.get( info.getIp() + "_" + info.getPort() + "_" + info.getSchema() );
//			
//			connection = pooledDS.getConnection();
//			
//			logger.info("!!!!!!!!!当前datasource状态!!!!!!!!!!!!");
//			
//			logger.info("当前datasource状态-NumBusyConnections:" + pooledDS.getNumBusyConnections());
//			logger.info("当前datasource状态-NumConnections:" + pooledDS.getNumConnections());
//			logger.info("当前datasource状态-NumIdleConnections:" + pooledDS.getNumIdleConnections());
//			logger.info("当前datasource状态-NumUnclosedOrphanedConnections:" + pooledDS.getNumUnclosedOrphanedConnections());
//			
//		} else {
//			
//			logger.info("!!!获取datasource!!!");
//
//			ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//			try {
//				
//				dataSource.setDriverClass("com.mysql.jdbc.Driver");
//				
//			} catch (PropertyVetoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			dataSource.setJdbcUrl( buildJdbcUrl( info.getIp(), info.getPort(), info.getUsername(), info.getPassword(), info.getSchema()) );
//			dataSource.setUser( info.getUsername() );
//			dataSource.setPassword( info.getPassword() );
//			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 2
//			// initialPoolSize
//			dataSource.setInitialPoolSize( 0 );
//			// 连接池中保留的最大连接数。Default: 8 maxPoolSize
//			dataSource.setMaxPoolSize( 4 );
//			// 连接池中保留的最小连接数。
//			dataSource.setMinPoolSize( 0 );
//			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 acquireIncrement
//			dataSource.setAcquireIncrement( 1 );
//			// 每60秒检查所有连接池中的空闲连接。Default: 0 idleConnectionTestPeriod
//			dataSource.setIdleConnectionTestPeriod( 60 );
//			// 最大空闲时间,25000秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 maxIdleTime
//			dataSource.setMaxIdleTime( 30*60 );
//			// 连接关闭时默认将所有未提交的操作回滚。Default: false autoCommitOnClose
//			dataSource.setAutoCommitOnClose( false );
//			//定义在从数据库获取新连接失败后重复尝试的次数。Default: 30  acquireRetryAttempts
//			dataSource.setAcquireRetryAttempts(30);
//	        //两次连接中间隔时间，单位毫秒。Default: 1000 acquireRetryDelay
//			dataSource.setAcquireRetryDelay(1000);
//			// 如果设置为true,每次从池中取一个连接，将做一下测试，使用automaticTestTable 或者 preferredTestQuery,做一条查询语句.看看连接好不好用，不好用，就关闭它，重新从池中拿一个.
//			dataSource.setTestConnectionOnCheckout(true);
//			connection = dataSource.getConnection();
//
//			dataSourcePoolMap.put( key, dataSource);
//		}
//
//		return connection;
//	}
	
	
	/** 根据数据库配置信息从数据库连接池获取连接
	 * @param info
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection( ConnectionInfo info) throws SQLException {
			
        BasicDataSource dataSource = getDataSource(info);

		return 	dataSource.getConnection();
	}

	/** 获取数据库DataSource(dbcp）
	 * @param info
	 * @return BasicDataSource
	 * @throws SQLException
	 */
	private synchronized static BasicDataSource getDataSource(ConnectionInfo info) throws SQLException {
		
		String key = info.getIp() + "_" + info.getPort() + "_" + info.getSchema();
		
		if (info.getDatabaseType().equalsIgnoreCase("ORACLE")) {
			key = info.getIp() + "_" + info.getPort() + "_" + info.getOracleSericeValue();
		}
		
		BasicDataSource  dataSource = null;
		
		if ( dataSourcePoolMap.containsKey( key ) ) {
	
			dataSource = dataSourcePoolMap.get( key );
			
			logger.info("!!!!!!!!!!!!当前datasource状态!!!!!!!!!!!!");
			logger.info("当前datasource状态-NumActive:" + dataSource.getNumActive());
			logger.info("当前datasource状态-NumIdle:" + dataSource.getNumIdle());
			
		} else {
			
			logger.info("!!!!!!!!!!!!获取datasource!!!!!!!!!!!!");
	
			dataSource = new BasicDataSource();
			
			if (info.getDatabaseType().equalsIgnoreCase("ORACLE")) {
				dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
				
				String oracleUrl = buildOracleJdbcUrl( info.getIp(), info.getPort(), info.getOracleSericeType(), info.getOracleSericeValue());
				
				logger.info("oracleUrl:" + oracleUrl);

				dataSource.setUrl(oracleUrl);

			} else {
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl( buildMySQLJdbcUrl( info.getIp(), info.getPort(), info.getUsername(), info.getPassword(), info.getSchema()) );
			}
			dataSource.setUsername( info.getUsername() );
			dataSource.setPassword( info.getPassword() );
			logger.info("setUsername:" + info.getUsername() );
			logger.info("setPassword:" + info.getPassword() );
			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 2
			// 初始化连接:连接池启动时创建的初始化连接数量 Default: 0
			dataSource.setInitialSize( 1 );
			// 最大活动连接:连接池在同一时间能够分配的最大活动连接的数量, 如果设置为非正数则表示不限制。Default: 8 maxPoolSize
			dataSource.setMaxTotal( 4 );
			// 最小空闲连接:连接池中容许保持空闲状态的最小连接数量,负数表示没有
			dataSource.setMinIdle( 0 );
			// 最大空闲连接:连接池中容许保持空闲状态的最大连接数量,超过的空闲连接将被释放,如果设置为负数表示不限制(
			dataSource.setMaxIdle( 2 );
			// 从连接池获取一个连接时，最大的等待时间，设置为-1时，如果没有可用连接，连接池会一直无限期等待，直到获取到连接为止。如果设置为N（毫秒），则连接池会等待N毫秒，等待不到，则抛出异常。
			dataSource.setMaxWaitMillis( 5 * 60 * 1000 );
			
			// 空闲的连接最低要待N毫秒后，才会被释放
			dataSource.setMinEvictableIdleTimeMillis( 30 * 60 * 1000 );
	//			// 每30秒运行一次空闲连接回收器
	//			dataSource.setTimeBetweenEvictionRunsMillis( 30 * 1000 );
	//			// 在每次空闲连接回收器线程(如果有)运行时检查的连接数量，默认值就是3.
	//			dataSource.setNumTestsPerEvictionRun(3);
			
			dataSource.setRollbackOnReturn(false);
			
	//			// 从连接池获取一个连接时，验证有效性,指明在从池中租借对象时是否要进行验证有效，如果对象验证失败，则对象将从池子释放，然后我们将尝试租借另一个
	//			dataSource.setTestOnBorrow( false ); // 借出连接时不要测试，否则很影响性能
	//			// 连接空闲时，验证有效性,指明对象是否需要通过对象驱逐者进行校验（如果有的话），假如一个对象验证失败，则对象将被从池中释放。
	//			dataSource.setTestWhileIdle( true );
	//			// 验证连接是否可用，使用的SQL语句
	//			dataSource.setValidationQuery("SELECT 1");
			
			// 超过时间限制，回收没有用(废弃)的连接（默认为 300秒，调整为180）
			dataSource.setRemoveAbandonedTimeout( 1 * 60 );
			// 标记是否删除超过removeAbandonedTimout所指定时间的被遗弃的连接。
			dataSource.setRemoveAbandonedOnBorrow(true);
			dataSource.setRemoveAbandonedOnMaintenance(true);
			
			dataSourcePoolMap.put( key, dataSource);
		}
		
		return dataSource;
	}

	
	public static BasicDataSource getDataSource2(ConnectionInfo info) throws SQLException {
		
		String key = info.getIp() + "_" + info.getPort() + "_" + info.getSchema();
		
		if (info.getDatabaseType().equalsIgnoreCase("ORACLE")) {
			key = info.getIp() + "_" + info.getPort() + "_" + info.getOracleSericeValue();
		}
		
		BasicDataSource  dataSource = null;
		
		if ( dataSourcePoolMap.containsKey( key ) ) {
	
			dataSource = dataSourcePoolMap.get( key );
			
			logger.info("!!!!!!!!!!!!当前datasource状态!!!!!!!!!!!!");
			logger.info("当前datasource状态-NumActive:" + dataSource.getNumActive());
			logger.info("当前datasource状态-NumIdle:" + dataSource.getNumIdle());
			
		} else {
			
			logger.info("!!!!!!!!!!!!获取datasource!!!!!!!!!!!!");
	
			dataSource = new BasicDataSource();
			
			if (info.getDatabaseType().equalsIgnoreCase("ORACLE")) {
				dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
				
				String oracleUrl = buildOracleJdbcUrl( info.getIp(), info.getPort(), info.getOracleSericeType(), info.getOracleSericeValue());
				
				logger.info("oracleUrl:" + oracleUrl);

				dataSource.setUrl(oracleUrl);

			} else {
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl( buildMySQLJdbcUrl( info.getIp(), info.getPort(), info.getUsername(), info.getPassword(), info.getSchema()) );
			}
			dataSource.setUsername( info.getUsername() );
			dataSource.setPassword( info.getPassword() );
			logger.info("setUsername:" + info.getUsername() );
			logger.info("setPassword:" + info.getPassword() );
			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 2
			// 初始化连接:连接池启动时创建的初始化连接数量 Default: 0
			dataSource.setInitialSize( 1 );
			// 最大活动连接:连接池在同一时间能够分配的最大活动连接的数量, 如果设置为非正数则表示不限制。Default: 8 maxPoolSize
			dataSource.setMaxTotal( 1 );
			// 最小空闲连接:连接池中容许保持空闲状态的最小连接数量,负数表示没有
			dataSource.setMinIdle( 0 );
			// 最大空闲连接:连接池中容许保持空闲状态的最大连接数量,超过的空闲连接将被释放,如果设置为负数表示不限制(
			dataSource.setMaxIdle( 1 );
			// 从连接池获取一个连接时，最大的等待时间，设置为-1时，如果没有可用连接，连接池会一直无限期等待，直到获取到连接为止。如果设置为N（毫秒），则连接池会等待N毫秒，等待不到，则抛出异常。
			dataSource.setMaxWaitMillis( 5 * 60 * 1000 );
			
			// 空闲的连接最低要待N毫秒后，才会被释放
			dataSource.setMinEvictableIdleTimeMillis( 30 * 60 * 1000 );
	//			// 每30秒运行一次空闲连接回收器
	//			dataSource.setTimeBetweenEvictionRunsMillis( 30 * 1000 );
	//			// 在每次空闲连接回收器线程(如果有)运行时检查的连接数量，默认值就是3.
	//			dataSource.setNumTestsPerEvictionRun(3);
			
			dataSource.setRollbackOnReturn(false);
			
	//			// 从连接池获取一个连接时，验证有效性,指明在从池中租借对象时是否要进行验证有效，如果对象验证失败，则对象将从池子释放，然后我们将尝试租借另一个
	//			dataSource.setTestOnBorrow( false ); // 借出连接时不要测试，否则很影响性能
	//			// 连接空闲时，验证有效性,指明对象是否需要通过对象驱逐者进行校验（如果有的话），假如一个对象验证失败，则对象将被从池中释放。
	//			dataSource.setTestWhileIdle( true );
	//			// 验证连接是否可用，使用的SQL语句
	//			dataSource.setValidationQuery("SELECT 1");
			
			// 超过时间限制，回收没有用(废弃)的连接（默认为 300秒，调整为180）
			dataSource.setRemoveAbandonedTimeout( 1 * 60 );
			// 标记是否删除超过removeAbandonedTimout所指定时间的被遗弃的连接。
			dataSource.setRemoveAbandonedOnBorrow(true);
			dataSource.setRemoveAbandonedOnMaintenance(true);
		}
		
		return dataSource;
	}
	
	public static void closeConnection(Connection conn) throws SQLException {
		DbUtils.close(conn);
	}

	public static void rollback(Connection conn) throws SQLException {
		DbUtils.rollback(conn);
	}

	private static String buildMySQLJdbcUrl(String ip, int port, String userName,
			String password, String schema) {
		String url = "jdbc:mysql://{1}:{2}/{3}?user={4}&password={5}&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
		
		return StringUtils.replaceEach(
				url, 
				new String[]{"{1}", "{2}", "{3}", "{4}", "{5}"}, 
				new String[]{ip, String.valueOf(port), schema, userName, password});
	}
	
	private static String buildOracleJdbcUrl(String ip, int port, String oracleServiceType, String oracleServiceValue) {
		// SERVICE_NAME or SID
		String url = "jdbc:oracle:thin:@//{1}:{2}/{3}";
		//jdbc:oracle:thin:scott/tiger@sean-m700:1521:ora92
		if (oracleServiceType.equalsIgnoreCase("SID")) {
			url = "jdbc:oracle:thin:@{1}:{2}:{3}";
		}
		
		return StringUtils.replaceEach(
				url, 
				new String[]{"{1}", "{2}", "{3}"}, 
				new String[]{ip, String.valueOf(port), oracleServiceValue});
	}
	
	public static ConnectionInfo getConnectionInfo(app_data_host app_data_host) {
		
		ConnectionInfo connectioninfo = new ConnectionInfo();
		
		connectioninfo.setIp(app_data_host.getIp_from_crawler_host());
		connectioninfo.setPort(app_data_host.getMysql_port());
		connectioninfo.setSchema(app_data_host.getMysql_schema());
		connectioninfo.setUsername(app_data_host.getMysql_username());
		connectioninfo.setPassword(app_data_host.getMysql_password());
		connectioninfo.setDatabaseType(app_data_host.getDatabase_type());
		connectioninfo.setOracleSericeType(app_data_host.getOracle_service_type());
		connectioninfo.setOracleSericeValue(app_data_host.getOracle_service_value());
		
		return connectioninfo;
	}
}
