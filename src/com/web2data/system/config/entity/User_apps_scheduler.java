package com.web2data.system.config.entity;

//SCH015_GetActiveAppListByUser.php,新的API返回值
public class User_apps_scheduler {

	private int appSeq;
	private int app_weight_of_private_cloud_session;

	public int getAppSeq() {
		return appSeq;
	}
	public void setAppSeq(int appSeq) {
		this.appSeq = appSeq;
	}
	
	public int getApp_weight_of_private_cloud_session() {
		return app_weight_of_private_cloud_session;
	}
	public void setApp_weight_of_private_cloud_session(int app_weight_of_private_cloud_session) {
		this.app_weight_of_private_cloud_session = app_weight_of_private_cloud_session;
	}
	
}
