package com.rkylin.crawler.engine.flood.exception;

public class FloodException extends Exception {

private static final long serialVersionUID = 5573159020673452131L;
	
	private int code = 0;
	private String name = null;
	
	private String fileName = null;
	private int lineNumber = -1;
	
	private String ref1 = null;
	private String ref2 = null;
	private String ref3 = null;
	private String ref4 = null;
	private String ref5 = null;
	
    public FloodException( int code ){  
        super();
    	this.code = code;
    }
    
    public FloodException( int code, String name ){  
        super();
    	this.code = code;
    	this.name = name;
    }
    
    public FloodException( int code, String name, String message ){  
        super(message);
    	this.code = code;
    	this.name = name;
    }
    
    public FloodException( int code, Throwable t ){  
        super(t);
    	this.code = code;
    }
    
    public FloodException( int code, String name, Throwable t ){  
        super(t);
    	this.code = code;
    	this.name = name;
    }
    
    public FloodException( int code, String name, String message, Throwable t ){  
        super(message,t);
    	this.code = code;
    	this.name = name;
    }
	
    
    public FloodException( int code, String fileName, int lineNumber ){  
        super();
    	this.code = code;
    	this.fileName = fileName;
    	this.lineNumber = lineNumber;
    }
    
    public FloodException( int code, String name, String fileName, int lineNumber  ){  
        super();
    	this.code = code;
    	this.name = name;
    	this.fileName = fileName;
    	this.lineNumber = lineNumber;
    }
    
    public FloodException( int code, String name, String message, String fileName, int lineNumber  ){  
        super(message);
    	this.code = code;
    	this.name = name;
    	this.fileName = fileName;
    	this.lineNumber = lineNumber;
    }
    
    public FloodException( int code, Throwable t, String fileName, int lineNumber  ){  
        super(t);
    	this.code = code;
    	this.fileName = fileName;
    	this.lineNumber = lineNumber;
    }
    
    public FloodException( int code, String name, Throwable t, String fileName, int lineNumber  ){  
        super(t);
    	this.code = code;
    	this.name = name;
    	this.fileName = fileName;
    	this.lineNumber = lineNumber;
    }
    
    public FloodException( int code, String name, String message, Throwable t, String fileName, int lineNumber  ){  
        super(message,t);
    	this.code = code;
    	this.name = name;
    	this.fileName = fileName;
    	this.lineNumber = lineNumber;
    }
    
    
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getRef1() {
		return ref1;
	}

	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}

	public String getRef2() {
		return ref2;
	}

	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

	public String getRef3() {
		return ref3;
	}

	public void setRef3(String ref3) {
		this.ref3 = ref3;
	}

	public String getRef4() {
		return ref4;
	}

	public void setRef4(String ref4) {
		this.ref4 = ref4;
	}

	public String getRef5() {
		return ref5;
	}

	public void setRef5(String ref5) {
		this.ref5 = ref5;
	}
	
	public void setRefs(String ref1) {
		this.ref1 = ref1;
	}
	
	public void setRefs(String ref1, String ref2) {
		this.ref1 = ref1;
		this.ref2 = ref2;
	}
	
	public void setRefs(String ref1, String ref2, String ref3) {
		this.ref1 = ref1;
		this.ref2 = ref2;
		this.ref3 = ref3;
	}
	
	public void setRefs(String ref1, String ref2, String ref3, String ref4) {
		this.ref1 = ref1;
		this.ref2 = ref2;
		this.ref3 = ref3;
		this.ref4 = ref4;
	}
	
	public void setRefs(String ref1, String ref2, String ref3, String ref4, String ref5) {
		this.ref1 = ref1;
		this.ref2 = ref2;
		this.ref3 = ref3;
		this.ref4 = ref4;
		this.ref5 = ref5;
	}
}
