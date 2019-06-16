package com.ruixuesoft.crawler.open;

public class RxResult {
	    // 记录任务完成的状态,app可以自己定义
		private int finishCode = 200;
        private int records = 0; 
		// 提供给app，方便记录一些参考值。
		private String result1 = "-1";
		private String result2 = "-1";
		private String result3 = "-1";

		private int lastLogIndex = -1;
		private String abortedException = "-1";

		public RxResult() {
		}
		
		public RxResult(int finishCode) {
			this.finishCode = finishCode;
		}
		
		public int getFinishCode() {
			return finishCode;
		}

		public void setFinishCode(int finishCode) {
			this.finishCode = finishCode;
		}

		public int getRecords() {
			return records;
		}

		public void setRecords(int records) {
			this.records = records;
		}
		
		public String getResult1() {
			return result1;
		}

		public void setResult1(String result1) {
			this.result1 = result1;
		}

		public String getResult2() {
			return result2;
		}

		public void setResult2(String result2) {
			this.result2 = result2;
		}

		public String getResult3() {
			return result3;
		}

		public void setResult3(String result3) {
			this.result3 = result3;
		}

		public int getLastLogIndex() {
			return lastLogIndex;
		}

		public void setLastLogIndex(int lastLogIndex) {
			this.lastLogIndex = lastLogIndex;
		}

		public String getAbortedException() {
			return abortedException;
		}

		public void setAbortedException(String abortedException) {
			this.abortedException = abortedException;
		}
	
}
