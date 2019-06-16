package com.rkylin.crawler.engine.flood.exception;

public class NotifySchedulerTaskStartedException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 7073159020673452131L;
    
    public NotifySchedulerTaskStartedException( int code ){  
        super(code);
    }
    
    public NotifySchedulerTaskStartedException( int code, String name ){  
        super(code, name);
    }
    
    public NotifySchedulerTaskStartedException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public NotifySchedulerTaskStartedException( int code, Throwable t ){  
        super(code, t);
    }
    
    public NotifySchedulerTaskStartedException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public NotifySchedulerTaskStartedException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
