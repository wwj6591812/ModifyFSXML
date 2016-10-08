package com.chinatelecom.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinatelecom.common.JAVAwithXML;
import com.chinatelecom.dao.FSPropertiesDao;
import com.chinatelecom.model.Allocations;
import com.chinatelecom.model.Pool;
import com.chinatelecom.model.Rule;
import com.chinatelecom.model.User;
import com.chinatelecom.server.UpdateFSXmlFromDataBase;

@Controller
@RequestMapping("/displayOrModifyFSConf")
public class FScontroller {

	private static UpdateFSXmlFromDataBase updateFSXmlFromDataBase = new UpdateFSXmlFromDataBase();
	private static Log logger = LogFactory.getLog(FScontroller.class);
	private static FSPropertiesDao dao = new FSPropertiesDao();

	@PostConstruct
	private void startUpdateFSXmlFromDataBaseThread() {
		updateFSXmlFromDataBase.start();
	}

	// 将一个队列及其所有子队列的所有信息呈现给客户端
	@RequestMapping("/displayTheQueueInfo")
	public void displayTheQueueInfo(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("display The Queue Info");
		String poolName = request.getParameter("poolName");
		if (StringUtils.isBlank(poolName)) {
			logger.error("poolName can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);

			PrintWriter pw = null;
			pw = response.getWriter();
			if (targetPool != null) {
				printQueueInfo(targetPool, pw);
			} else {
				logger.error("Pool does not exist, please re-enter！");
				pw.println("Pool does not exist, please re-enter！");
			}
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的minResources呈现给客户端
	@RequestMapping("/displayAllQueuesMinResources")
	public void displayAllQueuesMinResources(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Queues Min Resources");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeMinResourcesToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的maxResources呈现给客户端
	@RequestMapping("/displayAllQueuesMaxResources")
	public void displayAllQueuesMaxResources(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Queues MAX Resources");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeMaxResourcesToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的maxRunningApps呈现给客户端
	@RequestMapping("/displayAllQueuesMaxRunningApps")
	public void displayAllQueuesMaxRunningApps(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Queues MaxRunningApps");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeMaxRunningAppsToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的maxAMShare呈现给客户端
	@RequestMapping("/displayAllQueuesMaxAMShare")
	public void displayAllQueuesMaxAMShare(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Queues MaxAMShare");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeMaxAMShareToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的minSharePreemptionTimeout呈现给客户端
	@RequestMapping("/displayAllQueuesMinSharePreemptionTimeout")
	public void displayAllQueuesMinSharePreemptionTimeout(
			HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, IOException {
		logger.info("Display All Queues MinSharePreemptionTimeout");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeMinSharePreemptionTimeoutToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的fairSharePreemptionTimeout呈现给客户端
	@RequestMapping("/displayAllQueuesFairSharePreemptionTimeout")
	public void displayAllQueuesFairSharePreemptionTimeout(
			HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, IOException {
		logger.info("Display All Queues FairSharePreemptionTimeout");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeFairSharePreemptionTimeoutToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的fairSharePreemptionThreshold呈现给客户端
	@RequestMapping("/displayAllQueuesFairSharePreemptionThreshold")
	public void displayAllQueuesFairSharePreemptionThreshold(
			HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, IOException {
		logger.info("Display All Queues FairSharePreemptionThreshold");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeFairSharePreemptionThresholdToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的weight呈现给客户端
	@RequestMapping("/displayAllQueuesWeight")
	public void displayAllQueuesWeight(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Queues Weight");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeWeightToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的schedulingMode呈现给客户端
	@RequestMapping("/displayAllQueuesSchedulingMode")
	public void displayAllQueuesSchedulingMode(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Queues SchedulingMode");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeSchedulingModeToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有队列的aclSubmitApps和aclAdministerApps呈现给客户端
	@RequestMapping("/displayACLLists")
	public void displayACLLists(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Get All queues ACL Lists");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");

			// 将所有队列的ACL写给客户端
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				ArrayList<Pool> poolList = allocations.getPools();
				writeACLListToClient(poolList, pw);
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将所有用户最多同时运行的应用程序数目maxRunningJobs呈现给客户端
	@RequestMapping("/displayAllUsersMaxRunningJobs")
	public void displayAllUsersMaxRunningJobs(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Users MaxRunningJobs");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.println("maxRunningJobs : ");
				for (User user : allocations.getUsers()) {
					pw.println(user.getName() + " : "
							+ user.getMaxRunningJobs());
				}
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将当前设置的默认的所有属性呈现给客户端
	@RequestMapping("/displayAllDefaultProperties")
	public void displayAllDefaultProperties(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display All Default Properties");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				if (allocations.getQueueMaxAppsDefault() != null)
					pw.println("The Default queueMaxAppsDefault : "
							+ allocations.getQueueMaxAppsDefault());
				if (allocations.getDefaultMinSharePreemptionTimeout() != null)
					pw.println("The Default defaultMinSharePreemptionTimeout : "
							+ allocations.getDefaultMinSharePreemptionTimeout());
				if (allocations.getDefaultQueueSchedulingMode() != null)
					pw.println("The Default defaultPoolSchedulingMode : "
							+ allocations.getDefaultQueueSchedulingMode());
				if (allocations.getUserMaxAppsDefault() != null)
					pw.println("The Default userMaxAppsDefault : "
							+ allocations.getUserMaxAppsDefault());
				if (allocations.getDefaultFairSharePreemptionTimeout() != null)
					pw.println("The Default defaultFairSharePreemptionTimeout : "
							+ allocations
									.getDefaultFairSharePreemptionTimeout());
				if (allocations.getDefaultFairSharePreemptionThreshold() != null)
					pw.println("The Default defaultFairSharePreemptionThreshold : "
							+ allocations
									.getDefaultFairSharePreemptionThreshold());
				if (allocations.getQueueMaxAMShareDefault() != null)
					pw.println("The Default queueMaxAMShareDefault : "
							+ allocations.getQueueMaxAMShareDefault());
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 将配置文件的队列放置规则queuePlacementPolicy呈现给客户端
	@RequestMapping("/displayTheQueuePlacementPolicy")
	public void displayTheQueuePlacementPolicy(HttpServletRequest request,
			HttpServletResponse response) throws JAXBException, IOException {
		logger.info("Display The Queue Placement Policy");
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				for (Rule rule : allocations.getQueuePlacementPolicy()
						.getRules()) {
					pw.print(rule.getName());
					if (rule.getCreate() != null)
						pw.print(" , " + rule.getCreate());
					if (rule.getQueue() != null)
						pw.print(" , " + rule.getQueue());
					pw.println();
				}
			} catch (Exception e) {
				logger.error("Write Error:", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的minResources
	@RequestMapping("/modifyTheQueueMinResources")
	public void modifyTheQueueMinResources(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the queue minResources");
		String poolName = request.getParameter("poolName");
		String newMinResources = request.getParameter("newMinResources");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newMinResources)) {
			logger.error("poolName or newMinResources can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newMinResources != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"minResources", newMinResources);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的maxResources
	@RequestMapping("/modifyTheQueueMaxResources")
	public void modifyTheQueueMaxResources(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the queue maxResources");
		String poolName = request.getParameter("poolName");
		String newMaxResources = request.getParameter("newMaxResources");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newMaxResources)) {
			logger.error("poolName or newMaxResources can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newMaxResources != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"maxResources", newMaxResources);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的maxRunningApps
	@RequestMapping("/modifyTheQueueMaxRunningApps")
	public void modifyTheQueueMaxRunningApps(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the queue MaxRunningApps");
		String poolName = request.getParameter("poolName");
		String newQueueMaxRunningApps = request
				.getParameter("newQueueMaxRunningApps");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newQueueMaxRunningApps)) {
			logger.error("poolName or newQueueMaxRunningApps can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newQueueMaxRunningApps != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"maxRunningApps", newQueueMaxRunningApps);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的minSharePreemptionTimeout
	@RequestMapping("/modifyTheQueueMinSharePreemptionTimeout")
	public void modifyTheQueueMinSharePreemptionTimeout(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		logger.info("Modify the queue MinSharePreemptionTimeout");
		String poolName = request.getParameter("poolName");
		String newMinSharePreemptionTimeout = request
				.getParameter("newMinSharePreemptionTimeout");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newMinSharePreemptionTimeout)) {
			logger.error("poolName or newMinSharePreemptionTimeout can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newMinSharePreemptionTimeout != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"minSharePreemptionTimeout",
							newMinSharePreemptionTimeout);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的fairSharePreemptionTimeout
	@RequestMapping("/modifyTheQueueFairSharePreemptionTimeout")
	public void modifyTheQueueFairSharePreemptionTimeout(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		logger.info("Modify the queue FairSharePreemptionTimeout");
		String poolName = request.getParameter("poolName");
		String newFairSharePreemptionTimeout = request
				.getParameter("newFairSharePreemptionTimeout");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newFairSharePreemptionTimeout)) {
			logger.error("poolName or newFairSharePreemptionTimeout can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newFairSharePreemptionTimeout != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"fairSharePreemptionTimeout",
							newFairSharePreemptionTimeout);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的fairSharePreemptionThreshold
	@RequestMapping("/modifyTheQueueFairSharePreemptionThreshold")
	public void modifyTheQueueFairSharePreemptionThreshold(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		logger.info("Modify the queue FairSharePreemptionThreshold");
		String poolName = request.getParameter("poolName");
		String newFairSharePreemptionThreshold = request
				.getParameter("newFairSharePreemptionThreshold");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newFairSharePreemptionThreshold)) {
			logger.error("poolName or newFairSharePreemptionThreshold can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newFairSharePreemptionThreshold != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"fairSharePreemptionThreshold",
							newFairSharePreemptionThreshold);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的maxAMShare
	@RequestMapping("/modifyTheQueueMaxAMShare")
	public void modifyTheQueueMaxAMShare(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the queue MaxAMShare");
		String poolName = request.getParameter("poolName");
		String newMaxAMShare = request.getParameter("newMaxAMShare");
		if (StringUtils.isBlank(poolName) || StringUtils.isBlank(newMaxAMShare)) {
			logger.error("poolName or newMaxAMShare can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newMaxAMShare != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"maxAMShare", newMaxAMShare);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的weight
	@RequestMapping("/modifyTheQueueWeight")
	public void modifyTheQueueWeight(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the queue Weight");
		String poolName = request.getParameter("poolName");
		String newWeight = request.getParameter("newWeight");
		if (StringUtils.isBlank(poolName) || StringUtils.isBlank(newWeight)) {
			logger.error("poolName or newWeight can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newWeight != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"weight", newWeight);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的schedulingMode
	@RequestMapping("/modifyTheQueueSchedulingMode")
	public void modifyTheQueueSchedulingMode(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the queue SchedulingMode");
		String poolName = request.getParameter("poolName");
		String newSchedulingMode = request.getParameter("newSchedulingMode");
		if (StringUtils.isBlank(poolName)
				|| StringUtils.isBlank(newSchedulingMode)) {
			logger.error("poolName or newSchedulingMode can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newSchedulingMode != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"schedulingMode", newSchedulingMode);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改某个队列的aclSubmitApps和aclAdministerApps
	@RequestMapping("/modifyTheQueueACLListOrAdministerList")
	public void modifyTheQueueACLListsOrAdministerList(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		logger.info("Modify the queue aclSubmitApps and aclAdministerApps");
		String poolName = request.getParameter("poolName");
		String newAclSubmitApps = request.getParameter("newAclSubmitApps");
		String newAclAdministerApps = request
				.getParameter("newAclAdministerApps");
		if (StringUtils.isBlank(poolName)
				|| (newAclSubmitApps != null && StringUtils
						.isBlank(newAclSubmitApps))
				|| (newAclAdministerApps != null && StringUtils
						.isBlank(newAclAdministerApps))) {
			logger.error("poolName or newAclSubmitApps or newAclAdministerApps can not be null");
			return;
		}
		try {
			// 将XML文件转成JAVA内容树
			Allocations allocations = JAVAwithXML.transferXMLToJava();
			modifyTheQueueNameFromXML(allocations.getPools(), "");
			// 查找队列，如果该队列存在，则修改JAVA内容树的内容，否则给出不存在队列的提示
			ArrayList<Pool> poolList = allocations.getPools();
			Pool targetPool = findPool(poolList, poolName);
			if (targetPool != null) {
				if (newAclSubmitApps != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"aclSubmitApps", newAclSubmitApps);
				if (newAclAdministerApps != null)
					dao.updatePoolProperties(targetPool.getDisplayName(),
							"aclAdministerApps", newAclAdministerApps);
			} else {
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					logger.error("Pool does not exist, please re-enter！");
					pw.println("Pool does not exist, please re-enter！");
				} catch (IOException e) {
					logger.error("Write Error:", e);
				} finally {
					if (pw != null) {
						pw.flush();
						pw.close();
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// 修改用户最多同时运行的应用程序数目maxRunningJobs
	@RequestMapping("/modifyTheUserMaxRunningJobs")
	public void modifyTheUserMaxRunningJobs(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the User MaxRunningJobs");
		String modifyedUser = request.getParameter("modifyedUser");
		String newUserMaxRunningJobs = request
				.getParameter("newUserMaxRunningJobs");
		if (StringUtils.isBlank(modifyedUser)
				|| StringUtils.isBlank(newUserMaxRunningJobs)) {
			logger.error("modifyedUser or newUserMaxRunningJobs can not be null");
			return;
		}
		if (newUserMaxRunningJobs != null)
			dao.updateUserProperties(modifyedUser, "maxRunningJobs",
					newUserMaxRunningJobs);
		// try {
		// // 将XML文件转成JAVA内容树
		// Allocations allocations = JAVAwithXML.transferXMLToJava();
		// boolean flag = true;
		// for (User user : allocations.getUsers()) {
		// if (modifyedUser.equals(user.getName())) {
		// if(newUserMaxRunningJobs != null)
		// flag = false;
		// dao.updateUserProperties(modifyedUser, "maxRunningJobs",
		// newUserMaxRunningJobs);
		// }
		// }
		// if(flag) {
		// logger.error("modifyedUser is not in the User list of fair-scheduler.xml");
		// return ;
		// }
		// } catch (JAXBException e) {
		// e.printStackTrace();
		// }
	}

	// 修改可以配置的默认所有属性
	@RequestMapping("/modifyTheDefaultProperties")
	public void modifyTheDefaultProperties(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		logger.info("Modify the Default Properties");
		String newQueueMaxAppsDefault = request
				.getParameter("newQueueMaxAppsDefault");
		String newDefaultMinSharePreemptionTimeout = request
				.getParameter("newDefaultMinSharePreemptionTimeout");
		String newDefaultQueueSchedulingMode = request
				.getParameter("newDefaultQueueSchedulingMode");
		String newUserMaxAppsDefault = request
				.getParameter("newUserMaxAppsDefault");
		String newDefaultFairSharePreemptionTimeout = request
				.getParameter("newDefaultFairSharePreemptionTimeout");
		String newDefaultFairSharePreemptionThreshold = request
				.getParameter("newDefaultFairSharePreemptionThreshold");
		String newQueueMaxAMShareDefault = request
				.getParameter("newQueueMaxAMShareDefault");
		if ((newQueueMaxAppsDefault != null && StringUtils
				.isBlank(newQueueMaxAppsDefault))
				|| (newDefaultMinSharePreemptionTimeout != null && StringUtils
						.isBlank(newDefaultMinSharePreemptionTimeout))
				|| (newDefaultQueueSchedulingMode != null && StringUtils
						.isBlank(newDefaultQueueSchedulingMode))
				|| (newUserMaxAppsDefault != null && StringUtils
						.isBlank(newUserMaxAppsDefault))
				|| (newDefaultFairSharePreemptionTimeout != null && StringUtils
						.isBlank(newDefaultFairSharePreemptionTimeout))
				|| (newDefaultFairSharePreemptionThreshold != null && StringUtils
						.isBlank(newDefaultFairSharePreemptionThreshold))
				|| (newQueueMaxAMShareDefault != null && StringUtils
						.isBlank(newQueueMaxAMShareDefault))) {
			logger.error("New Default Propertie can not be all null");
			return;
		}
		if (newQueueMaxAppsDefault != null)
			dao.updateDefaultProperties("queueMaxAppsDefault",
					newQueueMaxAppsDefault);
		if (newDefaultMinSharePreemptionTimeout != null)
			dao.updateDefaultProperties("defaultMinSharePreemptionTimeout",
					newDefaultMinSharePreemptionTimeout);
		if (newDefaultQueueSchedulingMode != null)
			dao.updateDefaultProperties("defaultQueueSchedulingMode",
					newDefaultQueueSchedulingMode);
		if (newUserMaxAppsDefault != null)
			dao.updateDefaultProperties("userMaxAppsDefault",
					newUserMaxAppsDefault);
		if (newDefaultFairSharePreemptionTimeout != null)
			dao.updateDefaultProperties("defaultFairSharePreemptionTimeout",
					newDefaultFairSharePreemptionTimeout);
		if (newDefaultFairSharePreemptionThreshold != null)
			dao.updateDefaultProperties("defaultFairSharePreemptionThreshold",
					newDefaultFairSharePreemptionThreshold);
		if (newQueueMaxAMShareDefault != null)
			dao.updateDefaultProperties("queueMaxAMShareDefault",
					newQueueMaxAMShareDefault);
	}

	// 修改配置文件的队列放置规则queuePlacementPolicy
	@RequestMapping("/modifyTheQueuePlacementPolicy")
	public void modifyTheQueuePlacementPolicy(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Modify The Queue Placement Policy");

	}

	// 一些辅助函数
	private void writeMinResourcesToClient(ArrayList<Pool> poolList,
			PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  minResources : "
					+ pool.getMinResources());
			writeMinResourcesToClient(pool.getPools(), pw);
		}
	}

	private void writeMaxResourcesToClient(ArrayList<Pool> poolList,
			PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  maxResources : "
					+ pool.getMaxResources());
			writeMaxResourcesToClient(pool.getPools(), pw);
		}
	}

	private void writeMaxRunningAppsToClient(ArrayList<Pool> poolList,
			PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  maxRunningApps : "
					+ pool.getMaxRunningApps());
			writeMaxRunningAppsToClient(pool.getPools(), pw);
		}
	}

	private void writeMaxAMShareToClient(ArrayList<Pool> poolList,
			PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  MaxAMShare : "
					+ pool.getMaxAMShare());
			writeMaxAMShareToClient(pool.getPools(), pw);
		}
	}

	private void writeMinSharePreemptionTimeoutToClient(
			ArrayList<Pool> poolList, PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  minSharePreemptionTimeout : "
					+ pool.getMinSharePreemptionTimeout());
			writeMinSharePreemptionTimeoutToClient(pool.getPools(), pw);
		}
	}

	private void writeFairSharePreemptionTimeoutToClient(
			ArrayList<Pool> poolList, PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName()
					+ "  fairSharePreemptionTimeout : "
					+ pool.getFairSharePreemptionTimeout());
			writeFairSharePreemptionTimeoutToClient(pool.getPools(), pw);
		}
	}

	private void writeFairSharePreemptionThresholdToClient(
			ArrayList<Pool> poolList, PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName()
					+ "  fairSharePreemptionThreshold : "
					+ pool.getFairSharePreemptionThreshold());
			writeFairSharePreemptionThresholdToClient(pool.getPools(), pw);
		}
	}

	private void writeWeightToClient(ArrayList<Pool> poolList, PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  weight : " + pool.getWeight());
			writeWeightToClient(pool.getPools(), pw);
		}
	}

	private void writeSchedulingModeToClient(ArrayList<Pool> poolList,
			PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  schedulingMode : "
					+ pool.getSchedulingMode());
			writeSchedulingModeToClient(pool.getPools(), pw);
		}
	}

	private void writeACLListToClient(ArrayList<Pool> poolList, PrintWriter pw) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pw.println(pool.getDisplayName() + "  aclSubmitApps : "
					+ pool.getAclSubmitApps());
			pw.println(pool.getDisplayName() + "  aclAdministerApps : "
					+ pool.getAclAdministerApps());
			writeACLListToClient(pool.getPools(), pw);
		}
	}

	public Pool findPool(ArrayList<Pool> poolList, String poolName) {
		Pool resPool = null;
		for (Pool pool : poolList) {
			if (poolName.equals(pool.getDisplayName()))
				return pool;
			resPool = findPool(pool.getPools(), poolName);
		}
		return resPool;
	}

	public void modifyTheQueueNameFromXML(ArrayList<Pool> poolList,
			String nameOfTheUpperLayer) {
		if (poolList.isEmpty())
			return;
		for (Pool pool : poolList) {
			pool.setDisplayName(nameOfTheUpperLayer + pool.getName());
			modifyTheQueueNameFromXML(pool.getPools(), pool.getDisplayName()
					+ ".");
		}
	}

	private void printQueueInfo(Pool targetpool, PrintWriter pw) {
		if (!targetpool.getPools().isEmpty()) {
			for (Pool pool : targetpool.getPools()) {
				printQueueInfo(pool, pw);
			}
		}
		pw.println(targetpool.getDisplayName() + ": minResources : "
				+ targetpool.getMinResources());
		pw.println(targetpool.getDisplayName() + ": maxResources : "
				+ targetpool.getMaxResources());
		pw.println(targetpool.getDisplayName() + ": maxRunningApps : "
				+ targetpool.getMaxRunningApps());
		pw.println(targetpool.getDisplayName() + ": maxAMShare : "
				+ targetpool.getMaxAMShare());
		pw.println(targetpool.getDisplayName() + ": weight : "
				+ targetpool.getWeight());
		pw.println(targetpool.getDisplayName()
				+ ": minSharePreemptionTimeout : "
				+ targetpool.getMinSharePreemptionTimeout());
		pw.println(targetpool.getDisplayName()
				+ ": fairSharePreemptionTimeout : "
				+ targetpool.getFairSharePreemptionTimeout());
		pw.println(targetpool.getDisplayName()
				+ ": fairSharePreemptionThreshold : "
				+ targetpool.getFairSharePreemptionThreshold());
		pw.println(targetpool.getDisplayName() + ": schedulingMode : "
				+ targetpool.getSchedulingMode());
		pw.println(targetpool.getDisplayName() + ": aclSubmitApps : "
				+ targetpool.getAclSubmitApps());
		pw.println(targetpool.getDisplayName() + ": aclAdministerApps : "
				+ targetpool.getAclAdministerApps());
	}

}