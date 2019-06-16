package com.web2data.system.config.entity;

public class rule_crawler_session {
	private int rule_seq;
	private int crawler_host_seq;
	private int session_index;
	private int weight;
	private String comment;

	public rule_crawler_session() {
		// TODO Auto-generated constructor stub
	}

	public int getRule_seq() {
		return rule_seq;
	}

	public void setRule_seq(int rule_seq) {
		this.rule_seq = rule_seq;
	}

	public int getCrawler_host_seq() {
		return crawler_host_seq;
	}

	public void setCrawler_host_seq(int crawler_host_seq) {
		this.crawler_host_seq = crawler_host_seq;
	}

	public int getSession_index() {
		return session_index;
	}

	public void setSession_index(int session_index) {
		this.session_index = session_index;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
