package com.rkylin.crawler.engine.flood.model;

import com.web2data.system.config.entity.App_accounts;
import com.web2data.system.config.entity.Apps;
import com.web2data.system.config.entity.Cloud_users;
import com.web2data.system.config.entity.Clouds;
import com.web2data.system.config.entity.Rules;
import com.web2data.system.config.entity.Scenarios;
import com.web2data.system.config.entity.Session_account;
import com.web2data.system.config.entity.Session_apps;
import com.web2data.system.config.entity.Session_clouds;
import com.web2data.system.config.entity.User_apps;
import com.web2data.system.config.entity.api_app_task;
import com.web2data.system.config.entity.api_config;
import com.web2data.system.config.entity.api_monitor;
import com.web2data.system.config.entity.api_policy;
import com.web2data.system.config.entity.api_proxy;
import com.web2data.system.config.entity.api_scheduler;
import com.web2data.system.config.entity.config_crawler;
import com.web2data.system.config.entity.config_scheduler;
import com.web2data.system.config.entity.host_of_app_data;
import com.web2data.system.config.entity.host_of_app_task;
import com.web2data.system.config.entity.host_of_crawler;
import com.web2data.system.config.entity.host_of_monitor;
import com.web2data.system.config.entity.host_of_policy;
import com.web2data.system.config.entity.host_of_proxy;
import com.web2data.system.config.entity.host_of_scheduler;

public class Config {
    private int system_id;
    private String system_name;

    private api_config[] api_config;
    private api_scheduler[] api_scheduler;
    private api_policy[] api_policy;
    private api_proxy[] api_proxy;

	private api_monitor[] api_monitor;
    private api_app_task[] api_app_task;

	private config_crawler[] config_crawler;
	private config_scheduler[] config_scheduler;

	private host_of_app_data[] host_of_app_data;
    private host_of_app_task[] host_of_app_task;
    private host_of_crawler host_of_crawler;
    private host_of_scheduler[] host_of_scheduler;
    private host_of_policy[] host_of_policy;
    private host_of_proxy[] host_of_proxy;
	private host_of_monitor[] host_of_monitor;
	
	//new added fields for CrawlerGetConfig.php
	private Clouds[] clouds;
	private Apps[] apps;
	private Scenarios[] scenarios;
	private Rules[] rules;
	private App_accounts[] app_accounts;
	private Session_clouds[] session_clouds;
	private Session_apps[] session_apps;
	private Session_account[] session_account;
	
	private Cloud_users[] cloud_users;
	private User_apps[] user_apps;
	

	public int getSystem_id() {
        return system_id;
    }

    public void setSystem_id(int system_id) {
        this.system_id = system_id;
    }

    public String getSystem_name() {
        return system_name;
    }

    public void setSystem_name(String system_name) {
        this.system_name = system_name;
    }


    public api_scheduler[] getApi_scheduler() {
        return api_scheduler;
    }

    public void setApi_scheduler(api_scheduler[] api_scheduler) {
        this.api_scheduler = api_scheduler;
    }
    
    public api_monitor[] getApi_monitor() {
		return api_monitor;
	}

	public void setApi_monitor(api_monitor[] api_monitor) {
		this.api_monitor = api_monitor;
	}

    public config_crawler[] getConfig_crawler() {
        return config_crawler;
    }

    public void setConfig_crawler(config_crawler[] config_crawler) {
        this.config_crawler = config_crawler;
    }

    public host_of_app_data[] getHost_of_app_data() {
        return host_of_app_data;
    }

    public void setHost_of_app_data(host_of_app_data[] host_of_app_data) {
        this.host_of_app_data = host_of_app_data;
    }

    public host_of_app_task[] getHost_of_app_task() {
        return host_of_app_task;
    }

    public void setHost_of_app_task(host_of_app_task[] host_of_app_task) {
        this.host_of_app_task = host_of_app_task;
    }

    public host_of_crawler getHost_of_crawler() {
        return host_of_crawler;
    }

    public void setHost_of_crawler(host_of_crawler host_of_crawler) {
        this.host_of_crawler = host_of_crawler;
    }

    public host_of_scheduler[] getHost_of_scheduler() {
        return host_of_scheduler;
    }

    public void setHost_of_scheduler(host_of_scheduler[] host_of_scheduler) {
        this.host_of_scheduler = host_of_scheduler;
    }
    
    public api_config[] getApi_config() {
		return api_config;
	}

	public void setApi_config(api_config[] api_config) {
		this.api_config = api_config;
	}

    public api_proxy[] getApi_proxy() {
		return api_proxy;
	}

	public void setApi_proxy(api_proxy[] api_proxy) {
		this.api_proxy = api_proxy;
	}
	
    public api_policy[] getApi_policy() {
		return api_policy;
	}

	public void setApi_policy(api_policy[] api_policy) {
		this.api_policy = api_policy;
	}

	public host_of_policy[] getHost_of_policy() {
		return host_of_policy;
	}

	public void setHost_of_policy(host_of_policy[] host_of_policy) {
		this.host_of_policy = host_of_policy;
	}
	
    public host_of_proxy[] getHost_of_proxy() {
		return host_of_proxy;
	}

	public void setHost_of_proxy(host_of_proxy[] host_of_proxy) {
		this.host_of_proxy = host_of_proxy;
	}
	
    public host_of_monitor[] getHost_of_monitor() {
		return host_of_monitor;
	}

	public void setHost_of_monitor(host_of_monitor[] host_of_monitor) {
		this.host_of_monitor = host_of_monitor;
	}
	
	public api_app_task[] getApi_app_task() {
		return api_app_task;
	}

	public void setApi_app_task(api_app_task[] api_app_task) {
		this.api_app_task = api_app_task;
	}

	public Clouds[] getClouds() {
		return clouds;
	}

	public void setClouds(Clouds[] clouds) {
		this.clouds = clouds;
	}

	public Apps[] getApps() {
		return apps;
	}

	public void setApps(Apps[] apps) {
		this.apps = apps;
	}

	public Scenarios[] getScenarios() {
		return scenarios;
	}

	public void setScenarios(Scenarios[] scenarios) {
		this.scenarios = scenarios;
	}

	public Rules[] getRules() {
		return rules;
	}

	public void setRules(Rules[] rules) {
		this.rules = rules;
	}

	public App_accounts[] getApp_accounts() {
		return app_accounts;
	}

	public void setApp_accounts(App_accounts[] app_accounts) {
		this.app_accounts = app_accounts;
	}

	public Session_clouds[] getSession_clouds() {
		return session_clouds;
	}

	public void setSession_clouds(Session_clouds[] session_clouds) {
		this.session_clouds = session_clouds;
	}

	public Session_apps[] getSession_apps() {
		return session_apps;
	}

	public void setSession_apps(Session_apps[] session_apps) {
		this.session_apps = session_apps;
	}

	public Session_account[] getSession_account() {
		return session_account;
	}

	public void setSession_account(Session_account[] session_account) {
		this.session_account = session_account;
	}

	public Cloud_users[] getCloud_users() {
		return cloud_users;
	}

	public void setCloud_users(Cloud_users[] cloud_users) {
		this.cloud_users = cloud_users;
	}

	public User_apps[] getUser_apps() {
		return user_apps;
	}

	public void setUser_apps(User_apps[] user_apps) {
		this.user_apps = user_apps;
	}

	public config_scheduler[] getConfig_scheduler() {
		return config_scheduler;
	}

	public void setConfig_scheduler(config_scheduler[] config_scheduler) {
		this.config_scheduler = config_scheduler;
	}
	
}
