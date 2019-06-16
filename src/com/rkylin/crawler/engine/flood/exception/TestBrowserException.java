package com.rkylin.crawler.engine.flood.exception;

public class TestBrowserException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7173159020673452131L;
    
    public TestBrowserException( int code ){  
        super(code);
    }
    
    public TestBrowserException( int code, String name ){  
        super(code, name);
    }
    
    public TestBrowserException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public TestBrowserException( int code, Throwable t ){  
        super(code, t);
    }
    
    public TestBrowserException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public TestBrowserException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
