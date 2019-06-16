package com.web2data.open;

import java.util.List;
import java.util.Map;

import com.ruixuesoft.crawler.open.RxCrawlerException;

public interface RxDatabase {

	//
	/**
     * 根据参数插入数据
     * @param sql
     * @param params
     * @return 插入数据生成的自定义id, 如果没有自增主键返回-1
     * @exception RxCrawlerException
     */
	public int insert(String sql, Object[] params) throws RxCrawlerException;

	/**
     * 根据参数批量插入数据
     * @param sql
     * @param params
     * @return 插入数据生成的自定义id, 如果没有自增主键返回-1
     * @exception RxCrawlerException
     */
	public int[] batchInsert(String sql, Object[][] params) throws RxCrawlerException;
	
	
	/**
     * 根据参数批量更新数据
     * @param sql
     * @param params
     * @return 更新数据条数
     * @exception RxCrawlerException
     */
	public int[] batchUpdate(String sql, Object[][] params) throws RxCrawlerException;
	
	
	/**
     * 根据参数删除数据
     * @param sql
     * @param params
     * @return 删除数据条数
     * @exception RxCrawlerException
     */
	public int delete(String sql, Object[] params) throws RxCrawlerException;
	
	/**
     * 根据参数更新数据
     * @param sql
     * @param params
     * @return 更新数据条数
     * @exception RxCrawlerException
     */
	public int update(String sql, Object[] params) throws RxCrawlerException;
	
	/**
     * 根据指定参数查询数据
     * @param sql
     * @param clazz
     * @param params
     * @return 根据指定参数查询数据
     * @exception RxCrawlerException
     */
	public <T> List<T> query(String sql, Class<T> clazz, Object[] params)throws RxCrawlerException;
	
	/**
     * 根据已存在的表创建表
     * @param tableName 要创建的目标表
     * @param templateTableName 参照的表
     * @return void
     * @exception RxCrawlerException
     */
	public void createTableByTemplate(String tableName, String templateTableName) throws RxCrawlerException;
	

	/**
	 * @param data Map中将要插入数据的键值对,主键为表中字段的名称,键值为将要存储的数据值
	 * @param tableName 存储的表名
	 * @return  Map中的key为表中的字段名, 在tableName表中插入data中的值, 返回插入记录的主键seq
	 */
	public int saveData(Map<String, String> data,String tableName);
	
	

	/**
	 * @param List<Map> M使用dataList中的第一个元素的Map,Map中的key为表中的字段名,键值为将要存储的数据值
	 * @param tableName 存储的表名
	 * @return  Map中的key为表中的字段名, 在tableName表中批量插入data中的值, 返回插入记录的主键seq列表
	 */
	public int[] saveBatchData(List<Map<String, String>> dataList,String tableName);


}
