package com.web2data.engine.core;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


import com.web2data._global.R;
import com.web2data._global.SessionThreadLocal;

import com.web2data.engine.stage.RxStepManager;
import com.web2data.open.RxResult;
import com.web2data.open.RxStep;
import com.web2data.open.RxTask;

import com.web2data.utility.U;

public class Worker {

    private int _status = -1;
    
    //public int INITIALIZING = 0;
    private static int RUNNING = 1;
    //private int PAUSED = 2;
    private static int TERMINATED = 3;
	
	private RxTask _task = null;
	private RxResult _result = null;
	private RxStep _step = null;

	private int _sessionType = -1;
	private int _sessionIndex = -1;
	
	private WorkCallable _WorkCallable = null;
	//private FutureTask<Void> _FutureTask = null;
	//private _WorkThread _WorkThread = null;
	
	
	public Worker( int sessionType, int sessionIndex ) {
//		this._crawler = RxCrawlerImpl.getRxCrawler();
//		
//		// 为当前Session准备一个Crawler，如果不是第一次初始化，则需要把之前启动的浏览器杀掉
//		this._crawler = Crawler.newCrawler( this._sessionType, this._sessionIndex );
//		
//		// 如果初始化失败，则麻溜的退出
//		if ( this._crawler == null ) {
//			break;
//		}
		
		
//		this.sessionType = SessionThreadLocal.getSessionType();
//		this.sessionIndex = SessionThreadLocal.getSessionIndex();
		
		this._sessionType = sessionType;
		this._sessionIndex = sessionIndex;
		
		_WorkCallable = new WorkCallable(this._sessionType, this._sessionIndex);
		//_FutureTask = new FutureTask<Void>( _WorkCallable );
		//_FutureTask = new FutureTask<Void>( _WorkCallable );
		//_WorkThread = new WorkThread( _FutureTask, this._sessionType, this._sessionIndex );
	}
	
	
	public void terminateOngoingTask() {
		this._status = TERMINATED;
	}
	
    private boolean isTerminated() {
        if (this._status == TERMINATED) return true;
        return false;
    }
	
    
    public boolean executeOneTask() {
    	
    	// 重置状态，这个很重要
    	_status = -1;

		// 从Scheduler取一个任务, 在fetchTask()中判断是否违反 FactoryIP Policy. 注意： 先计算目标队列，再取消息
		_task = TaskScheduling.fetchTask2( this._sessionType, this._sessionIndex );
		
		// 没有任务则迅速返回
		if ( _task == null ) {
			System.out.println( "----------------- _task == null ");
			return false;
		} else {
			System.out.println( "----------------- _task == " + _task.getX1());
		}
		
		// worker 为 aTask 的执行 做好准备工作,包括数据库连接
		// 901-909 准备的各个环节出现问题
		//boolean prepareResult = this.prepareResourceForTask(aTask);
		
		// 根据 _task.env 设置 _step._IN_TEST_ENVIRONMENT; _step._IN_PRODUCTION_ENVIRONMENT;
		_step = RxStepManager.getStepForTheTask( _task );
		
		_result = null;
		_result = new RxResult();
		
		_WorkCallable.prepare( _task, _step, _result );
		
		
		// 没有准备好，则退出
		//if ( prepareResult != Constant.SUCCESS ) {
			
			// 不需要这里设置各种没有准备好的原因，在prepareForTask()中早就设置完了
			//aTask.setFinishedCode( R.CODE_990 );
			//aTask.log( "worker 没有准备好" );

			// 有两种选择： (1)抛弃且通知APP, (2)把消息返回给SCHEDULER
			//APP.notifyTaskExecutionResult( aTask, this._worker._rxResult );
			//SCHEDULER.returnTaskMessage( aTask );
		//	return false;
		//}

		// worker开始工作
        //FutureTask<Void> future = new FutureTask<Void>( new WorkCallable() );
        //new WorkThread( future ).start();
		
        //FutureTask<Void> future = new FutureTask<Void>( new WorkCallable(this._sessionType, this._sessionIndex) );
		
		FutureTask<Void> futureTask = new FutureTask<Void>( _WorkCallable );
		new WorkThread( futureTask, this._sessionType, this._sessionIndex ).start();
		
        
		//_WorkCallable = new WorkCallable(this._sessionType, this._sessionIndex);
		//_FutureTask = new FutureTask<Void>( _WorkCallable );
		//_WorkThread = new WorkThread( _FutureTask, this._sessionType, this._sessionIndex );
        
        
		//_WorkCallable.setParameters( _stage, _task, _crawler, _database );
		//_WorkThread.start();
        

        // 执行累计时间
        int eclapsedSeconds = 0;
        
        // 循环检测时间累积 和 任务线程的执行状态变化
        // 911-919 准备的各个环节出现问题
        
    	//public static int CODE_891 = 891; // Session,java - 891: Task 被手工终止
    	//public static int CODE_892 = 892; // Session,java - 892: Task 执行超时
    	//public static int CODE_893 = 893; // Session,java - 893: Task 其他原因被取消执行
    	//public static int CODE_899 = 899; // Worker.java - 899: 返回的RxResult为 null
        
        //System.out.print(0);
        
        while ( true ) {
        	
        	//System.out.print("<1>");
        	
    		// 正常结束
    		if ( futureTask.isDone() ) {
    			//System.out.print("<2>");
    			
    			// 利用客户设置的 status code,作为最终的status code
    			// 获得 this._worker._rxResult;

    			//_tasksCompleted++;
    			//System.out.println( " ================ " + _sessionType + " " + _sessionIndex + " " + _tasksCompleted );
    			
    			_finishTask();
    			break;
    		}
    		
    		// 前端触发手工终止
    		if ( this.isTerminated() ) {
    			//System.out.print("<3>");
    			
    			if ( !futureTask.isCancelled() && !futureTask.isDone() ) {
    				//System.out.print("<4>");
    				futureTask.cancel( true );
    			}
    			
    			_result.setFinishedCode( R.CODE_891 );
    			_result.log( "Task被手工终止" );
    			
    			_finishTask();
				break;
    		}
    		
    		// 累计到超时
    		if ( eclapsedSeconds > ( _task.getExecutionTimeoutSeconds() ) ) {
    			//System.out.print("<5>");
    			
    			if ( !futureTask.isCancelled() && !futureTask.isDone() ) {
    				//System.out.print("<6>");
    				
    				futureTask.cancel( true );
    			}
    			
    			_result.setFinishedCode( R.CODE_892 );
    			_result.log( "Task执行超时" );
    			
    			_finishTask();
				break;
    		}
    		
    		// 检测到暂停, 不支持暂停，开发者可以在Rule.java中加入 sleep(长时间) 来实现暂停来debug的效果
    		//if ( mySession.myWorker.isPAUSED() ) {
    		//
    		//}

    		//System.out.print("<7>");
    		
    		U.sleepSeconds( 1 );
    		eclapsedSeconds =+ 1;
    		
    		//LOG.info("+1	" + eclapsedSeconds );
    		
    		
    		//  调试 终止任务
    		//if ( T.mintutesEquals( 10 ) ) {
    		//	this._worker.setSTOPPED();
    		//}
        }
        
        
        //System.out.println("<9>");
        
        // worker 释放之前申请的资源,包括数据库连接
     	//this.releaseResource();
        
        
        // 可能有些特殊情况, 需要重新执行一次,可以计数比如：连续执行了多少次
        // times = times - 1;
        //if ( times > 0 ) {
        //	executeOneTaskWithManyTimes( times - 1 );
        //}
        
        
        // 更新的信息, 包括: RxResult
        //APP.notifyTaskExecutionResult( aTask );
        

		// 清理战场，关掉多余tab页，恢复缺省页（百度）
        // TODO ......

        return true;
	}
	
	
//	// 执行一个任务，重置一次
//	private void _reset() {
//		
//		_status = RUNNING;
//		_task = null;
//		_crawler = null;
//		_database = null;
//		_stage = null;
//	}
//	
	
	// 做好准备工作： Task, Crawler, Database, Stage, Result
//	public boolean _prepareResourceForTask( String env, RxTask arg ) {
//		
//		// _reset()
//		
//		_task = arg;
//	
//		// 出现的各种异常，记录到Task中, 96x
//		_step = RxStepManager.getStepForTask( _task );
//		
//		_result = new RxResult();
//		
//		// 出现的各种异常，记录到Task中, 97x
//		//_database = RxDatabaseImpl.openDatabaseForTask( _task );
//		
//		// 出现的各种异常，记录到Task中, 98x
//		//if ( _crawler == null ) {
//		//	_crawler = RxCrawlerImpl.getRxCrawlerForCurrentSession();
//		//}
//		
//		return true;
//	}
	
	
	// 
	public void _finishTask() {
		
		// 把执行完的时间, 更新到缓存的Policy的信息中
		//key: stepId
		//value: longTimeMills.
		//PolicySys.keep( _step.getId(), System.currentTimeMillis() );
		
		
		//
		_task = null;
		_step = null;
		_result = null;
		
		//_database.closeDatabase();
		
		return;
	}
	
	// 
	public void releaseResource2() {
		// 
		_task = null;
		//_stage = null;
		//_database.closeDatabase();
	}
	
 
//    @Override
//    public Void call() {
//        
//    	try {
//    		_stage.execute( _task, _crawler, _database );
//    	} catch ( Throwable e ) {
//    		_task.setFinishedCode( R.CODE_999 );
//    		_task.log( e.getMessage() + e.getLocalizedMessage() );
//    	}
//    	
//    	//_status = STOPPED;
//    	return null;
//    }
    
    
    //public void setRxResult( int code, String message ) {
    //	_rxResult = new RxResult( code, message );
    //}
	
    
	// ===================================================================================================
    
	
    //public int getStatus() {
    //    return this.status;
    //}
    
//    public String printStatus() {
//    	if ( this.status == INITIALIZING ) return "INITIALIZING";
//    	if ( this.status == RUNNING ) return "RUNNING";
//    	if ( this.status == PAUSED ) return "PAUSED";
//    	if ( this.status == STOPPED ) return "STOPPED";
//    	return null;
//    }
//    
//    public void _initialize() {
//        this.status = RUNNING;
//    }
//    
//    public void _run() {
//        this.status = RUNNING;
//    }
//
//    public void _pause() {
//        this.status = PAUSED;
//    }
//    
//    public void _stop() {
//        this.status = STOPPED;
//    }
    
    //public boolean isPAUSED() {
    //    if (this.status == PAUSED) return true;
    //    return false;
    //}
    
}

//========================================================
class WorkCallable implements Callable<Void> {
	
	private int _sessionType = -1;
	private int _sessionIndex = -1;
	
	public WorkCallable(int sessionType, int sessionIndex) {
		//
		this._sessionType = sessionType;
		this._sessionIndex = sessionIndex;
	}
	
	RxStep _step = null;
	RxTask _task = null;
	RxResult _result = null;
	//RxCrawler _crawler = null;
	//RxDatabase _database = null;
	
	public void prepare( RxTask task, RxStep step, RxResult result ) {
		//
		this._step = step;
		this._task = task;
		this._result = result;
		//this._crawler = crawler;
		//this._database = database;
	}
	
    @Override
    public Void call() {
        
		SessionThreadLocal.setSessionType( this._sessionType );
    	SessionThreadLocal.setSessionIndex( this._sessionIndex );
    	
    	try {
    		//System.out.println("============ Hello start 1=================");
    		
    		// 判断当前的环境，然后决定是否
    		//_step._execute( _step._testingTask, _result );
    		
    		_step._execute( _task, _result );
    		//System.out.println("============ Hello start 2=================");
    		//while (true) {
    		//	System.out.print(".");
    		//	U.sleepSeconds(1);
    		//}
    		//System.out.println("============ Hello end =================");
    	} catch ( Throwable e ) {
    		//_task.setFinishedCode( R.CODE_999 );
    		//_task.log( e.getMessage() + e.getLocalizedMessage() );
    		System.out.println( e.getMessage() + e.getLocalizedMessage() );
    	} finally {
    		//
    	}
    	
    	//_status = STOPPED;
    	return null;
    }

}

class WorkThread extends Thread {
	
	private int _sessionType = -1;
	private int _sessionIndex = -1;
	
	public WorkThread( FutureTask<Void> futureTask, int sessionType, int sessionIndex ) {
		super( futureTask );
		
		// 获取到的是启动MyThread线程的线程的 ThreadLocal
		this._sessionType = sessionType;
		this._sessionIndex = sessionIndex;
		
		//System.out.println( "this.sessionType 1= " + this.sessionType );
		//System.out.println( "this.sessionIndex 1= " + this.sessionIndex );
		
	}
	
	public void run() {
		
		// 因为进入新的Thread,以下两行会报错java.lang.NullPointerException, 因为之前没有做 setSessionType() 和 setSessionIndex()
		//System.out.println( "SessionThreadLocal.getSessionType() 2= " + SessionThreadLocal.getSessionType() );
		//System.out.println( "SessionThreadLocal.getSessionIndex() 2= " + SessionThreadLocal.getSessionIndex() );
		
		SessionThreadLocal.setSessionType( this._sessionType );
		SessionThreadLocal.setSessionIndex( this._sessionIndex );
		
		// 不加以下语句, 不会执行FutureTask中的worker
		super.run();
	}

}

