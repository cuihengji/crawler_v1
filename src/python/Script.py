# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: qiuchunwei
'''
from time import sleep
from RxResult import *
from RxTask import *
def execute(rxtask, rxcrawler, rxdatabase):

    rxtask.log("=====>python app 执行开始")
    
    '''打开网页，抓取数据''' 
    driver = rxcrawler.getDriver()
    driver.get("http://www.163.com/");
    sleep(5)

    '''插入数据''' 
    sql = "INSERT INTO test (company_name, company_address) VALUES (%s, %s)"
    params = ("瑞雪科技2017120500991", "黄浦路100号2017120500991")
    seq = rxdatabase.insert(sql, params)
    rxtask.log("=====>插入数据成功！插入后最新的id：" + str(seq))
    sql = "select * from test"
    rows = rxdatabase.query_all(sql, params = None)
    for row in rows:
        rxtask.log("=====>公司id：" + str(row["id"]))
        rxtask.log("=====>公司名：" + row["company_name"])
        rxtask.log("=====>公司地址：" + row["company_address"])

    sql = "select * from test where id = %s"
    row = rxdatabase.query_one(sql, params = (19745,))
    rxtask.log("=====>公司id：" + str(row["id"]))
    rxtask.log("=====>公司名：" + row["company_name"])
    rxtask.log("=====>公司地址：" + row["company_address"])

    '''创建任务'''
    task = RxTask()
    task.v1 = rxtask.v1
    task.v2 = 2
    task.v3 = 3
    task.v4 = 4
    task.v5 = 5
    task.v6 = 6
    task.v7 = 7
    task.v8 = 8
    task.v9 = 9
    rxtask.create_next_rule_task(task)

    rxresult = RxResult(200)
    rxresult.result1 = "结果1"
    rxresult.result2 = "结果2"
    rxresult.result3 = "结果3"
    rxresult.records = 10
    rxresult.lastLogIndex = "100"
    rxresult.abortedException ="发生异常！"
    rxtask.log("=====>python app 执行结束")
    return rxresult