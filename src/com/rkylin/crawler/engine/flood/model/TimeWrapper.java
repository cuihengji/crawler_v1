package com.rkylin.crawler.engine.flood.model;

public class TimeWrapper {

	private Long currentTime;

	public TimeWrapper(long currentTimeMillis) {
		currentTime = currentTimeMillis;
	}

	public Long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
	}

	@Override
	public String toString() {
		return "TimeWrapper [currentTime=" + currentTime + "]";
	}
	
}
