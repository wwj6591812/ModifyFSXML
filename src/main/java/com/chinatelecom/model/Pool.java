package com.chinatelecom.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "pools", "minResources", "maxResources",
		"maxRunningApps", "maxAMShare", "weight", "minSharePreemptionTimeout",
		"fairSharePreemptionTimeout", "fairSharePreemptionThreshold",
		"schedulingMode", "aclSubmitApps", "aclAdministerApps" })
@XmlRootElement(name = "pool")
public class Pool {
	@XmlTransient
	protected String displayName;

	@XmlAttribute(required = true, name = "name")
	protected String name;

	@XmlElement(required = false, name = "pool")
	protected ArrayList<Pool> pools;

	@XmlElement(required = false, name = "minResources")
	protected String minResources;

	@XmlElement(required = false, name = "maxResources")
	protected String maxResources;

	@XmlElement(required = false, name = "maxRunningApps")
	protected String maxRunningApps;

	@XmlElement(required = false, name = "maxAMShare")
	protected String maxAMShare;

	@XmlElement(required = false, name = "weight")
	protected String weight;

	@XmlElement(required = false, name = "minSharePreemptionTimeout")
	protected String minSharePreemptionTimeout;

	@XmlElement(required = false, name = "fairSharePreemptionTimeout")
	protected String fairSharePreemptionTimeout;

	@XmlElement(required = false, name = "fairSharePreemptionThreshold")
	protected String fairSharePreemptionThreshold;

	@XmlElement(required = false, name = "schedulingMode")
	protected String schedulingMode;

	@XmlElement(required = false, name = "aclSubmitApps")
	protected String aclSubmitApps;

	@XmlElement(required = false, name = "aclAdministerApps")
	protected String aclAdministerApps;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinResources() {
		return minResources;
	}

	public void setMinResources(String minResources) {
		this.minResources = minResources;
	}

	public String getMaxResources() {
		return maxResources;
	}

	public void setMaxResources(String maxResources) {
		this.maxResources = maxResources;
	}

	public String getMaxRunningApps() {
		return maxRunningApps;
	}

	public void setMaxRunningApps(String maxRunningApps) {
		this.maxRunningApps = maxRunningApps;
	}

	public String getMaxAMShare() {
		return maxAMShare;
	}

	public void setMaxAMShare(String maxAMShare) {
		this.maxAMShare = maxAMShare;
	}

	public String getFairSharePreemptionTimeout() {
		return fairSharePreemptionTimeout;
	}

	public void setFairSharePreemptionTimeout(String fairSharePreemptionTimeout) {
		this.fairSharePreemptionTimeout = fairSharePreemptionTimeout;
	}

	public String getFairSharePreemptionThreshold() {
		return fairSharePreemptionThreshold;
	}

	public void setFairSharePreemptionThreshold(
			String fairSharePreemptionThreshold) {
		this.fairSharePreemptionThreshold = fairSharePreemptionThreshold;
	}

	public String getMinSharePreemptionTimeout() {
		return minSharePreemptionTimeout;
	}

	public void setMinSharePreemptionTimeout(String minSharePreemptionTimeout) {
		this.minSharePreemptionTimeout = minSharePreemptionTimeout;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSchedulingMode() {
		return schedulingMode;
	}

	public void setSchedulingMode(String schedulingMode) {
		this.schedulingMode = schedulingMode;
	}

	public String getAclSubmitApps() {
		return aclSubmitApps;
	}

	public void setAclSubmitApps(String aclSubmitApps) {
		this.aclSubmitApps = aclSubmitApps;
	}

	public String getAclAdministerApps() {
		return aclAdministerApps;
	}

	public void setAclAdministerApps(String aclAdministerApps) {
		this.aclAdministerApps = aclAdministerApps;
	}

}