package com.web2data._global;

public class R {

	
	// 任何位置异常,都按照如下设置
	//	999		未知异常
	
	
	// 参照以下编码方式进行定义
	public static int CODE_990 = 990; // Session,java - 990: worker 为 aTask 的执行 没有做好准备工作, 用于跟踪出问题的具体准备步骤
	
	//	90X		Task 消息格式错误,等其他非常不常见且基本不会发生的严重错误
	//	91X		找不到 Crawler等, 与Crawler相关的错误
	//	92X		Database API calling exception
	//	93X		与Jar包相关的 exception
	//	94X		App API calling exception
	//	95X		Scheduler API calling exception
	//	96X		Message API calling exception
	//	97X		Proxy API calling exception
	//	98X		Policy API calling exception
	public static int CODE_999 = 999;		// 未知异常
	
	
	// 811 - 829 主要用于在get()方法检测各种异常
	public static int CODE_810 = 810; // driver is null
	public static int CODE_811 = 811;
	public static int CODE_812 = 812;
	public static int CODE_813 = 813;
	public static int CODE_814 = 814;
	public static int CODE_815 = 815;
	public static int CODE_816 = 816;
	public static int CODE_817 = 817;
	public static int CODE_818 = 818;
	public static int CODE_819 = 819;
	public static int CODE_820 = 820;
	public static int CODE_821 = 821;
	public static int CODE_822 = 822;
	public static int CODE_823 = 823;
	public static int CODE_824 = 824;
	public static int CODE_825 = 825;
	public static int CODE_826 = 826;
	public static int CODE_827 = 827;
	public static int CODE_828 = 828;
	public static int CODE_829 = 829;
	
	
	public static int CODE_890 = 890; // Worker.java -  890: 返回的RxResult为 null
	public static int CODE_891 = 891; // Session,java - 891: Task 被手工终止
	public static int CODE_892 = 892; // Session,java - 892: Task 执行超时
	public static int CODE_893 = 893; // Session,java - 893: Task 其他原因被取消执行
	
	
//    public static final int SYSTEM_ERR_CODE_900 = 900; // UNKNOWN_EXCEPTION
//    public static final int SYSTEM_ERR_CODE_901 = 901; // 消息格式错误
//    public static final int SYSTEM_ERR_CODE_902 = 902; // NotifySchedulerTaskStarted Exception
//    public static final int SYSTEM_ERR_CODE_903 = 903; // NotifyAppTaskStarted Exception
//    public static final int SYSTEM_ERR_CODE_904 = 904; // 取得app失败
//    public static final int SYSTEM_ERR_CODE_905 = 905; // 下载jar包失败
//    public static final int SYSTEM_ERR_CODE_906 = 906; // GetAppTaskInfoAndDataSchemaInfo Exception
//    public static final int SYSTEM_ERR_CODE_907 = 907; // NotifySchedulerTaskFinished Exception
//    public static final int SYSTEM_ERR_CODE_908 = 908; // NotifyAppTaskFinished Exception
//    public static final int SYSTEM_ERR_CODE_909 = 909; // 数据库连接异常
//    public static final int SYSTEM_ERR_CODE_910 = 910; // 数据库连接无效
//    public static final int SYSTEM_ERR_CODE_911 = 911; // clearBrowser异常
//    public static final int SYSTEM_ERR_CODE_912 = 912; // updateProxy异常
//    public static final int SYSTEM_ERR_CODE_999 = 999; // 无效爬取过程的未知异常
	
	
	
	
	
	//	在一个Task开始执行前，应该做清理工作，确保Tab页只有一个
	//	在一个Task结束后，特别是异常接触后，应该做清理工作，确保Tab页只有一个
	//
	//	9xx 的问题，都是平台系统引起的执行前和执行后通知阶段发现的问题
	//	与开发者没有关系
	//	8xx 的问题，是APP执行发现的问题，比如：浏览器的问题，代理的问题，或者APP异常没有捕获跳出的问题
	//	7xx 保留不用，用于做区隔
	//	6xx 其他一些严重的问题，或开发者另做他用
	//	5xx server端发生了问题
	//	4xx request 参数引起的问题
	//	3xx 模糊的情况
	//	2xx 正确的各种情况
	//	1xx 用于调试连通性 和 版本使用，非业务使用
	
}
