package com.rkylin.crawler.engine.flood.model;

public class AppTaskAndDBInfoResponse {
    private int code;
    private String message;
    private AppTaskAndDBInfoResult result;

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

    public AppTaskAndDBInfoResult getResult() {
        return result;
    }

    public void setResult(AppTaskAndDBInfoResult result) {
        this.result = result;
    }
}
