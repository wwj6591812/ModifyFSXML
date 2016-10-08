package com.chinatelecom.common;

public class Constant {

	// JETTY服务的端口
	public static final String HTTP_PORT_KEY = "server.http.port";

	// fair-scheduler.xml文件，所在位置
	public static final String FAIR_SCHEDULER_ADDRESS = "fair-scheduler.address";

	// 所连接的数据库的URL、账号、密码
	public static final String DATABASE_URL = "database.url";
	public static final String DATABASE_USER = "database.user";
	public static final String DATABASE_PASSWORD = "database.password";

	// 每隔这么久的时间，读取一次数据库，并且根据数据库中的数据来更新fair-scheduler.xml
	public static final String READ_DATEBASE_ANDUPDATEDATE_SLEEPMS = "read.datebase.andupdate.sleepms";
}
