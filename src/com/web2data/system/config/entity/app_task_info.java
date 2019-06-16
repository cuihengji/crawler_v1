package com.web2data.system.config.entity;


public class app_task_info {

//	private HashMap<String, String> taskInfo;
//
//	public HashMap<String, String> getTaskInfo() {
//		return taskInfo;
//	}
//
//	public void setTaskInfo(HashMap<String, String> taskInfo) {
//		this.taskInfo = taskInfo;
//	}
//
//	public app_task_info() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public int getValueAsInt(String key) throws Exception {
//
//		int value = 0;
//
//		if (taskInfo == null || taskInfo.size() == 0) {
//			throw new Exception("app_task_info 不存在 ");
//		} else {
//			try {
//				value = Integer.parseInt(taskInfo.get(key));
//			} catch (Exception e) {
//				throw e;
//			}
//		}
//
//		return value;
//	}
//
//	public String getValueAsString(String key) throws Exception {
//
//		String value = null;
//
//		if (taskInfo == null || taskInfo.size() == 0) {
//			
//			throw new Exception("app_task_info 不存在！ ");
//			
//		} else {
//			
//			if (!taskInfo.containsKey(key)) {
//				throw new Exception(key + "不存在！");
//			}
//			
//			value = taskInfo.get(key);
//		}
//
//		return value;
//	}
	
	private	int	seq;
	private	String	status;
	private	int	scheduleds;
	private	String	scheduled_code;
	private	String	started_code;
	private	String	finished_code;
	private	String	create_time;
	private	String	update_time;
	private	int	account_index;
	private	int	ref1_seq;
	private	int	ref2_seq;
	private	int	ref3_seq;
	private	int	ref4_seq;
	private	int	ref5_seq;
	private	int	ref6_seq;
	private	int	ref7_seq;
	private	int	ref8_seq;
	private	int	ref9_seq;
	private	int	ref10_seq;
	private	int	source_task_seq;
	private	String	source_data_table_name;
	private	int	source_data_seq;
	private	String	ref1_name;
	private	String	ref2_name;
	private	String	ref3_name;
	private	String	ref4_name;
	private	String	ref5_name;
	private	String	ref6_name;
	private	String	ref7_name;
	private	String	ref8_name;
	private	String	ref9_name;
	private	String	ref10_name;
	private	String	v1;
	private	String	v2;
	private	String	v3;
	private	String	v4;
	private	String	v5;
	private	String	v6;
	private	String	v7;
	private	String	v8;
	private	String	v9;
	private	String	v10;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getScheduleds() {
		return scheduleds;
	}
	public void setScheduleds(int scheduleds) {
		this.scheduleds = scheduleds;
	}
	public String getScheduled_code() {
		return scheduled_code;
	}
	public void setScheduled_code(String scheduled_code) {
		this.scheduled_code = scheduled_code;
	}
	public String getStarted_code() {
		return started_code;
	}
	public void setStarted_code(String started_code) {
		this.started_code = started_code;
	}
	public String getFinished_code() {
		return finished_code;
	}
	public void setFinished_code(String finished_code) {
		this.finished_code = finished_code;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public int getAccount_index() {
		return account_index;
	}
	public void setAccount_index(int account_index) {
		this.account_index = account_index;
	}
	public int getRef1_seq() {
		return ref1_seq;
	}
	public void setRef1_seq(int ref1_seq) {
		this.ref1_seq = ref1_seq;
	}
	public int getRef2_seq() {
		return ref2_seq;
	}
	public void setRef2_seq(int ref2_seq) {
		this.ref2_seq = ref2_seq;
	}
	public int getRef3_seq() {
		return ref3_seq;
	}
	public void setRef3_seq(int ref3_seq) {
		this.ref3_seq = ref3_seq;
	}
	public int getRef4_seq() {
		return ref4_seq;
	}
	public void setRef4_seq(int ref4_seq) {
		this.ref4_seq = ref4_seq;
	}
	public int getRef5_seq() {
		return ref5_seq;
	}
	public void setRef5_seq(int ref5_seq) {
		this.ref5_seq = ref5_seq;
	}
	public int getRef6_seq() {
		return ref6_seq;
	}
	public void setRef6_seq(int ref6_seq) {
		this.ref6_seq = ref6_seq;
	}
	public int getRef7_seq() {
		return ref7_seq;
	}
	public void setRef7_seq(int ref7_seq) {
		this.ref7_seq = ref7_seq;
	}
	public int getRef8_seq() {
		return ref8_seq;
	}
	public void setRef8_seq(int ref8_seq) {
		this.ref8_seq = ref8_seq;
	}
	public int getRef9_seq() {
		return ref9_seq;
	}
	public void setRef9_seq(int ref9_seq) {
		this.ref9_seq = ref9_seq;
	}
	public int getRef10_seq() {
		return ref10_seq;
	}
	public void setRef10_seq(int ref10_seq) {
		this.ref10_seq = ref10_seq;
	}
	public int getSource_task_seq() {
		return source_task_seq;
	}
	public void setSource_task_seq(int source_task_seq) {
		this.source_task_seq = source_task_seq;
	}
	public String getSource_data_table_name() {
		return source_data_table_name;
	}
	public void setSource_data_table_name(String source_data_table_name) {
		this.source_data_table_name = source_data_table_name;
	}
	public int getSource_data_seq() {
		return source_data_seq;
	}
	public void setSource_data_seq(int source_data_seq) {
		this.source_data_seq = source_data_seq;
	}
	public String getRef1_name() {
		return ref1_name;
	}
	public void setRef1_name(String ref1_name) {
		this.ref1_name = ref1_name;
	}
	public String getRef2_name() {
		return ref2_name;
	}
	public void setRef2_name(String ref2_name) {
		this.ref2_name = ref2_name;
	}
	public String getRef3_name() {
		return ref3_name;
	}
	public void setRef3_name(String ref3_name) {
		this.ref3_name = ref3_name;
	}
	public String getRef4_name() {
		return ref4_name;
	}
	public void setRef4_name(String ref4_name) {
		this.ref4_name = ref4_name;
	}
	public String getRef5_name() {
		return ref5_name;
	}
	public void setRef5_name(String ref5_name) {
		this.ref5_name = ref5_name;
	}
	public String getRef6_name() {
		return ref6_name;
	}
	public void setRef6_name(String ref6_name) {
		this.ref6_name = ref6_name;
	}
	public String getRef7_name() {
		return ref7_name;
	}
	public void setRef7_name(String ref7_name) {
		this.ref7_name = ref7_name;
	}
	public String getRef8_name() {
		return ref8_name;
	}
	public void setRef8_name(String ref8_name) {
		this.ref8_name = ref8_name;
	}
	public String getRef9_name() {
		return ref9_name;
	}
	public void setRef9_name(String ref9_name) {
		this.ref9_name = ref9_name;
	}
	public String getRef10_name() {
		return ref10_name;
	}
	public void setRef10_name(String ref10_name) {
		this.ref10_name = ref10_name;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public String getV3() {
		return v3;
	}
	public void setV3(String v3) {
		this.v3 = v3;
	}
	public String getV4() {
		return v4;
	}
	public void setV4(String v4) {
		this.v4 = v4;
	}
	public String getV5() {
		return v5;
	}
	public void setV5(String v5) {
		this.v5 = v5;
	}
	public String getV6() {
		return v6;
	}
	public void setV6(String v6) {
		this.v6 = v6;
	}
	public String getV7() {
		return v7;
	}
	public void setV7(String v7) {
		this.v7 = v7;
	}
	public String getV8() {
		return v8;
	}
	public void setV8(String v8) {
		this.v8 = v8;
	}
	public String getV9() {
		return v9;
	}
	public void setV9(String v9) {
		this.v9 = v9;
	}
	public String getV10() {
		return v10;
	}
	public void setV10(String v10) {
		this.v10 = v10;
	}
	
	

	

}
