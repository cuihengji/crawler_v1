package com.rkylin.crawler.engine.flood.model;

public class AppWeight {

	private  int appSeq;
	private int weight;

	public AppWeight() {
		super();
	}
	
	public AppWeight(int appSeq, int weight) {
		super();
		this.appSeq = appSeq;
		this.weight = weight;
	}

	public int getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(int appSeq) {
		this.appSeq = appSeq;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
