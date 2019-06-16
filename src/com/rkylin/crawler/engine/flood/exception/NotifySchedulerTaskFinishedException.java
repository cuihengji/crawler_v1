package com.rkylin.crawler.engine.flood.exception;

public class NotifySchedulerTaskFinishedException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 8773159020673452131L;
    
    public NotifySchedulerTaskFinishedException( int code ){  
        super(code);
    }
    
    public NotifySchedulerTaskFinishedException( int code, String name ){  
        super(code, name);
    }
    
    public NotifySchedulerTaskFinishedException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public NotifySchedulerTaskFinishedException( int code, Throwable t ){  
        super(code, t);
    }
    
    public NotifySchedulerTaskFinishedException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public NotifySchedulerTaskFinishedException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
