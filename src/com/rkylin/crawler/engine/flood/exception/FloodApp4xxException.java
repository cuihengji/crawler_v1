package com.rkylin.crawler.engine.flood.exception;

public class FloodApp4xxException extends FloodAppException {

    /**
     * 
     */
    private static final long serialVersionUID = 6573159020673452131L;

    public FloodApp4xxException( int code ){  
        super(code);
    }
    
    public FloodApp4xxException( int code, String name ){  
        super(code, name);
    }
    
    public FloodApp4xxException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public FloodApp4xxException( int code, Throwable t ){  
        super(code, t);
    }
    
    public FloodApp4xxException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public FloodApp4xxException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
