package com.web2data.engine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.web2data._global.G2;
import com.web2data._global.SessionType;
import com.web2data.open.RxTask;
import com.web2data.system.infra.api.API010GetWorker2UserTaskHostsMapOfTheFactory;
import com.web2data.system.infra.api.API020GetWorker2StepsMapOfTheFactoryUser;
import com.web2data.system.infra.api.API030GetRelatedTablesOfTheApp;
import com.web2data.system.task.api.API310GetQueueListInTheUserTaskHost;
import com.web2data.system.task.api.API320GetTaskOfTheStep;
import com.web2data.utility.U;

public class TaskScheduling {

	
	//
	private static Map<String, Long> teststep_lastRunnedTimeMillis_map = new HashMap<String, Long>();
	private static Map<String, Long> prodstep_lastRunnedTimeMillis_map = new HashMap<String, Long>();
	
	
	public static RxTask fetchTask2(int workerType, int workerIndex) {
		RxTask result = null;
		
		
		//
		String randomUserTaskHost = API010GetWorker2UserTaskHostsMapOfTheFactory
			.findOneRandomUserTaskHostForTheWorker(workerType, workerIndex);
		if (randomUserTaskHost == null) return null;
		
		int[] userTaskHost = U.convertStringToInts( randomUserTaskHost );
		int userId = userTaskHost[0];
		int taskHostId = userTaskHost[1];
		
		//System.out.println("userId =" + userId);
		//System.out.println("taskHostId =" + taskHostId);

		
		// ----------------------- TestEnv -------------------------
		
		// 静态配置的testSteps
		List<String> possibleTestStepsOfTheUserWorker = API020GetWorker2StepsMapOfTheFactoryUser
				.findTestStepListForTheUserWorker(userId, workerType, workerIndex);
		
		//System.out.println("possibleTestStepsOfTheUserWorker =" + possibleTestStepsOfTheUserWorker);
		
		if (possibleTestStepsOfTheUserWorker != null && possibleTestStepsOfTheUserWorker.size() >= 0) {

			// 动态运行的testQueues
			List<String> testQueuesOfTheUserTaskHost = API310GetQueueListInTheUserTaskHost
					.findTestQueueListInTheUserTaskHost(userId, taskHostId);
			
			//System.out.println("testQueuesOfTheUserTaskHost =" + testQueuesOfTheUserTaskHost);
			
			if (testQueuesOfTheUserTaskHost!=null && testQueuesOfTheUserTaskHost.size()>0) {
				
				// 取交集
				List<String> runableSteps = intersection(testQueuesOfTheUserTaskHost, possibleTestStepsOfTheUserWorker);
				
				//System.out.println("runableTestSteps1 =" + runableSteps);
				
				// 去掉超过频繁限度的Steps
				runableSteps = getPolicyedStepsFrom(G2.TEST, runableSteps);
				
				//System.out.println("runableTestSteps2 =" + runableSteps);
				
				if ( runableSteps != null && runableSteps.size() > 0 ) {
					
					
					while ( runableSteps.size() > 0 ) {
					
						String randomStep = U.getRandomElement(runableSteps);
						
						//System.out.println("randomTestStep =" + randomStep);
						
						result = API320GetTaskOfTheStep.fetchTestTask(userId, taskHostId, randomStep);
						
						if ( result == null ) {
							runableSteps.remove( randomStep );
							U.sleepSeconds(1);
							//System.out.println("try =" + runableSteps.size() );
						} else {
							//System.out.println("selectedTestStep =" + randomStep);
							teststep_lastRunnedTimeMillis_map.put(randomStep, System.currentTimeMillis());
							return result; 
						}
					}
				}
			}
		}
		

		// ----------------------- ProdEnv -------------------------
		
		// 静态配置的prodSteps
		List<String> possibleProdStepsOfTheUserWorker = API020GetWorker2StepsMapOfTheFactoryUser
				.findProdStepListForTheUserWorker(userId, workerType, workerIndex);
		
		//System.out.println("possibleProdStepsOfTheUserWorker =" + possibleProdStepsOfTheUserWorker);
		
		if (possibleProdStepsOfTheUserWorker != null && possibleProdStepsOfTheUserWorker.size() >=0) {
		
			// 动态运行的prodQueues
			List<String> prodQueuesOfTheUserTaskHost = API310GetQueueListInTheUserTaskHost
					.findProdQueueListInTheUserTaskHost(userId, taskHostId);
			
			//System.out.println("prodQueuesOfTheUserTaskHost =" + prodQueuesOfTheUserTaskHost);
			
			if (prodQueuesOfTheUserTaskHost!=null && prodQueuesOfTheUserTaskHost.size()>0) {
				// 取交集
				List<String> runableSteps = intersection(prodQueuesOfTheUserTaskHost, possibleProdStepsOfTheUserWorker);
				
				//System.out.println("runableProdSteps1 =" + runableSteps);
				
				// 去掉超过频繁限度的Steps
				runableSteps = getPolicyedStepsFrom(G2.PROD, runableSteps);
				
				//System.out.println("runableProdSteps2 =" + runableSteps);
				
				if ( runableSteps != null && runableSteps.size() > 0 ) {
					
					int fetchs = 0;
					while ( runableSteps.size() > 0 && fetchs < 3 ) {
					
						String randomStep = U.getRandomElement(runableSteps);
						
						//System.out.println("randomTestStep =" + randomStep);
						
						result = API320GetTaskOfTheStep.fetchProdTask(userId, taskHostId, randomStep);
						fetchs++;
						
						if ( result == null ) {
							runableSteps.remove( randomStep );
							U.sleepSeconds(3); // 3秒尝试一次，一般来讲不会发生，因为队列中有消息，一般会被取到
							//System.out.println("try =" + runableSteps.size());
						} else {
							//System.out.println("selectedProdStep =" + randomStep);
							prodstep_lastRunnedTimeMillis_map.put(randomStep, System.currentTimeMillis());
							return result;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	// 过滤出符合Policy条件的Step
	public static List<String> getPolicyedStepsFrom(int env, List<String> steps) {
		
        LinkedList<String> bigLinkedList = new LinkedList<String>(steps);
        Iterator<String> bigIterator = bigLinkedList.iterator();
        
        Map<String, Long> temp = null;
        if ( env == G2.TEST )
        	temp = teststep_lastRunnedTimeMillis_map;
        else 
        	temp = prodstep_lastRunnedTimeMillis_map;
        
        while (bigIterator.hasNext()) {
            
        	String step = bigIterator.next();
        	
        	Long lastRunnedTime = temp.get(step);
        	if ( lastRunnedTime == null ) {
        		lastRunnedTime = 0L;
        	}
        	
        	// 得到 step 对应的 policyedInterval
        	int policyedIntervalSeconds 
        		= API030GetRelatedTablesOfTheApp.getTheStepPolicyIntervalInCurrentHour(env, step);
        	
    		if ( (System.currentTimeMillis() - lastRunnedTime) < (policyedIntervalSeconds*1000) ) {
    			bigIterator.remove();
    		}
        }
        
        return bigLinkedList;
	}
	
	
	public static List<String> intersection(List<String> smallList, List<String> bigList) {
		
		if ( smallList == null || smallList.size()==0 || bigList == null || bigList.size()==0 )
			return null;
	        
	    LinkedList<String> bigLinkedList = new LinkedList<String>(bigList); // 大集合用linkedlist  
	    HashSet<String> smallHashSet = new HashSet<String>(smallList); // 小集合用hashset  
	        
	    Iterator<String> bigIterator = bigLinkedList.iterator(); // 采用Iterator迭代器进行数据的操作  
	    while (bigIterator.hasNext()) {
	            if (!smallHashSet.contains(bigIterator.next())) {
	            	bigIterator.remove();
	            }
	    }
	    return bigLinkedList;
	}
	
	
	public static void main(String[] args) {
		//
		
		//RxTask testTask = fetchTask(SessionType.SENIOR, 1);
		//System.out.println("testTask = " + testTask.getX1());
		
		
		//RxTask result = API320GetTaskOfTheStep.fetchTestTask(123, 3, "900-3");
		//result.getX1();
		
//		System.out.println("Task1 = " + fetchTask2(SessionType.SENIOR, 0));  U.sleepSeconds( 30 );
//		System.out.println("Task2 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task3 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task4 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task5 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task6 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task7 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task8 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		System.out.println("Task9 = " + fetchTask(SessionType.SENIOR, 1));  U.sleepSeconds( 30 );
//		
		
		List<String> x = new ArrayList<String>();
		x.add(0, "10"); x.add(0, "20"); x.add(0, "30"); x.add(0, "40"); x.add(0, "50");
		
		List<String> y = new ArrayList<String>();
		y.add(0, "100"); y.add(0, "20"); y.add(0, "300"); y.add(0, "40"); y.add(0, "500");
		
		List<String> z = intersection(x,y);
		System.out.println("z = " + z );
		
		z = intersection(y,z);
		System.out.println("z = " + z );
		
		z = intersection(null,z);
		System.out.println("z = " + z );
		
		z = intersection(y,null);
		System.out.println("z = " + z );
	}
}
