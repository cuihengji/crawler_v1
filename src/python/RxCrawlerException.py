# -*- coding: UTF-8 -*-
'''
Created on 2017��12��8��

@author: qiuchunwei
'''

class RxCrawlerException(Exception):
    '''
    classdocs
    '''


    def __init__(self, exception_code= 999, exception_message = ""):
        '''
        Constructor
        '''
        Exception.__init__(self)
        self.exception_code = exception_code
        self.exception_message = exception_message
        
    def get_exception_code(self):
        return  self.exception_code
    
    def get_exception_message(self):
        return self.exception_message