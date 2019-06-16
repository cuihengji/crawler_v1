package com.web2data.engine.crawler.browser.impl.a;

import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.rkylin.crawler.engine.flood.websocket.ProxyAddressProducer;

public class PureJSActions {
	
	String name;
	String action;
	
	public void open(String url){
		
		action = "Open";
		name = url;

		
		setAction("Open");
		setName(url);
		String openMessage  = "";
		try {
			openMessage = JsonUtil.convertObj2JsonStr(this);
			
			ProxyAddressProducer.sendMessage("2", openMessage);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
public void click(String node){
		
		action = "Click";
		name = node;

		
		setAction(action);
		setName(name);
		String openMessage  = "";
		try {
			openMessage = JsonUtil.convertObj2JsonStr(this);
			
			ProxyAddressProducer.sendMessage("2", openMessage);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

public void input(String value){
	
	action = "Input";
	name = value;

	
	setAction(action);
	setName(name);
	String openMessage  = "";
	try {
		openMessage = JsonUtil.convertObj2JsonStr(this);
		
		ProxyAddressProducer.sendMessage("2", openMessage);
	} catch (Exception e) {
	
		e.printStackTrace();
	}
}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
