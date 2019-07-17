package com.web2data.system.infra.api;

import java.util.List;

import app123.recipe3.user_task_host;

public class worker_usertaskhosts {

	
    int worker = -1;

	List<String> usertaskhosts = null;

	
	public int getWorker() {
		return worker;
	}

	public void setWorker(int worker) {
		this.worker = worker;
	}

	public List<String> getUsertaskhosts() {
		return usertaskhosts;
	}

	public void setUsertaskhosts(List<String> usertaskhosts) {
		this.usertaskhosts = usertaskhosts;
	}

}
