package com.web2data.open;

import com.web2data.engine.crawler.browser.BrowserInfra;

public abstract class RxAdvancedStep extends RxStep {

	
	public BrowserInfra BROWSER = null;
	
	
	@Override
	protected abstract RxTask createTestTask();

	@Override
	public void _before( RxTask task, RxResult result ) throws Exception {
		super._before(task, result);
		BROWSER = BrowserInfra.getInstance();
	}
	
	@Override
	public abstract void execute( RxTask task, RxResult result ) throws Exception;
	
	@Override
	public void _after( RxTask task, RxResult result ) throws Exception {
		super._after(task, result);
	}
}
