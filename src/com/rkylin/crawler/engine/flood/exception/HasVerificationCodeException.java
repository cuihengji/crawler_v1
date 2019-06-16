package com.rkylin.crawler.engine.flood.exception;

public class HasVerificationCodeException extends FloodException {

    /**
     * 
     */
    private static final long serialVersionUID = 8073159020673452131L;
    
    public HasVerificationCodeException( int code ){  
        super(code);
    }
    
    public HasVerificationCodeException( int code, String name ){  
        super(code, name);
    }
    
    public HasVerificationCodeException( int code, String name, String message ){  
        super(code, name, message);
    }
    
    public HasVerificationCodeException( int code, Throwable t ){  
        super(code, t);
    }
    
    public HasVerificationCodeException( int code, String name, Throwable t ){  
        super(code, name, t);
    }
    
    public HasVerificationCodeException( int code, String name, String message, Throwable t ){  
        super(code, name, message, t);
    }
}
