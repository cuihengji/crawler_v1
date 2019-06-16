package com.rkylin.crawler.engine.flood.common;


public class CallProxyApiResponse {
    private int code;
    private String message;
    private IpProxyResult result;

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

    public IpProxyResult getResult() {
        return result;
    }

    public void setResult(IpProxyResult result) {
        this.result = result;
    }
    

}
