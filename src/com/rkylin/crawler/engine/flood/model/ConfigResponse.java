package com.rkylin.crawler.engine.flood.model;

public class ConfigResponse {
    private int code;
    private String message;
    private Config result;

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

    public Config getResult() {
        return result;
    }

    public void setResult(Config result) {
        this.result = result;
    }

}
