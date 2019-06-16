package com.rkylin.crawler.engine.flood.webdriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WebDriverProxy {
    private WebDriver wd = null;
    private int crawlerid;
    private int sessionid;

	private static final Logger logger = Logger.getLogger(WebDriverProxy.class);

    public WebDriverProxy(WebDriver obj, int cid, int sid) {
        this.wd = obj;
        this.crawlerid = cid;
        this.sessionid = sid;
    }

    public WebDriver getWd() {
    	
    	logger.info("getDriver --------- getWd(): "+wd);

        return wd;
    }

    public void setWd(WebDriver wd) {
        this.wd = wd;
    }

    public int getCrawlerid() {
        return crawlerid;
    }

    public void setCrawlerid(int crawlerid) {
        this.crawlerid = crawlerid;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

	@Override
	public String toString() {
		return "WebDriverProxy [wd=" + wd + ", crawlerid=" + crawlerid + ", sessionid=" + sessionid + "]";
	}
    

}
