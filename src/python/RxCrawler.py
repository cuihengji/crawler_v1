# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: qiuchunwei
'''
from RxNode import * 
from selenium.webdriver.common.keys import Keys
from selenium.common.exceptions import UnexpectedAlertPresentException
from selenium.webdriver.remote.webelement import *
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.common.by import By
from time import sleep
from RxCrawlerException import *


class RxCrawler(object):

    def __init__(self, driver, accounting):

        self.driver = driver
        self.accounting = accounting
        self.tabwindow = TabWindow()
    
    def open(self, url, xpath="-1", wait_seconds=-1):
        try:
            self.driver.get(url)
            # 默认打开URL后等待3秒钟
            if(xpath == "-1") and (wait_seconds == -1):
                wait_seconds = 3
                sleep(wait_seconds)
            elif(xpath == "-1") and (wait_seconds != -1):
                if(wait_seconds > 0):
                    sleep(wait_seconds)
            elif(xpath != "-1") and (wait_seconds == -1):
                wait_seconds = 60
                WebDriverWait(self.driver, wait_seconds, 0.1).until(expected_conditions.presence_of_element_located((By.XPATH, xpath))) 
            else:
                if(wait_seconds > 0):  
                    WebDriverWait(self.driver, wait_seconds, 0.1).until(expected_conditions.presence_of_element_located((By.XPATH, xpath)))              
        except BaseException as e:
            raise RxCrawlerException(999, "打开指定的URL异常--" + url + "  URL的输入格式为 http://www.web2data.com")
         
         
    def get_node_by_xpath(self, xpath):
         try:
            element = self.driver.find_element_by_xpath(xpath)
            rxnode = RxNode(self.driver, element, xpath, self.accounting, self.tabwindow)
            return rxnode
         except BaseException as e:
             return None
        
        
    def get_nodelist_by_xpath(self, xpath):
        try:
            nodelist =[]
            element_list = self.driver.find_elements_by_xpath(xpath)
            for element in element_list:
                rxnode = RxNode(self.driver, element, xpath, self.accounting, self.tabwindow)
                nodelist.append(rxnode)
            return nodelist
        except BaseException as e:
            return None
    
    
    def get_selectnode_by_xpath(self, xpath):
        try:
            element = self.driver.find_element_by_xpath(xpath)
            return Select(element)
        except BaseException as e:
            return None
    
    
    def input(self, xpath, text):
        try:   
            element = self.driver.find_element_by_xpath(xpath)
            element.send_keys(text)
        except BaseException as e:
            raise RxCrawlerException(999, "不能找输入指定的文本--" + text)
        
        
    def clear(self, xpath):
        try: 
            element = self.driver.find_element_by_xpath(xpath)
            element.clear()
        except BaseException as e:
            raise RxCrawlerException(999, "不能清除指定xpath的文本--" + xpath)
        
        
    def execute_script(self, jscript):
        try: 
            return self.driver.execute_script(jscript)
        except BaseException as e:
            raise RxCrawlerException(999, "不能执行javascript脚本--" + jscript)
        
        
    def back(self):
        try:
            self.driver.back()
        except BaseException as e:
            raise RxCrawlerException(999, "不能返回到指定的的URL")

        
    def forward(self):
        try:
            self.driver.forward()
        except BaseException as e:
            raise RxCrawlerException(999, "不能前进到指定的URL")    

        
    def refresh(self):
        try:
            self.driver.refresh() 
        except BaseException as e:
            raise RxCrawlerException(999, "不能刷新当前页面")               
     
         
    def get_page_source(self):
        try:
            return self.driver.page_source
        except BaseException as e:
            raise RxCrawlerException(999, "不能得到页面的page source")     
    
    
    def get_title(self):
        try:
            return self.driver.title
        except BaseException as e:
            raise RxCrawlerException(999, "不能得到页面的title")    
    
      
    def scroll_to_top(self):
        try:
            self.driver.execute_script("window.scrollTo(0, 0)")
    #         for i in range(1,20):
    #                 self.driver.find_element_by_xpath("/html/body").send_keys(Keys.UP)  
        except BaseException as e:
            raise RxCrawlerException(999, "不能滚动到页面顶部")    
    
                
    def scroll_to_bottom(self):
        try:
            #移动到页面最底部  
            self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight)")
        except BaseException as e:
            raise RxCrawlerException(999, "不能滚动到页面底部")    
             
                
    def scroll(self, pixels):
        try:
            scroll_js = "window.scrollTo(0, " + str(pixels) + ")"
            self.driver.execute_script(scroll_js)
        except BaseException as e:
            raise RxCrawlerException(999, "不能滚动到指定位置")           


    def is_alert_displayed(self):
        try:
            is_alert_displayed = False
            self.driver.title
        except UnexpectedAlertPresentException:
            is_alert_displayed = True
        return is_alert_displayed 

    
    def get_alert_text(self):
        try:
            myalert = self.driver.switch_to.alert
            return myalert.text
        except BaseException as e:
            raise RxCrawlerException(999, "不能得到弹出框的文本")  
    
    
    def close_alert(self):
        try:
            self.driver.switch_to.alert.accept()
        except BaseException as e:
            raise RxCrawlerException(999, "不能关闭弹出框")  
    
    
    def sleep(self, time):
        try:
            sleep(time)
        except BaseException as e:
            raise RxCrawlerException(999, "不能休眠指定的时间")

    def is_new_tab_opened(self):
        print("is_new_tab_opened start")
        try:
            new_tab_index = self.tabwindow.new_tab_window_handle_index
            print("new_tab_index:" + str(new_tab_index))
            if new_tab_index > 0:
                return True
            return False
            print("is_new_tab_opened end")
        except BaseException as e:
            print(str(e))
            raise RxCrawlerException(999, "判断是否出现新tab异常")
    
    def switch_to_new_tab(self):
        print("switch_to_new_tab start")
        try:
            new_tab_index = self.tabwindow.new_tab_window_handle_index
            print("new_tab_index:" + str(new_tab_index))
            if new_tab_index > 0:
                self.driver.switch_to_window(self.tabwindow.tab_window_handle_list[new_tab_index])
                print("switch_to_new_tab:" + str(new_tab_index))
            print("switch_to_new_tab end")
        except BaseException as e:
            print(str(e))
            raise RxCrawlerException(999, "switch_to_new_tab异常")
    
    def close_new_tab(self):
        print("close_new_tab start")
        try:
            cur_tab_index = self.tabwindow.new_tab_window_handle_index
            print("cur_tab_index:" + str(cur_tab_index))
            if cur_tab_index > 0:
                self.driver.switch_to_window(self.tabwindow.tab_window_handle_list[cur_tab_index])
                self.driver.close()
                
                self.driver.switch_to_window(self.tabwindow.tab_window_handle_list[cur_tab_index-1])
                del self.tabwindow.tab_window_handle_list[cur_tab_index]
                self.tabwindow.new_tab_window_handle_index = cur_tab_index - 1
            print("close_new_tab end")
        except BaseException as e:
            print(str(e))
            raise RxCrawlerException(999, "close_new_tab异常")

class TabWindow(object):
    
        def __init__(self, tab_window_handle_list=[], new_tab_window_handle_index = -1):
            self.tab_window_handle_list = tab_window_handle_list
            self.new_tab_window_handle_index = new_tab_window_handle_index
