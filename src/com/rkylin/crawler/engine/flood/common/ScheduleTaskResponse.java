package com.rkylin.crawler.engine.flood.common;

public class ScheduleTaskResponse {
    
	private int code;
    private String message;
    private ScheduleTaskCode result;

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

    public ScheduleTaskCode getResult() {
        return result;
    }

    public void setResult(ScheduleTaskCode result) {
        this.result = result;
    }

}
