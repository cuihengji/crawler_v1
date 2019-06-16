package com.web2data.system.config.entity;

public class scenario {
    private int seq;
    private int app_seq;
    private String name;
    private String create_time;
    private String comment;
    private int scenario_index;

    public int getScenario_index() {
        return scenario_index;
    }

    public void setScenario_index(int scenario_index) {
        this.scenario_index = scenario_index;
    }

    public scenario() {
        // TODO Auto-generated constructor stub
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getApp_seq() {
        return app_seq;
    }

    public void setApp_seq(int app_seq) {
        this.app_seq = app_seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
