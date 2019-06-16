package com.rkylin.crawler.engine.flood.exception;

public class LoopProcessMessagesException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7173159020673452131L;
    
    public LoopProcessMessagesException( int code ){  
        super(code);
    }
    
    public LoopProcessMessagesException( int code, String name ){  
        super(code, name);
    }
    
    public LoopProcessMessagesException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public LoopProcessMessagesException( int code, Throwable t ){  
        super(code, t);
    }
    
    public LoopProcessMessagesException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public LoopProcessMessagesException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
