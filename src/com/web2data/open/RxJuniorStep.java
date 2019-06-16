package com.web2data.open;


public abstract class RxJuniorStep extends RxStep {

	
	@Override
	protected abstract RxTask createTestTask();

	@Override
	public void _before( RxTask task, RxResult result ) throws Exception {
		super._before(task, result);
	}
	
	@Override
	public abstract void execute( RxTask task, RxResult result ) throws Exception;
	
	@Override
	public void _after( RxTask task, RxResult result ) throws Exception {
		super._after(task, result);
	}
}
