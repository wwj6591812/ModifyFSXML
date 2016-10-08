package com.chinatelecom.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinatelecom.common.Constant;
import com.chinatelecom.common.JAVAwithXML;
import com.chinatelecom.common.PropertiesHelper;
import com.chinatelecom.common.PropertiesHelper.PropertiesConfig;
import com.chinatelecom.controller.FScontroller;
import com.chinatelecom.dao.FSPropertiesDao;
import com.chinatelecom.model.Allocations;
import com.chinatelecom.model.Pool;
import com.chinatelecom.model.User;

public class UpdateFSXmlFromDataBase extends Thread {

	private static Log logger = LogFactory
			.getLog(UpdateFSXmlFromDataBase.class);
	private static FScontroller fsc = new FScontroller();
	private static FSPropertiesDao dao = new FSPropertiesDao();
	private static PropertiesConfig conf;

	// 每隔5分钟，读取数据库，更新fair-scheduler.xml
	public void run() {
		Properties prop = null;
		try {
			prop = PropertiesHelper.load("modifyQueueACL.properties");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		conf = PropertiesHelper.createConfig(prop);
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// 将XML文件转化为JAVA内容树
				Allocations allocations = JAVAwithXML.transferXMLToJava();
				fsc.modifyTheQueueNameFromXML(allocations.getPools(), "");

				// 读取数据库，并且根据数据库中的内容，更新java内容树
				readDatebaseAndupdateDate(allocations);

				// 将JAVA内容树转化为xml文件
				JAVAwithXML.transferJavaToXML(allocations);
				Thread.sleep(conf.get(
						Constant.READ_DATEBASE_ANDUPDATEDATE_SLEEPMS, 300000));
			} catch (InterruptedException e) {
				logger.warn(
						"UpdateFSXmlFromDataBase thread interrupted. Exiting.",
						e);
				return;
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 分别连接三个表，分别读取三个表中的数据，然后更新
	void readDatebaseAndupdateDate(Allocations allocations) throws Exception {
		readPoolPropertiesAndUpdate(allocations);
		readUsersPropertiesAndUpdate(allocations);
		readDefaultPropertiesInDatebase(allocations);
	}

	void readPoolPropertiesAndUpdate(Allocations allocations) throws Exception {
		ArrayList<Pool> poolsInDatebase = new ArrayList<Pool>();
		poolsInDatebase = dao.queryPoolProperties();
		if (!poolsInDatebase.isEmpty()) {
			for (Pool pool : poolsInDatebase) {
				Pool targetPool = fsc.findPool(allocations.getPools(),
						pool.getDisplayName());
				if (targetPool != null) {
					if (pool.getMinResources() != null)
						targetPool.setMinResources(pool.getMinResources());
					if (pool.getMaxResources() != null)
						targetPool.setMaxResources(pool.getMaxResources());
					if (pool.getMaxRunningApps() != null)
						targetPool.setMaxRunningApps(pool.getMaxRunningApps());
					if (pool.getMaxAMShare() != null)
						targetPool.setMaxAMShare(pool.getMaxAMShare());
					if (pool.getWeight() != null)
						targetPool.setWeight(pool.getWeight());
					if (pool.getMinSharePreemptionTimeout() != null)
						targetPool.setMinSharePreemptionTimeout(pool
								.getMinSharePreemptionTimeout());
					if (pool.getFairSharePreemptionTimeout() != null)
						targetPool.setFairSharePreemptionTimeout(pool
								.getFairSharePreemptionTimeout());
					if (pool.getFairSharePreemptionThreshold() != null)
						targetPool.setFairSharePreemptionThreshold(pool
								.getFairSharePreemptionThreshold());
					if (pool.getSchedulingMode() != null)
						targetPool.setSchedulingMode(pool.getSchedulingMode());
					if (pool.getAclSubmitApps() != null)
						targetPool.setAclSubmitApps(pool.getAclSubmitApps());
					if (pool.getAclAdministerApps() != null)
						targetPool.setAclAdministerApps(pool
								.getAclAdministerApps());
				} else {
					logger.error("name in database is not right!");
					break;
				}
			}
		}
	}

	void readUsersPropertiesAndUpdate(Allocations allocations) throws Exception {
		ArrayList<User> usersInDatebase = new ArrayList<User>();
		usersInDatebase = dao.queryUserProperties();
		if (!usersInDatebase.isEmpty()) {
			for (User user1 : usersInDatebase) {
				boolean flag = true;
				for (User user2 : allocations.getUsers()) {
					if (user1.getName().equals(user2.getName())) {
						user2.setMaxRunningJobs(user1.getMaxRunningJobs());
						flag = false;
						break;
					}
				}
				if (flag) {
					User user = new User();
					user.setName(user1.getName());
					user.setMaxRunningJobs(user1.getMaxRunningJobs());
					allocations.getUsers().add(user);
				}
			}
		}
	}

	void readDefaultPropertiesInDatebase(Allocations allocations)
			throws Exception {
		ArrayList<Allocations> defaultPropertiesInDatebase = new ArrayList<Allocations>();
		defaultPropertiesInDatebase = dao.queryDefaultProperties();
		if (!defaultPropertiesInDatebase.isEmpty()) {
			for (Allocations allocationsIndatabase : defaultPropertiesInDatebase) {
				if (allocationsIndatabase.getQueueMaxAppsDefault() != null)
					allocations.setQueueMaxAppsDefault(allocationsIndatabase
							.getQueueMaxAppsDefault());
				if (allocationsIndatabase.getDefaultMinSharePreemptionTimeout() != null)
					allocations
							.setDefaultMinSharePreemptionTimeout(allocationsIndatabase
									.getDefaultMinSharePreemptionTimeout());
				if (allocationsIndatabase.getDefaultQueueSchedulingMode() != null)
					allocations
							.setDefaultQueueSchedulingMode(allocationsIndatabase
									.getDefaultQueueSchedulingMode());
				if (allocationsIndatabase.getUserMaxAppsDefault() != null)
					allocations.setUserMaxAppsDefault(allocationsIndatabase
							.getUserMaxAppsDefault());
				if (allocationsIndatabase
						.getDefaultFairSharePreemptionTimeout() != null)
					allocations
							.setDefaultFairSharePreemptionTimeout(allocationsIndatabase
									.getDefaultFairSharePreemptionTimeout());
				if (allocationsIndatabase
						.getDefaultFairSharePreemptionThreshold() != null)
					allocations
							.setDefaultFairSharePreemptionThreshold(allocationsIndatabase
									.getDefaultFairSharePreemptionThreshold());
				if (allocationsIndatabase.getQueueMaxAMShareDefault() != null)
					allocations.setQueueMaxAMShareDefault(allocationsIndatabase
							.getQueueMaxAMShareDefault());
			}
		}
	}

}
