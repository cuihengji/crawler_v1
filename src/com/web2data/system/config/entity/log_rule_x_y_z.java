package com.web2data.system.config.entity;

public class log_rule_x_y_z {
	private int seq = -1;
	private int session_index;
	private int account_index;
	private int app_task_seq;
	private String file = "-1";
	private String method = "-1";
	private int code = -1;
	private String name = "-1";
	private String ref1 = "-1";
	private String ref2 = "-1";
	private String ref3 = "-1";
	private String ref4 = "-1";
	private String ref5 = "-1";
	private String comment = "-1";
	private String create_time = "-1";
	
	public int getSeq() {
		return seq;
	}

	public int getSession_index() {
		return session_index;
	}

	public void setSession_index(int session_index) {
		this.session_index = session_index;
	}

	public int getAccount_index() {
		return account_index;
	}

	public void setAccount_index(int account_index) {
		this.account_index = account_index;
	}

	public int getApp_task_seq() {
		return app_task_seq;
	}

	public void setApp_task_seq(int app_task_seq) {
		this.app_task_seq = app_task_seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRef1() {
		return ref1;
	}

	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}

	public String getRef2() {
		return ref2;
	}

	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

	public String getRef3() {
		return ref3;
	}

	public void setRef3(String ref3) {
		this.ref3 = ref3;
	}

	public String getRef4() {
		return ref4;
	}

	public void setRef4(String ref4) {
		this.ref4 = ref4;
	}

	public String getRef5() {
		return ref5;
	}

	public void setRef5(String ref5) {
		this.ref5 = ref5;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public log_rule_x_y_z() {
		// TODO Auto-generated constructor stub
	}
}
