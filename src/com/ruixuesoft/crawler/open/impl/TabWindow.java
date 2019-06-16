package com.ruixuesoft.crawler.open.impl;

import java.util.List;

public class TabWindow {
	
   	List<String> tabWindowHandleList = null;
   	int newTabWindowHandleIndex = -1;
   	
	public List<String> getTabWindowHandleList() {
		return tabWindowHandleList;
	}
	
	public void setTabWindowHandleList(List<String> tabWindowHandleList) {
		this.tabWindowHandleList = tabWindowHandleList;
	}
	
	public int getNewTabWindowHandleIndex() {
		return newTabWindowHandleIndex;
	}
	
	public void setNewTabWindowHandleIndex(int newTabWindowHandleIndex) {
		this.newTabWindowHandleIndex = newTabWindowHandleIndex;
	}
}
