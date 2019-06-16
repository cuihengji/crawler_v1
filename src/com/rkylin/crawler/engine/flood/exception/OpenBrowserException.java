package com.rkylin.crawler.engine.flood.exception;

public class OpenBrowserException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7773159020673452131L;
    
    public OpenBrowserException( int code ){  
        super(code);
    }
    
    public OpenBrowserException( int code, String name ){  
        super(code, name);
    }
    
    public OpenBrowserException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public OpenBrowserException( int code, Throwable t ){  
        super(code, t);
    }
    
    public OpenBrowserException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public OpenBrowserException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
