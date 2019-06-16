package com.rkylin.crawler.engine.flood.exception;

public class WebDriverGetException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7973159020673452131L;
    
    public WebDriverGetException( int code ){  
        super(code);
    }
    
    public WebDriverGetException( int code, String name ){  
        super(code, name);
    }
    
    public WebDriverGetException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public WebDriverGetException( int code, Throwable t ){  
        super(code, t);
    }
    
    public WebDriverGetException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public WebDriverGetException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
