# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: qiuchunwei
'''
import time
import json
import requests
from requests import exceptions
from RxCrawlerException import *
class RxTask(object):
    '''
    classdocs
    '''
    def __init__(self, task={}, app_task_host="-1", api_port=80):
        '''
        Constructor
        '''
        self.__user_seq = task["userSeq"] if "userSeq" in task.keys() else -1

        self.__app_seq = task["appSeq"] if "appSeq" in task.keys() else -1

        self.__scenario_index = task["scenarioIndex"] if "scenarioIndex" in task.keys() else -1

        self.__rule_index = task["ruleIndex"] if "ruleIndex" in task.keys() else -1

        self.__rule_version = task["ruleVersion"] if "ruleVersion" in task.keys() else -1
        
        self.__task_seq = task["taskSeq"] if "taskSeq" in task.keys() else -1

        self.__source_task_seq = task["sourceTaskSeq"] if "sourceTaskSeq" in task.keys() else -1
    
        self.__account_index = task["accountIndex"] if "accountIndex" in task.keys() else -1
            
        self.__user_name = task["userName"] if "userName" in task.keys() else "-1"
        
        self.__pass_word = task["passWord"] if "passWord" in task.keys() else "-1"
        
        self.__schedule_type = task["scheduleType"] if "scheduleType" in task.keys() else "JOB"
        
        self.__crawler_seq = task["crawlerSeq"] if "crawlerSeq" in task.keys() else -1

        self.__crawler_host_ip = task["crawlerHostIP"] if "crawlerHostIP" in task.keys() else "-1"

        self.session_index = task["sessionIndex"] if "sessionIndex" in task.keys() else -1

        self.v1 = task["v1"] if "v1" in task.keys() else "-1"
        self.v2 = task["v2"] if "v2" in task.keys() else "-1"
        self.v3 = task["v3"] if "v3" in task.keys() else "-1"
        self.v4 = task["v4"] if "v4" in task.keys() else "-1"
        self.v5 = task["v5"] if "v5" in task.keys() else "-1"
        self.v6 = task["v6"] if "v6" in task.keys() else "-1"
        self.v7 = task["v7"] if "v7" in task.keys() else "-1"
        self.v8 = task["v8"] if "v8" in task.keys() else "-1"
        self.v9 = task["v9"] if "v9" in task.keys() else "-1"
        self.__app_task_host = app_task_host
        self.__api_port = api_port
        
    def log(self, log_message):
        if self.__schedule_type == "TEST":
            message = "RUNNING" + "|" + time.strftime("%H:%M:%S", time.localtime()) + "|" +  str(log_message).replace("|", "-");
            url = "http://" + self.__app_task_host + ":" + self.__api_port + "/app/APP707_PushAppTaskLog.php"
            data = {
                    "appSeq":self.__app_seq,
                    "scenarioIndex":self.__scenario_index,
                    "ruleIndex":self.__rule_index,
                    "ruleVersion":self.__rule_version,
                    "scheduleType":self.__schedule_type,
                    "logMessage":message}
            try:
                r = requests.post(url, data)
                print("log:" + log_message)
            except BaseException as be:
                print(str(be))
                pass
        
    def create_next_rule_task(self, task):
        
        url = "http://" + self.__app_task_host + ":" + self.__api_port + "/app/APP701_CreateRuleTask.php"
        payload = {
                    "userSeq":self.__user_seq,
                    "appSeq":self.__app_seq,
                    "scenarioIndex":self.__scenario_index,
                    "ruleIndex":self.__rule_index + 1,
                    "accountIndex":self.__account_index,
                    "sourceTaskSeq":self.__source_task_seq,
                    "scheduledType":self.__schedule_type, 
                    "v1":task.v1, "v2":task.v2, "v3":task.v3,
                    "v4":task.v4, "v5":task.v5, "v6":task.v6,
                    "v7":task.v7, "v8":task.v8, "v9":task.v9
                    }
        try:
            r = requests.get(url, params=payload)
            print(r.url)
            print(r.text)
            response = json.loads(r.text)
            if response["code"]!=200:
                raise RxCrawlerException(999, "任务创建失败!" + str(r.text))
        except exceptions.RequestException as e:
            raise RxCrawlerException(999, "网络请求失败!" + str(e))