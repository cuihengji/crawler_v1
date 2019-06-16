package com.rkylin.crawler.engine.flood.common;

public class AppCreateRuleTaskResponse {
    private int code;
    private String message;
    private AppCreateRuleTask result;

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

    public AppCreateRuleTask getResult() {
        return result;
    }

    public void setResult(AppCreateRuleTask result) {
        this.result = result;
    }

}
