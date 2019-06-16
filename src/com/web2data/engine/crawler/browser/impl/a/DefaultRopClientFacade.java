package com.web2data.engine.crawler.browser.impl.a;

import com.Rop.api.DefaultRopClient;

public class DefaultRopClientFacade {

	//以下信息为沙箱环境的信息
//	private static String ropUrl = "https://testapi.open.ruixuesoft.com:30005/ropapi";
//	private static String appKey = "9DE7524A-9F9B-4B1F-9B79-9D763C402236";
//	private static String appSecret = "F6AB788C-DB6E-4D36-8EA1-AF19C38AB3A8";
	
	//以下信息为正式环境的信息
	private static String ropUrl = "https://api.open.ruixuesoft.com:30005/ropapi";
	private static String appKey = "B48CDD54-719B-41FF-BB31-D2869229937F";
	private static String appSecret = "95BEB8B7-A8BF-46AD-A5D3-01C0A9857495";

	private static DefaultRopClient ropClientFacade = null;

	private DefaultRopClientFacade() {

	}

	public static DefaultRopClient getInstance() {

		if (ropClientFacade == null) {
			ropClientFacade = new DefaultRopClient(ropUrl, appKey, appSecret, "xml");
		}

		return ropClientFacade;
	}
}
