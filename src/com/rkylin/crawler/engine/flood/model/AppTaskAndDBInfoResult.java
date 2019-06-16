package com.rkylin.crawler.engine.flood.model;

import com.web2data.system.config.entity.app_data_host;
import com.web2data.system.config.entity.app_task_info;

public class AppTaskAndDBInfoResult {
	
	private app_task_info task_info;
	private app_data_host app_data_host;
	
	public app_task_info getTask_info() {
		return task_info;
	}

	public void setTask_info(app_task_info task_info) {
		this.task_info = task_info;
	}

	public app_data_host getApp_data_host() {
		return app_data_host;
	}
	
	public void setApp_data_host(app_data_host app_data_host) {
		this.app_data_host = app_data_host;
	}
}
