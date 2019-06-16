package com.ruixuesoft.crawler.open.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxDatabase;
import com.web2data.engine.database.ConnectionInfo;
import com.web2data.engine.database.ConnectionManager;
import com.web2data.system.app.AppFacade;

public class RxDatabaseImpl implements RxDatabase {

	private static final Logger logger = Logger.getLogger(RxDatabaseImpl.class);
	
	protected ConnectionInfo conInfo;
	
	private int userSeq;
	private int appSeq;
	private int scenarioIndex;
	private int ruleIndex;
	
	public RxDatabaseImpl() {
		
	}
	
	public RxDatabaseImpl(ConnectionInfo conInfo) {
		super();
		this.conInfo = conInfo;
	}

	@Override
	public int insert(String sql, Object[] params) throws RxCrawlerException {
		
		checkInterrupted();
		
		Connection connection = getDbConnection(this.conInfo);
		
		long recordSeq = -1;

		String dbType = conInfo.getDatabaseType();
		QueryRunner runner = new QueryRunner();
		try {
			if (dbType.equalsIgnoreCase("ORACLE")) {
				//新的ORACLE QueryRunner 用法
				recordSeq = runner.update(connection, sql, params);
			} else {
				//MySQL还用以前的方法
				recordSeq = runner.insert(connection, sql, new ScalarHandler<Long>(), params);
			}
		} catch (SQLException e) {
			throw new RxCrawlerException(e);
		} catch (Throwable t) {
			if (!(t instanceof NullPointerException)) {
				t.printStackTrace();
				logger.error("插入发生异常:", t);
			}
		} finally {
			
			closeConnection(connection);
			
		}

		return (int)recordSeq;
	}

	
	@Override
	public int[] batchInsert(String sql, Object[][] params) throws RxCrawlerException {

		checkInterrupted();

		Connection connection = getDbConnection(this.conInfo);
		
		int[] cnt = null;

		QueryRunner runner = new QueryRunner();
		try {
			cnt = runner.batch(connection, sql, params);
		} catch (SQLException e) {
			throw new RxCrawlerException(e.getMessage());
		} finally {
			
			closeConnection(connection);
			
		}

		return cnt;
	}
	
	@Override
	public int[] batchUpdate(String sql, Object[][] params) throws RxCrawlerException {

		checkInterrupted();

		Connection connection = getDbConnection(this.conInfo);
		
		int[] cnt = null;

		QueryRunner runner = new QueryRunner();
		try {
			cnt = runner.batch(connection, sql, params);
		} catch (SQLException e) {
			throw new RxCrawlerException(e.getMessage());
		} finally {
			
			closeConnection(connection);
			
		}
		
		return cnt;
	}
	
	
	@Override
	public int delete(String sql, Object[] params) throws RxCrawlerException {
		
		checkInterrupted();
		int cnt = -1;
		Connection connection = getDbConnection(this.conInfo);
		try {
			cnt = upd(connection, sql, params);
		} catch (Exception e) {
			throw new RxCrawlerException(e.getMessage());
		} finally {

			closeConnection(connection);

		}
		
		return cnt;
	}

	@Override
	public int update(String sql, Object[] params) throws RxCrawlerException {
		
		checkInterrupted();

		Connection connection = getDbConnection(this.conInfo);
		int cnt = -1;
		try {
			upd(connection, sql, params);
		} catch (Exception e) {
			throw new RxCrawlerException(e.getMessage());
		} finally {

			closeConnection(connection);

		}
		
		return cnt;
	}
	
	private static int upd(Connection conn, String sql, Object[] params) throws RxCrawlerException {

		int cnt = 0;
		QueryRunner runner = new QueryRunner();
		try {
			cnt = runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RxCrawlerException(e.getMessage());
		}

		return cnt;
	}

	@Override
	public <T> List<T> query(String sql, Class<T> clazz, Object[] params) throws RxCrawlerException {

		checkInterrupted();

		Connection connection = getDbConnection(this.conInfo);
		
		List<T> list = new ArrayList<T> ();

		QueryRunner runner = new QueryRunner();
		List<Map<String, Object>> result = null;
		
		String dbType = conInfo.getDatabaseType();
		try {

			if (dbType.equalsIgnoreCase("ORACLE")) {
				list = (List<T>) runner.query(connection, sql, params, new BeanListHandler<T>(clazz));
			} else {
				result = runner.query(connection, sql, new MapListHandler(), params);

				for (Map<String, Object> map : result) {

					T instance = null;
					try {
						instance = clazz.newInstance();
					} catch (InstantiationException e) {
						logger.error(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage(), e);
					}
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						setField(clazz, instance, entry);
					}
					list.add(instance);
				}
			}
		} catch (SQLException e) {
			throw new RxCrawlerException(e.getMessage());
		} finally {
			closeConnection(connection);
		}
		
		return list;
	}

	private <T> void setField(Class<T> clazz, T instance, Map.Entry<String, Object> entry) throws RxCrawlerException {
	
		Field field = null;
		try {
			
			field = clazz.getDeclaredField( entry.getKey() );
			
		} catch (NoSuchFieldException e) {
			
			String fieldName = convertFieldName( entry.getKey() );
		
			if ( fieldName != null && !fieldName.isEmpty() ) {
			
				try {
					
					field = clazz.getDeclaredField( fieldName );
					
				} catch (NoSuchFieldException e1) {
					
					logger.error(entry.getKey() + "不存在！", e1);
					return;
					
				} catch (SecurityException e1) {
					
					logger.error(e.getMessage(), e1);
					return;
				}
				
			} else {
				
				logger.error(entry.getKey() + "不存在！", e);
				return;
			}
			
		} catch (SecurityException e) {
			
			logger.error(e.getMessage(), e);
			return;
		}

		field.setAccessible(true);
		try {
			field.set(instance, entry.getValue());
		} catch (IllegalArgumentException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new RxCrawlerException(e.getMessage());
			
		} catch (IllegalAccessException e) {
			
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @param fieldName
	 * @return
	 */
	private String convertFieldName( String fieldName ) {
		
		StringBuffer fieldNameBuf = new StringBuffer();
		
		if ( fieldName != null && !fieldName.isEmpty() ) {
			
			String [] fieldNameArr = fieldName.split("_");

			
			if ( fieldNameArr.length > 1 ) {
				
				for (int i = 0; i < fieldNameArr.length; i++ ) {
					
					if ( i == 0) {
						
						fieldNameBuf.append(fieldNameArr[i]);
						
						continue;
						
					} else {
						
						String fieldNameUpper = toUpperCaseFirstOne(fieldNameArr[i]);
						fieldNameBuf.append( fieldNameUpper.length() > 0 ? fieldNameUpper : "" );
						
					}
					
					
				}
			}
			
		}
		
		return fieldNameBuf.toString();
	}

	@Override
	public void createTableByTemplate(String tableName, String templateTableName) throws RxCrawlerException {

		checkInterrupted();

		Connection connection = getDbConnection(this.conInfo);
		
		String sql = "CREATE TABLE IF NOT EXISTS `%s` LIKE %s";
		sql = String.format(sql, tableName, templateTableName);
		try {
			upd(connection, sql, null);
		} catch (Exception e) {
			throw new RxCrawlerException(e.getMessage());
		} finally {

			closeConnection(connection);

		}
	}
	
	private Connection getDbConnection(ConnectionInfo conInfo) throws RxCrawlerException {
		
		Connection con = null;
		try {
//			con = ConnectionManager.openConnection(conInfo);
			con = ConnectionManager.getConnection(conInfo);
		} catch (SQLException e) {
			
			logger.error("getDbConnection SQLException:", e);
			throw new RxCrawlerException(e.getMessage());
			
		} catch (Throwable  t) {
			
			logger.error("getDbConnection Throwable:", t);
			throw new RxCrawlerException(t.getMessage());
		}
		
		return con;
	}
	
	private void closeConnection(Connection con) {
		try {
			if (con != null) {				
				con.close();
				logger.info("db pool connection closed ------------------------------- ");
			}
		} catch (SQLException e) {
			logger.error("closeConnection", e);
		}
	}
	
	private  String toUpperCaseFirstOne(String fieldName) {
        
        StringBuffer a = new StringBuffer();
        if ( fieldName != null && fieldName.length() > 0) {
        
    		char[] ch = fieldName.toCharArray();
            
            for ( int i = 0; i < ch.length; i++ ) {
                
            	if ( i == 0 ) {
                    
            		ch[0] = Character.toUpperCase( ch[0] );
                    
                } else {
                    
                	ch[i] = Character.toLowerCase( ch[i] );
                	
                }
            }
            
            a.append(ch);
            
        }
        
        return a.toString();
    }
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}
	
	

	@Override
	public int saveData(Map<String, String> data, String tableName) {
		
		Object[] params = new Object[data.keySet().size()];
		// 取出所有的key值
		Set<String> set = data.keySet(); 
		int valueIndex = 0;
		
		for (String key : set) {
			params[valueIndex] = data.get(key);
			valueIndex++;
		}

		String sql = generateSQLStatement(data, tableName);

		return insert(sql, params);
	}

	@Override
	public int[] saveBatchData(List<Map<String, String>> dataList, String tableName) {
		
		if(dataList.size()<=0){
			throw new RxCrawlerException("dataList中没有有效数据.");
		}
	
		Map<String, String> firstDataMap = dataList.get(0);
		
		Object[][] params = new Object[dataList.size()][firstDataMap.keySet().size()];
		
		for (int i = 0; i < dataList.size(); i++) {
			
			Map<String, String> dataMap = dataList.get(i);
			int valueIndex = 0;
			// 取出所有的key值
			Set<String> eachRowSet = dataMap.keySet(); 
			for (String key : eachRowSet) {
				params[i][valueIndex] = dataMap.get(key);
				valueIndex++;
			}
		}
		
		String sql = generateSQLStatement(firstDataMap, tableName);
			
		return batchInsert(sql, params);
	}
	
	
	private String generateSQLStatement(Map<String, String> data, String tableName){
		
		StringBuffer tableFieldBuffer = new StringBuffer();
		StringBuffer tableFieldsSQLBuffer = new StringBuffer();
		StringBuffer tableValueSQLBuffer = new StringBuffer();
		
		// 取出所有的key值
		Set<String> set = data.keySet(); 
		
		for (String key : set) {
			tableFieldBuffer.append(key).append("|");
			tableFieldsSQLBuffer.append(key).append(",");
			tableValueSQLBuffer.append("?,");
		}

		String tableFieldsString = tableFieldBuffer.toString();
		tableFieldsString = tableFieldsString.substring(0, (tableFieldsString.length() - 1));
		
		String tableFieldsSQL = tableFieldsSQLBuffer.toString();
		tableFieldsSQL = tableFieldsSQL.substring(0, (tableFieldsSQL.length() - 1));
		
		String tableValueSQL = tableValueSQLBuffer.toString();
		tableValueSQL = tableValueSQL.substring(0, (tableValueSQL.length() - 1));
		
		String sql = "";
		try {
			String tableNameInDB = AppFacade.getInstance().createAppDataTable(this.getUserSeq(), 
													   						  this.getAppSeq(), 
													   						  this.getScenarioIndex(), 
													   						  this.getRuleIndex(), 
													   						  tableName,
													   						  tableFieldsString);
			//String sql = "insert into BS_QUERY_RESULT(APP_SEQ,TASK_SEQ,RULE_INDEX,C_DPT_NAME,C_PACKAGE_NAME,C_ITEM_NAME,N_BAO_FEI_YUAN) VALUES (399,?,1,'太平洋保险',?,?,?)";
			sql = "insert into "+ tableNameInDB+"( "+ tableFieldsSQL + " ) VALUES ( "+ tableValueSQL + " )" ;
			
		} catch (Exception e) {
			logger.error("createAppDataTable exception: ", e);
			throw new RxCrawlerException("不能够自动创建数据库表, 请联系瑞雪采集云技术支持");
		}	
		return sql;
	}
	

	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public int getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(int appSeq) {
		this.appSeq = appSeq;
	}

	public int getScenarioIndex() {
		return scenarioIndex;
	}

	public void setScenarioIndex(int scenarioIndex) {
		this.scenarioIndex = scenarioIndex;
	}

	public int getRuleIndex() {
		return ruleIndex;
	}

	public void setRuleIndex(int ruleIndex) {
		this.ruleIndex = ruleIndex;
	}
	
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		ConnectionInfo conInfo = new ConnectionInfo();
		conInfo.setIp("59.110.10.13");
		conInfo.setPort(3306);
		conInfo.setSchema("user17_data");
		conInfo.setUsername("root");
		conInfo.setPassword("mysql0411");
		
		RxDatabaseImpl rxdb = new RxDatabaseImpl(conInfo);
		for (int i = 1; i <= 1000 ; i++) {
			
			   String sql = "insert into test(company_name) values(?)";
			   String companyName = "大连瑞雪科技" + i;
			   System.out.println(companyName);
			   Object[] hotelTypeRoomParams = new Object[]{companyName};
               try {
			   Integer compayId = rxdb.insert(sql, hotelTypeRoomParams);
               } catch (Exception e) {
            	   e.printStackTrace();
               }
		}
		
		long endTime = System.currentTimeMillis();
		
		long times = endTime - startTime;
		System.out.println("times:" + times); // times:392935 连接池
		                                      // times:370048不用连接池
	}
	
}
