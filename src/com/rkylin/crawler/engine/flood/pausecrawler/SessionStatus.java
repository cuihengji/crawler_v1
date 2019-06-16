package com.rkylin.crawler.engine.flood.pausecrawler;

import java.util.Date;

public class SessionStatus {

	private boolean isPausedFetchMessage;
	private Date updatedTime;
	
	public boolean isPausedFetchMessage() {
		return isPausedFetchMessage;
	}

	public void setPausedFetchMessage(boolean isPausedFetchMessage) {
		this.isPausedFetchMessage = isPausedFetchMessage;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
