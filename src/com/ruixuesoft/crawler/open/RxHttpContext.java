package com.ruixuesoft.crawler.open;

import java.util.Map;

public interface RxHttpContext {

	public Map<String, String> getHeader();
	
	public String getParameters();

	public Map<String, String> getCookies();
}
