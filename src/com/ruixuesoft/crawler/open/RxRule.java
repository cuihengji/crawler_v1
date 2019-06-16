package com.ruixuesoft.crawler.open;

public interface RxRule {

/**
 * app插件接口
 * @param myTask             封装任务相关的属性和方法
 * @param myCrawler          封装页面元素爬取操作的方法
 * @param myDatabase         封装数据库操作的方法
 * @return RxResult          封装返回结果
 * @exception RxCrawlerException
 *            Throwable
 */
public RxResult execute(RxTask myTask, RxCrawler myCrawler, RxDatabase myDatabase) throws RxCrawlerException;


}
