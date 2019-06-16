# -*- coding: UTF-8 -*-
'''
Created on 2017年12月08日

@author: 
'''
from selenium.webdriver.remote.webelement import *

class RxSelectNode(object):

    
    def __init__(self, select):
      
        self.select = select
            
    
    def select_by_index(self, index):
         self.select.select_by_index()
  
  
    def select_by_visible_text(self, text):
        self.select.select_by_visible_text()
  
        
    def select_by_value(self, value):
         self.select.select_by_value()
  
     
    