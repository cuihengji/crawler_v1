package com.rkylin.crawler.engine.flood.exception;

public class BusinessException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5773159020673452131L;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable t) {
        super(msg, t);
    }

    public BusinessException(Throwable t) {
        super(t);
    }
}
