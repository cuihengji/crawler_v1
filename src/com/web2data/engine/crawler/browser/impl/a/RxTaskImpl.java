package com.web2data.engine.crawler.browser.impl.a;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.model.RuleTaskInputParameter;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxTask;
import com.web2data.system.app.AppFacade;
import com.web2data.system.config._main.ConfigFacade;
import com.web2data.system.config.entity.Rules;

public class RxTaskImpl extends RxTask {
	
	private static final Logger logger = Logger.getLogger(RxSelectNodeImpl.class);
	
	@Override
	public void createNextRuleTask(RxTask task) throws RxCrawlerException {
		
		checkInterrupted();
		
		try {
			RuleTaskInputParameter ruleTaskInputParameter = new RuleTaskInputParameter();

			ruleTaskInputParameter.setEnv("TEST");
			ruleTaskInputParameter.setUserSeq(String.valueOf(userSeq));
			ruleTaskInputParameter.setAppSeq(appSeq);
			ruleTaskInputParameter.setScenarioIndex(scenarioIndex);
			ruleTaskInputParameter.setRuleIndex(ruleIndex + 1);
			ruleTaskInputParameter.setSourceTaskSeq(sourceTaskSeq);
			ruleTaskInputParameter.setScheduledType(scheduleType);
			if (task.getV1() != null) {
				ruleTaskInputParameter.setV1(task.getV1());
			}
			if (task.getV2() != null) {
				ruleTaskInputParameter.setV2(task.getV2());
			}
			if (task.getV3() != null) {
				ruleTaskInputParameter.setV3(task.getV3());
			}
			if (task.getV4() != null) {
				ruleTaskInputParameter.setV4(task.getV4());
			}
			if (task.getV5() != null) {
				ruleTaskInputParameter.setV5(task.getV5());
			}
			if (task.getV6() != null) {
				ruleTaskInputParameter.setV6(task.getV6());
			}
			if (task.getV7() != null) {
				ruleTaskInputParameter.setV7(task.getV7());
			}
			if (task.getV8() != null) {
				ruleTaskInputParameter.setV8(task.getV8());
			}
			if (task.getV9() != null) {
				ruleTaskInputParameter.setV9(task.getV9());
			}

			ruleTaskInputParameter.setAccountIndex(accountIndex);

			AppFacade.getInstance().createRuleTask(ruleTaskInputParameter);

		} catch (Throwable e) {
			logger.info("createNextRuleTask失败:", e);
			throw new RxCrawlerException(e.getMessage());
		}
	}
	
	@Override
	public void createNextRuleTasks(RxTask[] tasks) throws RxCrawlerException {

		checkInterrupted();

		if (tasks.length <= 100) {
			invokeRuleTask(tasks);
		} else {
			int index100 = tasks.length / 100;
			int lastPart = tasks.length % 100;

			for (int j = 1; j <= index100; j++) {

				RxTask splitedTasks[] = Arrays.copyOfRange(tasks, j * 100 - 100, j * 100);
				invokeRuleTask(splitedTasks);
			}

			RxTask lastPartTasks[] = Arrays.copyOfRange(tasks, index100 * 100, (index100 * 100 + lastPart));
			invokeRuleTask(lastPartTasks);

		}

	}


	private void invokeRuleTask(RxTask[] tasks) {
		try {
			RuleTaskInputParameter ruleTaskInputParameter = new RuleTaskInputParameter();

			ruleTaskInputParameter.setEnv("TEST");
			ruleTaskInputParameter.setUserSeq(String.valueOf(userSeq));
			ruleTaskInputParameter.setAppSeq(appSeq);
			ruleTaskInputParameter.setScenarioIndex(scenarioIndex);
			ruleTaskInputParameter.setRuleIndex(ruleIndex + 1);
			ruleTaskInputParameter.setSourceTaskSeq(sourceTaskSeq);
			ruleTaskInputParameter.setScheduledType(scheduleType);
			ruleTaskInputParameter.setAccountIndex(accountIndex);
			ruleTaskInputParameter.setBatchSize(tasks.length);

			String[] taskValues = new String[tasks.length];
			
			for (int i = 0; i < tasks.length; i++) {
				
				StringBuffer taskValue = new StringBuffer();

				if (tasks[i].getV1() != null) {
					taskValue = taskValue.append(tasks[i].getV1() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV2() != null) {
					taskValue = taskValue.append(tasks[i].getV2() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV3() != null) {
					taskValue = taskValue.append(tasks[i].getV3() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV4() != null) {
					taskValue = taskValue.append(tasks[i].getV4() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV5() != null) {
					taskValue = taskValue.append(tasks[i].getV5() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV6() != null) {
					taskValue = taskValue.append(tasks[i].getV6() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV7() != null) {
					taskValue = taskValue.append(tasks[i].getV7() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV8() != null) {
					taskValue = taskValue.append(tasks[i].getV8() + "|");
				} else {
					taskValue = taskValue.append("-1|");
				}

				if (tasks[i].getV9() != null) {
					taskValue = taskValue.append(tasks[i].getV9());
				} else {
					taskValue = taskValue.append("-1");
				}
				
				logger.info("single taskValues:" + taskValue);
				taskValues[i] = taskValue.toString();
			}
			
			ruleTaskInputParameter.setTaskValues(taskValues);

			AppFacade.getInstance().createRuleTasks(ruleTaskInputParameter);

		} catch (Throwable e) {
			logger.info("createNextRuleTask失败:", e);
			throw new RxCrawlerException(e.getMessage());
		}
	}
	
	@Override
	public void log(String logMessage) {
		
		logger.info("来自app的log:" + logMessage);
		
		checkInterrupted();
		
		if (!"TEST".equals(scheduleType)) {
			
			return;
		}
		
		if (logMessage.length() > Constant.APPTASK_LOG_KEY.APP_LOG_MSG_LEN) {
			logMessage = logMessage.substring(0, Constant.APPTASK_LOG_KEY.APP_LOG_MSG_LEN);
		}

		try {
			Rules rule = ConfigFacade.getInstance().getRules(appSeq, scenarioIndex, ruleIndex);
	        int ruleVersion = rule.getVersion();
	        
			String message = Constant.APPTASK_LOG_KEY.APP_LOG_STATE_RUNNING
		            + "|" + new SimpleDateFormat("HH:mm:ss").format(new Date())
		            + "|" +  logMessage.replace("|", "-");
			
			AppFacade.getInstance().pushAppTaskLog(appSeq, scenarioIndex, ruleIndex, ruleVersion, scheduleType, message);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void logScreen() {
		if (!"TEST".equals(scheduleType)) {
			return;
		}
		logger.info("logScreen:" + appSeq);
		// 网页截图http://taskhost/App713_Download_App_LogScreen.php?userUuid={userUuid}&app=111&imageName=20180227101021321.png
		String imageUrl = rxCrawler.logScreen(appSeq);
		log(imageUrl);
	}
	
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}
	
}
