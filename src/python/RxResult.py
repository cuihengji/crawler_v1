# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: qiuchunwei
'''

class RxResult(object):
    '''
                记录app返回的结果
    '''
    
    def __init__(self, finishCode = 200):
        '''
        Constructor
        '''
        self.finishCode = finishCode
        self.records = 0
        self.result1 = "-1"
        self.result2 = "-1"
        self.result3 = "-1"
        self.lastLogIndex = -1
        self.abortedException = "-1"