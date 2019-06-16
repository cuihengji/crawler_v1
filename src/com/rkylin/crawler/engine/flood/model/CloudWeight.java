package com.rkylin.crawler.engine.flood.model;

public class CloudWeight  implements Comparable<CloudWeight>{

	private int cloudSeq;
	private int weight;

	public CloudWeight() {
		super();
	}
	
	public CloudWeight(int cloudSeq, int weight) {
		super();
		this.cloudSeq = cloudSeq;
		this.weight = weight;
	}

	public int getCloudSeq() {
		return cloudSeq;
	}

	public void setCloudSeq(int cloudSeq) {
		this.cloudSeq = cloudSeq;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(CloudWeight o) {
		
		return ((Integer)this.getWeight()).compareTo(o.getWeight());
	}

}
