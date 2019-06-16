package com.rkylin.crawler.engine.flood.exception;

public class FloodApp5xxException extends FloodAppException {

    /**
     * 
     */
    private static final long serialVersionUID = 6473159020673452131L;

    public FloodApp5xxException( int code ){  
        super(code);
    }
    
    public FloodApp5xxException( int code, String name ){  
        super(code, name);
    }
    
    public FloodApp5xxException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public FloodApp5xxException( int code, Throwable t ){  
        super(code, t);
    }
    
    public FloodApp5xxException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public FloodApp5xxException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
