package com.chinatelecom.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "pools", "users", "queueMaxAppsDefault",
		"defaultMinSharePreemptionTimeout", "defaultQueueSchedulingMode",
		"userMaxAppsDefault", "defaultFairSharePreemptionTimeout",
		"defaultFairSharePreemptionThreshold", "queueMaxAMShareDefault",
		"queuePlacementPolicy" })
@XmlRootElement(name = "allocations")
public class Allocations {
	@XmlElement(required = true, name = "pool")
	protected ArrayList<Pool> pools;

	@XmlElement(required = false, name = "user")
	protected ArrayList<User> users;

	@XmlElement(required = false, name = "queueMaxAppsDefault")
	protected String queueMaxAppsDefault;

	@XmlElement(required = false, name = "defaultMinSharePreemptionTimeout")
	protected String defaultMinSharePreemptionTimeout;

	@XmlElement(required = false, name = "defaultPoolSchedulingMode")
	protected String defaultQueueSchedulingMode;

	@XmlElement(required = false, name = "userMaxAppsDefault")
	protected String userMaxAppsDefault;

	@XmlElement(required = false, name = "defaultFairSharePreemptionTimeout")
	protected String defaultFairSharePreemptionTimeout;

	@XmlElement(required = false, name = "defaultFairSharePreemptionThreshold")
	protected String defaultFairSharePreemptionThreshold;

	@XmlElement(required = false, name = "queueMaxAMShareDefault")
	protected String queueMaxAMShareDefault;

	@XmlElement(required = false, name = "queuePlacementPolicy")
	protected QueuePlacementPolicy queuePlacementPolicy;

	public ArrayList<User> getUsers() {
		if (users == null) {
			users = new ArrayList<User>();
		}
		return this.users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public String getQueueMaxAppsDefault() {
		return queueMaxAppsDefault;
	}

	public void setQueueMaxAppsDefault(String queueMaxAppsDefault) {
		this.queueMaxAppsDefault = queueMaxAppsDefault;
	}

	public String getDefaultMinSharePreemptionTimeout() {
		return defaultMinSharePreemptionTimeout;
	}

	public void setDefaultMinSharePreemptionTimeout(
			String defaultMinSharePreemptionTimeout) {
		this.defaultMinSharePreemptionTimeout = defaultMinSharePreemptionTimeout;
	}

	public String getDefaultQueueSchedulingMode() {
		return defaultQueueSchedulingMode;
	}

	public void setDefaultQueueSchedulingMode(String defaultQueueSchedulingMode) {
		this.defaultQueueSchedulingMode = defaultQueueSchedulingMode;
	}

	public String getUserMaxAppsDefault() {
		return userMaxAppsDefault;
	}

	public void setUserMaxAppsDefault(String userMaxAppsDefault) {
		this.userMaxAppsDefault = userMaxAppsDefault;
	}

	public String getDefaultFairSharePreemptionTimeout() {
		return defaultFairSharePreemptionTimeout;
	}

	public void setDefaultFairSharePreemptionTimeout(
			String defaultFairSharePreemptionTimeout) {
		this.defaultFairSharePreemptionTimeout = defaultFairSharePreemptionTimeout;
	}

	public String getDefaultFairSharePreemptionThreshold() {
		return defaultFairSharePreemptionThreshold;
	}

	public void setDefaultFairSharePreemptionThreshold(
			String defaultFairSharePreemptionThreshold) {
		this.defaultFairSharePreemptionThreshold = defaultFairSharePreemptionThreshold;
	}

	public String getQueueMaxAMShareDefault() {
		return queueMaxAMShareDefault;
	}

	public void setQueueMaxAMShareDefault(String queueMaxAMShareDefault) {
		this.queueMaxAMShareDefault = queueMaxAMShareDefault;
	}

	public QueuePlacementPolicy getQueuePlacementPolicy() {
		return queuePlacementPolicy;
	}

	public void setQueuePlacementPolicy(
			QueuePlacementPolicy queuePlacementPolicy) {
		this.queuePlacementPolicy = queuePlacementPolicy;
	}

	public ArrayList<Pool> getPools() {
		if (pools == null) {
			pools = new ArrayList<Pool>();
		}
		return this.pools;
	}

	public void setPools(ArrayList<Pool> pools) {
		this.pools = pools;
	}

}