package com.rkylin.crawler.engine.flood.common;

public class AvailableSessionResponse {
    
	private int code;
    private String message;
    
    private AvailableSessionNum result;
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public AvailableSessionNum getResult() {
		return result;
	}

	public void setResult(AvailableSessionNum result) {
		this.result = result;
	}

}
