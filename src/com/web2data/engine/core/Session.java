package com.web2data.engine.core;

import org.apache.log4j.Logger;

import com.web2data._global.Constant;
import com.web2data._global.SessionStatus;
import com.web2data._global.SessionThreadLocal;
import com.web2data._global.SessionType;
import com.web2data.engine.crawler.browser.BrowserInfra;
import com.web2data.engine.crawler.httpclient.HttpClientInfra;
import com.web2data.engine.crawler.jsoup.JsoupInfra;
import com.web2data.engine.database.DatabaseInfra;
import com.web2data.engine.service.CaptchaCodeInfra;
import com.web2data.engine.service.ProxyPoolInfra;
import com.web2data.utility.U;

public class Session extends Thread {
	
	// Session 类型和序号
    private int _sessionType = -1;
    private int _sessionIndex = -1;
    
    // Session 状态
    private int _status = -1;
    

    // 作为基础设施使用
    //public Infra _infra = null;
    
	public BrowserInfra _BrowserInfra = null;
	public HttpClientInfra _HttpClientInfra = null; // com.web2data.engine.crawler.httpClient
	public JsoupInfra _JsoupInfra = null; // com.web2data.engine.crawler.jsoupInfra
	
	public DatabaseInfra _DatabaseInfra = null; // com.web2data.engine.crawler.DatabaseInfra
	
	public CaptchaCodeInfra _CaptchaCodeInfra = null;
	public ProxyPoolInfra _ProxyPoolInfra = null;
	
//	public BrowserInfra _browserInfra2 = null;
//	public Chrome60BrowserInfra _chrome60BrowserInfra = null;
//	public IE10BrowserInfra _ie10BrowserInfra = null;
//	public Firefox30BrowserInfra _firefox30BrowserInfra = null;
//	public ClientInfra _clientInfra = null;
	

    // 在该Session中工作的Worker
    public Worker _worker = null;
	
    
    //private int _tasksCompleted = 0;
    
    
	// =============================================================
	
	public Session( int sessionType, int sessionIndex ) {
		this._sessionType = sessionType;
		this._sessionIndex = sessionIndex;
		this._status = SessionStatus.INTIALIZING;
		
    	_worker = new Worker( this._sessionType, this._sessionIndex );
    	
    	System.out.println( "public Session( int sessionType, int sessionIndex ) { !!! " );
	}
	
	
	public void terminateOngoingTask() {
		_worker.terminateOngoingTask();
	}
	
	
	// =============================================================
	
    @Override
    public void run() {
    	
		SessionThreadLocal.setSessionType( this._sessionType );
    	SessionThreadLocal.setSessionIndex( this._sessionIndex );
    	
    	//System.out.println( "	SessionThreadLocal.getSessionType() " + SessionThreadLocal.getSessionType() );
    	//System.out.println( "	SessionThreadLocal.getSessionIndex() " + SessionThreadLocal.getSessionIndex() );
    	
    	
    	// 给Session分配一个Worker, 并给Workder分配一个RxCrawler
    	//_worker = new Worker();
    	//_worker.assginRxCrawler();
    	
    	
    	// 循环取任务，然后执行任务
        while ( true ) {
        	
        	// 初始化，如果把浏览器生命周期设置为很大，则通常只运行一次，为了清除浏览器垃圾，也需要定期重启，会运行多次
        	if ( this._status == SessionStatus.INTIALIZING ) {
        		System.out.println( "INTIALIZING !!! " + Thread.currentThread().getName() );
        		
        		try {

        			INTIALIZING(); // 开始初始化
            		this._status = SessionStatus.RUNNING; // 正常初始化则设置成 RUNNING
        			
        		} catch (Exception e) {
        			System.out.println( "INTIALIZING ERROR !!! " + e.getMessage() );
        			U.sleepSeconds( 60 * 60 * 24 * 7 );
        		}
        		
        		// 对浏览器的运行时间设置一个【时间统计变量】
        	}
        	
        	
        	// 如果浏览器定期比如24小时重启一次，时间统计变量到期后，然后把状态设置为INTIALIZING
        	{
        		// 判断时间统计变量是否到期
        		// this._status = SessionStatus.INTIALIZING;
        		
        	}
        	
        	
        	// 运行，通常运行一次就是执行一个任务
        	if ( this._status == SessionStatus.RUNNING ) {
        		System.out.println( "RUNNING	" + Thread.currentThread().getName() );
        		
        		RUNNING();
        	}
        	
        	
        	// 在一些特殊场景下，可以在前端暂停Session的执行
        	if ( this._status == SessionStatus.PAUSED ) {
        		System.out.println( "PAUSED		" + Thread.currentThread().getName() );
        		U.sleepSeconds( Constant.SESSION_PAUSED_SLEEP_SECONDS );
        	}
        	
        	
        	// 停止
        	if ( this._status == SessionStatus.STOPPED ) {
        		System.out.println( "STOPPED	" + Thread.currentThread().getName() );
        		
        		// 释放占用的资源，比如：浏览器
        		// TODO......
        		break;
        	}
        	
        }
    }
    
    private void INTIALIZING() throws Exception {
		// 为当前Session准备Infra，如果不是第一次初始化，则需要把之前启动的浏览器杀掉
		//this._infra = Infra.newInfra( this._sessionType, this._sessionIndex );
		
		this._HttpClientInfra = HttpClientInfra.getInstance();
		this._JsoupInfra = JsoupInfra.getInstance();
		this._DatabaseInfra = DatabaseInfra.getInstance();
		
		this._CaptchaCodeInfra = CaptchaCodeInfra.getInstance();
		this._ProxyPoolInfra = ProxyPoolInfra.getInstance();
    	
		
		if ( this._sessionType == SessionType.SENIOR ) {
			
			// Browser.killBrowser(); // 把之前启动的浏览器删掉
			
			this._BrowserInfra = BrowserInfra.getInstance();
			
    		// 如果初始化失败，则麻溜的退出
    		//if ( this._browserInfra == null || this._httpClientInfra == null || this._jsoupInfra == null ) {
    		//	throw new Exception();
    		//}
			
		} else if ( this._sessionType == SessionType.JUNIOR  ) {
			
    		// 如果初始化失败，则麻溜的退出
    		//if ( this._httpClientInfra == null || this._jsoupInfra == null ) {
    		//	throw new Exception();
    		//}
		}
    }

    
    private void RUNNING() {
		//boolean result = executeOneTask();
    	
    	_worker.executeOneTask();
		
		boolean result = false;
		
		if ( result == Constant.SUCCESS  ) {
			// U.sleepMillis(1 * 1000); // 不休息
		} else {
			U.sleepSeconds( Constant.TASK_UNAVAILABLE_OR_EXECUTION_ERROR_SLEEP_SECONDS );
		}

		//this.status = SessionStatus.PAUSED;
    }
    
    
    // =============================================================
    
    
//	private boolean executeOneTask2() {
//
//		// 从Scheduler取一个任务
//		RxTask aTask = SCHEDULER.fetchNewTask();
//		
//		
//		// 没有任务则迅速返回
//		if ( aTask == null ) {
//			//return false;   //  for testing
//			
//			//  for testing
//			aTask = new RxTask();
//			aTask.setTimeoutSeconds( 10000000 );
//		}
//		
//		
//		// worker 为 aTask 的执行 做好准备工作,包括数据库连接
//		// 901-909 准备的各个环节出现问题
//		boolean prepareResult = this._worker.prepareResourceForTask(aTask);
//		
//		
//		// 没有准备好，则退出
//		if ( prepareResult != Constant.SUCCESS ) {
//			
//			// 不需要这里设置各种没有准备好的原因，在prepareForTask()中早就设置完了
//			//aTask.setFinishedCode( R.CODE_990 );
//			//aTask.log( "worker 没有准备好" );
//
//			// 有两种选择： (1)抛弃且通知APP, (2)把消息返回给SCHEDULER
//			//APP.notifyTaskExecutionResult( aTask, this._worker._rxResult );
//			//SCHEDULER.returnTaskMessage( aTask );
//			return false;
//		}
//
//		// worker开始工作
//        FutureTask<Void> future = new FutureTask<Void>( this._worker );
//        new WorkerThread( future ).start();
//        
//        // 执行累计时间
//        int eclapsedSeconds = 0;
//        
//        // 循环检测时间累积 和 任务线程的执行状态变化
//        // 911-919 准备的各个环节出现问题
//        
//    	//public static int CODE_891 = 891; // Session,java - 891: Task 被手工终止
//    	//public static int CODE_892 = 892; // Session,java - 892: Task 执行超时
//    	//public static int CODE_893 = 893; // Session,java - 893: Task 其他原因被取消执行
//    	//public static int CODE_899 = 899; // Worker.java - 899: 返回的RxResult为 null
//        
//        while ( true ) {
//        	
//    		// 正常结束
//    		if ( future.isDone() ) {
//    			// 利用客户设置的 status code,作为最终的status code
//    			// 获得 this._worker._rxResult;
//
//    			//_tasksCompleted++;
//    			//System.out.println( " ================ " + _sessionType + " " + _sessionIndex + " " + _tasksCompleted );
//    			break;
//    		}
//    		
//    		// 前端触发手工终止
//    		if ( this._worker.isSTOPPED() ) {
//    			
//    			if ( !future.isCancelled() && !future.isDone() ) {
//    				future.cancel( true );
//    			}
//    			
//    			aTask.setFinishedCode( R.CODE_891 );
//    			aTask.log( "Task被手工终止" );
//				break;
//    		}
//    		
//    		// 累计到超时
//    		if ( eclapsedSeconds > ( aTask.getTimeoutSeconds() ) ) {
//    			
//    			if ( !future.isCancelled() && !future.isDone() ) {
//    				future.cancel( true );
//    			}
//    			
//    			aTask.setFinishedCode( R.CODE_892 );
//    			aTask.log( "Task执行超时" );
//				break;
//    		}
//    		
//    		// 检测到暂停, 不支持暂停，开发者可以在Rule.java中加入 sleep(长时间) 来实现暂停来debug的效果
//    		//if ( mySession.myWorker.isPAUSED() ) {
//    		//
//    		//}
//
//    		U.sleepSeconds( 3 );
//    		eclapsedSeconds =+ 3;
//    		
//    		//LOG.info("+1	" + eclapsedSeconds );
//    		
//    		
//    		//  调试 终止任务
//    		//if ( T.mintutesEquals( 10 ) ) {
//    		//	this._worker.setSTOPPED();
//    		//}
//        }
//        
//        
//        // worker 释放之前申请的资源,包括数据库连接
//     	this._worker.releaseResource();
//        
//        
//        // 可能有些特殊情况, 需要重新执行一次,可以计数比如：连续执行了多少次
//        // times = times - 1;
//        //if ( times > 0 ) {
//        //	executeOneTaskWithManyTimes( times - 1 );
//        //}
//        
//        
//        // 更新的信息, 包括: RxResult
//        APP.notifyTaskExecutionResult( aTask );
//        
//
//		// 清理战场，关掉多余tab页，恢复缺省页（百度）
//        // TODO ......
//
//        return true;
//	}

	
	// ----------------
	private static final transient Logger LOG = Logger.getLogger(Session.class);
    
}


//========================================================
//class WorkerThread2 extends Thread {
//	
//	private int sessionType = -1;
//	private int sessionIndex = -1;
//	
//	public WorkerThread2( FutureTask<Void> futureTask ) {
//		super( futureTask );
//		
//		// 获取到的是启动MyThread线程的线程的 ThreadLocal
//		this.sessionType = SessionThreadLocal.getSessionType();
//		this.sessionIndex = SessionThreadLocal.getSessionIndex();
//		
//		//System.out.println( "this.sessionType 1= " + this.sessionType );
//		//System.out.println( "this.sessionIndex 1= " + this.sessionIndex );
//		
//	}
//	
//	public void run() {
//		
//		// 因为进入新的Thread,以下两行会报错java.lang.NullPointerException, 因为之前没有做 setSessionType() 和 setSessionIndex()
//		//System.out.println( "SessionThreadLocal.getSessionType() 2= " + SessionThreadLocal.getSessionType() );
//		//System.out.println( "SessionThreadLocal.getSessionIndex() 2= " + SessionThreadLocal.getSessionIndex() );
//		
//		SessionThreadLocal.setSessionType( this.sessionType );
//		SessionThreadLocal.setSessionIndex( this.sessionIndex );
//		
//		// 不加以下语句, 不会执行FutureTask中的worker
//		super.run();
//	}
//
//}
