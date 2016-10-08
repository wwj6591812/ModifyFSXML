package com.chinatelecom.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.chinatelecom.common.PropertiesHelper.PropertiesConfig;

public class DBUtil {

	// private static final String URL =
	// "jdbc:mysql://127.0.0.1:3306/fair_scheduler";
	// private static final String USER = "root";
	// private static final String PASSWORD = "root";

	private static PropertiesConfig conf;

	static {
		try {
			// 1、加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws IOException {
		Connection conn = null;
		Properties prop = PropertiesHelper.load("modifyQueueACL.properties");
		conf = PropertiesHelper.createConfig(prop);
		try {
			// 2、获得数据库的连接
			conn = DriverManager.getConnection(conf.get(Constant.DATABASE_URL,
					"jdbc:mysql://127.0.0.1:3306/fair_scheduler"), conf.get(
					Constant.DATABASE_USER, "root"), conf.get(
					Constant.DATABASE_PASSWORD, "root"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
