# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: qiuchunwei
'''
import sys
import json
from selenium import webdriver
import traceback
import platform

from RxCrawler import *
from RxTask import *
from RxDatabase import *
from RxResult import *
from RxCrawlerException import *
from Script import execute
#不安全/非法调用禁止黑名单，避免script.py import 违规库, 避免crawler被干扰／控制／破坏
# blacklist = ['os', 'ctypes']
# for mod in blacklist:
#     i = __import__(mod)
#     sys.modules[mod] = None

# from script import execute
def main():
    if "Windows" in platform.platform():
        input_file_path = "C:\\flood\\session"+ str(sys.argv[1]) + "\\input.json"
    else:
         input_file_path =  "/flood/session" + str(sys.argv[1]) + "/input.json"   
    with open(input_file_path, "r") as f:
        input_json = json.load(f)

    driver_for_python = create_driver_session( input_json["chromeSessionID"], input_json["chromeUrl"] )
    
    crawler = RxCrawler(driver_for_python, accounting = None)
    
    task = RxTask(input_json["task"], input_json["appTaskHost"], input_json["apiPort"])
    
    dataBase = RxDatabase(input_json["db"])

    result = RxResult()
    try:
        result = execute(task, crawler, dataBase)
    except RxCrawlerException as e_rx:
        result.finishCode = e_rx.get_exception_code()
        result.abortedException  = str(traceback.format_exc()) + e_rx.get_exception_message()
    except Exception as e_sys:
        result.finishCode = 999
        result.abortedException = str(traceback.format_exc())
        print("====exception in main====:" + traceback.format_exc())
    finally:
        index = str(result.abortedException).find("Script.py")
        if "Windows" in platform.platform():
            if index >= 0:
                trace_exe = str(result.abortedException)[index + 11:]
                print("====trace_exe===:" + trace_exe)
                aborted_exception = trace_exe
                index_c = trace_exe.find('File "C:')
                if index_c >= 0:
                    trace_exe_c = trace_exe[:index_c]
                    print("====trace_exe_c===:" + trace_exe_c)
                    aborted_exception = trace_exe_c
                index_rxcrawler_exception = trace_exe.find(".RxCrawlerException")
                if index_rxcrawler_exception >= 0:
                    trace_exe_rxcrawler_exception = trace_exe[index_rxcrawler_exception+20:]
                    print("====trace_exe_rxcrawler_exception===:" + trace_exe_rxcrawler_exception)
                    aborted_exception = aborted_exception + trace_exe_rxcrawler_exception
                result.abortedException = aborted_exception
        else:
            if index >= 0:
                trace_exe = str(result.abortedException)[index + 11:]
                print("====trace_exe===:" + trace_exe)
                aborted_exception = trace_exe
                index_c = trace_exe.find('File "/flood')
                if index_c >= 0:
                    trace_exe_c = trace_exe[:index_c]
                    print("====trace_exe_c===:" + trace_exe_c)
                    aborted_exception = trace_exe_c
                index_rxcrawler_exception = trace_exe.find(".RxCrawlerException")
                if index_rxcrawler_exception >= 0:
                    trace_exe_rxcrawler_exception = trace_exe[index_rxcrawler_exception+20:]
                    print("====trace_exe_rxcrawler_exception===:" + trace_exe_rxcrawler_exception)
                    aborted_exception = aborted_exception + trace_exe_rxcrawler_exception
                result.abortedException = aborted_exception
        print("====exceptions===:" + result.abortedException)
        result_str = json.dumps(result.__dict__, default=lambda o: o.__dict__, sort_keys=False)

    if "Windows" in platform.platform():
        out_file_path = "C:\\flood\\session" + str(task.session_index) + "\\output.json"
    else:
        out_file_path = "/flood/session" + str(task.session_index) + "/output.json"

    with open(out_file_path,"w") as f:
        f.write(result_str)

def create_driver_session(session_id, executor_url):
    from selenium.webdriver.remote.webdriver import WebDriver as RemoteWebDriver

    # Save the original function, so we can revert our patch
    org_command_execute = RemoteWebDriver.execute

    def new_command_execute(self, command, params = {'status': True}):
        if command == "newSession":
            # Mock the response
            return {'success': 0, 'value': None, 'status':True, 'sessionId': session_id}
        else:
            return org_command_execute(self, command, params)

    # Patch the function before creating the driver object
    RemoteWebDriver.execute = new_command_execute

    new_driver = webdriver.Remote(command_executor=executor_url, desired_capabilities={})
    new_driver.session_id = session_id

    # Replace the patched function with original function
    RemoteWebDriver.execute = org_command_execute

    return new_driver

    
main()
