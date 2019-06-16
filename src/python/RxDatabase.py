# -*- coding: UTF-8 -*-
'''
Created on 2017年11月25日

@author: qiuchunwei
'''
import mysql.connector
import traceback
from RxCrawlerException import *
class RxDatabase(object):
    '''
    classdocs
    '''
    __config = None
    def __init__(self, db):
        '''
        Constructor
        '''
        self.__config = {
                        'host': db["ip"],
                        'user': db["username"],
                        'password': db["password"],
                        'port': db["port"],
                        'database': db["schema"],
                        'charset': 'utf8'
                       }
    
    def insert(self, sql, params=None):
        lastrowid = -1;
        try:
            cnn = mysql.connector.connect(**self.__config)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库连接异常!" + str(e))

        try:
            cursor = cnn.cursor()
            cursor.execute(sql, params)
            lastrowid = cursor.lastrowid
            cnn.commit()
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库操作异常!" + str(e))
        finally:
            cursor.close()
            cnn.close()
        return lastrowid
    
    def batch_insert(self, sql, params):
        return self.__executemany(sql, params)
    
    def update(self, sql, params=None):
        return self.__execute(sql, params)
    
    def batch_update(self, sql, params):
        return self.__executemany(sql, params)

    def delete(self, sql, params=None):
        return self.__execute(sql, params)

    def query_all(self, sql, params=None):
        rows = None
        try:
            cnn = mysql.connector.connect(**self.__config)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库连接异常!" + str(e))

        try:
            cursor = cnn.cursor(buffered=True, dictionary=True)
            cursor.execute(sql, params)
            rows = cursor.fetchall()
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库查询异常!" + str(e))
        finally:
            cursor.close()
            cnn.close()
        return rows
    
        
    def query_one(self, sql, params=None):
        row = None
        try:
            cnn = mysql.connector.connect(**self.__config)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库连接异常!" + str(e))

        try:
            cursor = cnn.cursor(buffered=True, dictionary=True)
            cursor.execute(sql, params)
            row = cursor.fetchone()
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库查询异常!" + str(e))
        finally:
            cursor.close()
            cnn.close()
        return row
    
    def create_table_by_template(self, table_name, template_table_name):
        sql_ddl = "CREATE TABLE IF NOT EXISTS %s LIKE %s" %(table_name, template_table_name)
        
        try:
            cnn = mysql.connector.connect(**self.__config)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库连接异常!" + str(e))

        try:
            cursor = cnn.cursor()
            cursor.execute(sql_ddl)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库操作异常!" + str(e))
        finally:
            cursor.close()
            cnn.close()
     
    def __execute(self, sql, params):
        rows = -1
        try:
            cnn = mysql.connector.connect(**self.__config)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库连接异常!" + str(e))

        try:
            cursor = cnn.cursor()
            cursor.execute(sql, params)
            rows = cursor.rowcount
            cnn.commit()
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库操作异常!" + str(e))
        finally:
            cursor.close()
            cnn.close()
        return rows
            
    def __executemany(self, sql, params):
        rows = -1
        try:
            cnn = mysql.connector.connect(**self.__config)
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库连接异常!" + str(e))

        try:
            cursor = cnn.cursor()
            cursor.executemany(sql, params)
            rows = cursor.rowcount
            cnn.commit()
        except mysql.connector.Error as e:
            raise RxCrawlerException(999, "数据库操作异常!" + str(e))
        finally:
            cursor.close()
            cnn.close()
        return rows

    
# db = {"ip":"103.249.252.21","port":3306,"username":"root","password":"mysql0411","schema":"user17_data"}
# database = RxDatabase(db)
# sql_insert = "INSERT INTO test (company_name, company_address) VALUES (%s, %s)"
# params_insert = ("瑞雪科技2017120400", "黄浦路100号00")
# sql_insert = "INSERT INTO test (company_name, company_address) VALUES ('瑞雪科技2017120400', '黄浦路100号00')"
# params_insert = ("瑞雪科技2017120400", "黄浦路100号00")
# id = database.insert(sql_insert,params_insert)
# print(id)
# 
# sql_batch_insert = "INSERT INTO test (company_name, company_address) VALUES (%s, %s)"
# params_batch_insert = [("瑞雪科技201712040110", "黄浦路100号10"), ("瑞雪科技201712040211", "黄浦路100号11"), ("瑞雪科技201712040312", "黄浦路100号12")]
# rows = database.batchInsert(sql_batch_insert, params_batch_insert)
# print(rows)
# sql_update = "UPDATE test SET company_name = %s, company_address = %s where id = %s"
# sql_update = "UPDATE test SET company_name = '瑞雪科技201712040399_123', company_address = '黄浦路100号68_123' where id = 19762"
# params_update = ("瑞雪科技201712040399_123", "黄浦路100号68_123", 19720)
# rows = database.update(sql_update, params_update)
# rows = database.update(sql_update)
# print(rows)

# sql_delete = "delete from test where id in (%s, %s)"
# params_delete = (19720, 19721)
# rows = database.delete(sql_delete, params_delete)
# print(rows)
# sql_delete = "delete from test where id=19762"
# rows = database.delete(sql_delete)
# print(rows)
# 
# sql_batch_update = "UPDATE test SET company_name = %s, company_address = %s WHERE id = %s"
# params_batch_update = [("瑞雪科技201712040199", "黄浦路100号661", 19722), ("瑞雪科技201712040299", "黄浦路100号671", 19723)]
# rows = database.batchUpdate(sql_batch_update, params_batch_update)
# print(rows)
# sql_query_all = "select * from test"
# rows = database.query_all(sql_query_all)
# print(rows)
# 
# sql_query_one = "select * from test where id = %s"
# row = database.query_one(sql_query_one, params = (19745,))
# print(row)

# database.create_table_by_template("test2323", "company_info_by_brand")