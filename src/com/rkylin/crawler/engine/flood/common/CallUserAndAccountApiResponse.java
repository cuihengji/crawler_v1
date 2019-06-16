package com.rkylin.crawler.engine.flood.common;


public class CallUserAndAccountApiResponse {
    private int code;
    private String message;
    private UserAndAccountResult result;

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

    public UserAndAccountResult getResult() {
        return result;
    }

    public void setResult(UserAndAccountResult result) {
        this.result = result;
    }

}
