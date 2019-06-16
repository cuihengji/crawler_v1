package com.rkylin.crawler.engine.flood.model;

public class MessageObject {

	// Message format:
	// cloudType~taskPriority~websiteSeq~schedulerHostSeq~appTaskHostSeq~appDataHostSeq~schedulerTaskSeq~appSeq~scenarioIndex~ruleIndex~appTaskTableName~appTaskSeq~appAccountIndex
	private String cloudType;
	private String taskPriority;
	private int websiteSeq;
	private int schHostSeq;
	private int appTaskHostSeq;
	private int appDataHostSeq;
    private int schTaskSeq;
    private int userSeq;
    private String scheduledType;
    
	public String getCloudType() {
		return cloudType;
	}

	public void setCloudType(String cloudType) {
		this.cloudType = cloudType;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public int getWebsiteSeq() {
		return websiteSeq;
	}

	public void setWebsiteSeq(int websiteSeq) {
		this.websiteSeq = websiteSeq;
	}

	public int getSchHostSeq() {
		return schHostSeq;
	}

	public void setSchHostSeq(int schHostSeq) {
		this.schHostSeq = schHostSeq;
	}

	public int getAppTaskHostSeq() {
		return appTaskHostSeq;
	}

	public void setAppTaskHostSeq(int appTaskHostSeq) {
		this.appTaskHostSeq = appTaskHostSeq;
	}

	public int getAppDataHostSeq() {
		return appDataHostSeq;
	}

	public void setAppDataHostSeq(int appDataHostSeq) {
		this.appDataHostSeq = appDataHostSeq;
	}

	private int appSeq;
    private int scenarioIndex;
    private int ruleIndex;
    private String appTaskTableName;
    private int appTaskSeq;
//    private String schTaskTableName;
    private int appAccountSeq;

    public int getAppAccountSeq() {
        return appAccountSeq;
    }

    public void setAppAccountSeq(int appAccountSeq) {
        this.appAccountSeq = appAccountSeq;
    }

    public int getAppSeq() {
        return appSeq;
    }

    public void setAppSeq(int appSeq) {
        this.appSeq = appSeq;
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public void setScenarioIndex(int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    public void setRuleIndex(int ruleIndex) {
        this.ruleIndex = ruleIndex;
    }

    public String getAppTaskTableName() {
        return appTaskTableName;
    }

    public void setAppTaskTableName(String appTaskTableName) {
        this.appTaskTableName = appTaskTableName;
    }

    public int getAppTaskSeq() {
        return appTaskSeq;
    }

    public void setAppTaskSeq(int appTaskSeq) {
        this.appTaskSeq = appTaskSeq;
    }

//    public String getSchTaskTableName() {
//        return schTaskTableName;
//    }
//
//    public void setSchTaskTableName(String schTaskTableName) {
//        this.schTaskTableName = schTaskTableName;
//    }

    public int getSchTaskSeq() {
        return schTaskSeq;
    }

    public void setSchTaskSeq(int schTaskSeq) {
        this.schTaskSeq = schTaskSeq;
    }

	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public String getScheduledType() {
		return scheduledType;
	}

	public void setScheduledType(String scheduledType) {
		this.scheduledType = scheduledType;
	}
    
}
