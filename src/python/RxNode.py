# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: 
'''
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.remote.webelement import *
from time import sleep

from RxCrawlerException import *

class RxNode(object):

    
    def __init__(self, driver, element, xpath, accounting, tabwindow = None):
      
        self.driver = driver
        self.accounting = accounting
        self.element = element
        self.xpath = xpath
        self.tabwindow = tabwindow
  
  
    def click(self, xpath="-1", wait_seconds=-1):
        
        try:
            size_before_click = len(self.driver.window_handles)
            print("size_before_click:" + str(size_before_click))

            self.element.click()
            
            if(xpath=="-1") and (wait_seconds==-1):
                wait_seconds = 3
                sleep(wait_seconds)
            elif(xpath=="-1") and (wait_seconds!=-1):
                if(wait_seconds >0):
                    sleep(wait_seconds)
            elif(xpath!="-1") and (wait_seconds==-1):
                wait_seconds = 60
                WebDriverWait(self.driver, wait_seconds, 0.1).until(expected_conditions.presence_of_element_located((By.XPATH, xpath))) 
            else:
                if(wait_seconds >0):  
                    WebDriverWait(self.driver, wait_seconds, 0.1).until(expected_conditions.presence_of_element_located((By.XPATH, xpath)))
                    
            size_after_click = len(self.driver.window_handles)
            print("size_after_click:" + str(size_after_click))
            self.tabwindow.tab_window_handle_list = self.driver.window_handles
            if size_after_click - size_before_click > 0:
                self.tabwindow.new_tab_window_handle_index = size_after_click - 1
            else:
                self.tabwindow.new_tab_window_handle_index = size_after_click - 1 if size_after_click > 1 else 0

        except BaseException as e:
            raise RxCrawlerException(999, "点击Node时出现错误")   
  
    
    def input(self, text):
        try:
            self.element.send_keys(text)
        except BaseException as e:
            raise RxCrawlerException(999, "不能输入指定的文本--" + text)
   
        
    def clear(self):
        try:
            self.element.clear()
        except BaseException as e:
            raise RxCrawlerException(999, "不能清除输入框内的文本")
    
        
    def get_text(self):
        try:
            return self.element.text
        except BaseException as e:
            raise RxCrawlerException(999, "不能得到指定的文本")
  
    '''
          删除 Selenium的源代码 webElement 139行的if判断后好用
    #         if self._w3c:
    #         attributeValue = self.parent.execute_script(
    #         "return (%s).apply(null, arguments);" % getAttribute_js, self, name)
    #         else:
    '''
    #not working due to the webdriver\remote\webdriver
    def get_attribute(self, name):
        try:
            return self.element.get_attribute(name)
        except BaseException as e:
            raise RxCrawlerException(999, "不能得到指定属性的内容--" + name)
  
    
    # actionChains.py line 79 def perform(self): C:\Program Files\Python36\Lib\site-packages\selenium\webdriver\common
    def move_to_node(self):
        try:
            action = ActionChains(self.driver)
            action.move_to_element(self.element).perform()
        except BaseException as e:
            raise RxCrawlerException(999, "不能一定到指定的节点")    
        
    
    '''
    webElement 修改
    '''
    def is_displayed(self):
        try:
            return self.element.is_displayed()
        except BaseException as e:
            raise RxCrawlerException(999, "不能判断指定的节点是否显示在页面") 
         
    
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
