package com.rkylin.crawler.engine.flood.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.rkylin.crawler.engine.flood.crawler.CrawlerManager;
import com.rkylin.crawler.engine.flood.tools.ReportHostStatusTool;
import com.rkylin.crawler.engine.flood.tools.ThreadPoolManager4Tool;
import com.rkylin.crawler.engine.flood.util.FileUtil;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;

public class EngineEntryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger(EngineEntryServlet.class);

    public EngineEntryServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        initializeApp();
    }

    /**
     * Flood engine主入口
     * 
     */
    private void initializeApp() {

        try {
        	
        	deleteTempdataFolder();
        	
            // 读取本地配置文件，必须先做！
            LocalConfigLoader.load();

            // 取得所有的配置，必须在业务代码之前进行！
            ConfigFacade cf = ConfigFacade.getInstance();
            cf.initializeConf();

            // 根据配置启动webdriver和工作线程
            if (Constant.LocalConfig.SYSTEM.equals(cf.getSystemType())) {
                // 取得crawler id和max session
                int[] cs = cf.getCrawlerSeqAndSessNum();
                
                int browserSessions = cf.getBrowserSessions();
                int clientSessions = cf.getClientSessions();
                
				if (browserSessions <= 0 && clientSessions <= 0) {
                    logger.error("Can not find this host in crawler!");
                    return;
                }

                // 启动工具类ThreadPool
                int half = cs[1] / 2;
                ThreadPoolManager4Tool.init(half == 0 ? 1 : half);
                
                // 启动config定期更新
                //cf.scheduleRefresh(5 * 60 * 1000, 5 * 60 * 1000);
                
                // 启动本机状态监控
                ReportHostStatusTool.scheduleReportHostStatus(cf);
                
                // 启动工作线程
                //TODO 正常启动Browser Session,然后Client从最大的Browser数开始
                CrawlerManager.getIns().initializeCrawlerThread(cs[0], browserSessions, clientSessions);
                
            }
        } catch (Exception e) {
        	System.out.println(e);
            logger.error(e.getMessage(), e);
        }

    }

	private void deleteTempdataFolder() {
		
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (isWindowsHost) {
			// 删除c://tempdata
			logger.info("start deleting C:\\chromeData");
			FileUtil.deleteFiles(WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DATA);
			logger.info("end deleting C:\\chromeData");
		} else {
			// 删除/var/tempdata
			logger.info("start deleting /data/chromeData");
			//TODO File.separator?
			FileUtil.deleteFiles(File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.CHROME_DATA);
			logger.info("end deleting /data/chromeData");
		}
	}
}
