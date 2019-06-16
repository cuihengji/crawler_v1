package com.rkylin.crawler.engine.flood.exception;

public class FloodAppException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 6073159020673452131L;

    public FloodAppException( int code ){  
        super(code);
    }
    
    public FloodAppException( int code, String name ){  
        super(code, name);
    }
    
    public FloodAppException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public FloodAppException( int code, Throwable t ){  
        super(code, t);
    }
    
    public FloodAppException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public FloodAppException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
