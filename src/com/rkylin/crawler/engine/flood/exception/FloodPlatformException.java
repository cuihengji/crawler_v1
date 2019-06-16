package com.rkylin.crawler.engine.flood.exception;

public class FloodPlatformException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 5673159020673452131L;
    
    public FloodPlatformException( int code ){  
        super(code);
    }
    
    public FloodPlatformException( int code, String name ){  
        super(code, name);
    }
    
    public FloodPlatformException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public FloodPlatformException( int code, Throwable t ){  
        super(code, t);
    }
    
    public FloodPlatformException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public FloodPlatformException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
