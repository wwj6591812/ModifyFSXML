package com.chinatelecom.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.chinatelecom.common.Constant;
import com.chinatelecom.common.PropertiesHelper;
import com.chinatelecom.common.PropertiesHelper.PropertiesConfig;
import com.chinatelecom.dao.FSPropertiesDao;
import com.chinatelecom.server.UpdateFSXmlFromDataBase;

public class Controller {

	private static PropertiesConfig conf;

	public static void main(String[] args) throws SQLException, IOException {

		// Properties prop = new Properties();
		// InputStream in =
		// Object.class.getResourceAsStream("modifyQueueACL.properties");
		// try {
		// prop.load(in);
		// param1 = prop.getProperty("initYears1").trim();
		// param2 = prop.getProperty("initYears2").trim();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		Properties prop = PropertiesHelper.load("modifyQueueACL.properties");
		conf = PropertiesHelper.createConfig(prop);
		System.out.println(conf.get(Constant.FAIR_SCHEDULER_ADDRESS,
				"xxx/fair-scheduler.xml"));

		// UpdateFSXmlFromDataBase updateFSXmlFromDataBase = new
		// UpdateFSXmlFromDataBase();
		// updateFSXmlFromDataBase.start();
		//
		// FSPropertiesDao dao = new FSPropertiesDao();
		// String poolName = "root.pool1";
		// String newValue = "wwj";
		// String targetProperties = "";
		// String userName = "user";
		// dao.updatePoolProperties(poolName, "minResources", newValue);
		// dao.updateUserProperties(userName, targetProperties, newValue);
		// dao.updateDefaultProperties(targetProperties, newValue);
	}
}