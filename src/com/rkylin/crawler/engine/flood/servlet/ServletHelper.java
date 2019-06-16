package com.rkylin.crawler.engine.flood.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ServletHelper {
    private static final transient Logger logger = Logger.getLogger(ServletHelper.class);

    public static void return200(HttpServletResponse response) {
        StringBuffer result = new StringBuffer();
        result.append("{\"code\":\"200\",\"message\":\"OK\"}");
        writeResponse(result, response);
    }

    public static void return500(HttpServletResponse response, String msg) {
        StringBuffer result = new StringBuffer();
        if (msg == null || msg.length() == 0) {
            result.append("{\"code\":\"500\",\"message\":\"System Error!\"}");
        } else {
            result.append("{\"code\":\"500\",\"message\":\"");
            result.append(msg);
            result.append("\"}");
        }

        writeResponse(result, response);
    }

    /**
     * 通过response将内容返回给前端
     * 
     * @param text
     * @param response
     */
    private static void writeResponse(StringBuffer text, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json;charset=utf-8");
            out.println(text.toString());
            out.flush();
            out.close();

        } catch (IOException e) {
            logger.error(e, e);
        }
    }
}
