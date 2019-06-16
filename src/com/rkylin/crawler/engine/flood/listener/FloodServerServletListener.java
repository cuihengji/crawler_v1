package com.rkylin.crawler.engine.flood.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.crawler.CrawlerManager;
import com.rkylin.crawler.engine.flood.tools.ThreadPoolManager4Tool;
import com.rkylin.crawler.engine.flood.webdriver.WebDriverManager;

public class FloodServerServletListener implements ServletContextListener {
    private static final transient Logger logger = Logger.getLogger(FloodServerServletListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

        // 序列化downloadcache
        // HttpDownload.serializeCache();

        // 安全停止所有Thread
        CrawlerManager.getIns().closeAllThread();

        // 安全关闭所有webdriver
        WebDriverManager.getIns().closeAllWebDriver();

        // 关闭工具类ThreadPool
        ThreadPoolManager4Tool.shutdown();
        
        logger.info("contextDestroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // do nothing

    }

}
