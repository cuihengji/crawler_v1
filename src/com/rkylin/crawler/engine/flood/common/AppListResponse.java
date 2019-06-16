package com.rkylin.crawler.engine.flood.common;


public class AppListResponse {
    private int code;
    private String message;
    private AppListResult result;

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

    public AppListResult getResult() {
        return result;
    }

    public void setResult(AppListResult result) {
        this.result = result;
    }
    

}
