package com.web2data.system.task.api;

import com.web2data.open.RxResult;
import com.web2data.open.RxTask;

public class API840NotityTaskFinished {


	//public static int _code = -1; // 200 - OK

	private static void call( RxTask task, RxResult result ) {
		
		String file = null;
		
		try {
			
			//file = HTTP.doGet("");
			//API330NotityTaskStarted json = JsonUtil.convertJsonStr2Obj(file, API330NotityTaskStarted.class);
			//_code = json.getCode();

		} catch (Exception e) {
			//
			result.setFinishedCode(840);
		}
	}
	
	
	// -----------------------------------------------------------------------------
	public int code = -1;
	public String message = null;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static void main(String[] args) {

		//List<String> temp1 = findTestQueueListInTheUserTaskHost( 123, 1 );
		//System.out.println("temp1 = " + temp1.size());
		
		//List<String> temp2 = findProdQueueListInTheUserTaskHost( 123, 1 );
		//System.out.println("temp2 = " + temp2.size());
	}
	
}
