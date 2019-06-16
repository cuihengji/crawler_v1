package com.rkylin.crawler.engine.flood.exception;

public class SystemException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -7551997228958674909L;

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(String msg, Throwable t) {
        super(msg, t);
    }

    public SystemException(Throwable t) {
        super(t);
    }
}
