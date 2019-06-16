package com.rkylin.crawler.engine.flood.exception;

public class ProcessMsgException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7873159020673452131L;
    
    public ProcessMsgException( int code ){  
        super(code);
    }
    
    public ProcessMsgException( int code, String name ){  
        super(code, name);
    }
    
    public ProcessMsgException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public ProcessMsgException( int code, Throwable t ){  
        super(code, t);
    }
    
    public ProcessMsgException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public ProcessMsgException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
