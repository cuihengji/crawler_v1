package com.rkylin.crawler.engine.flood.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.web2data.system.config._main.ConfigFacade;

public class ConfigQueryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger(ConfigQueryServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        refreshConfig(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        refreshConfig(resp);
    }

    private void refreshConfig(HttpServletResponse resp) {
        try {
            ConfigFacade.getInstance().refreshConfig();
            ServletHelper.return200(resp);
        } catch (Throwable e) {
            logger.error(e);
            ServletHelper.return500(resp, e.getMessage());
        }
    }
}
