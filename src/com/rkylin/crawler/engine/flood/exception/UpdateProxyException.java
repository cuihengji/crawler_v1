package com.rkylin.crawler.engine.flood.exception;

public class UpdateProxyException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7773159032573452131L;
    
    public UpdateProxyException( int code ){  
        super(code);
    }
    
    public UpdateProxyException( int code, String name ){  
        super(code, name);
    }
    
    public UpdateProxyException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public UpdateProxyException( int code, Throwable t ){  
        super(code, t);
    }
    
    public UpdateProxyException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public UpdateProxyException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
