package com.chinatelecom.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class User {
	@XmlElement(required = false, name = "maxRunningJobs")
	protected String maxRunningJobs;

	@XmlAttribute(required = false, name = "name")
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaxRunningJobs() {
		return maxRunningJobs;
	}

	public void setMaxRunningJobs(String maxRunningJobs) {
		this.maxRunningJobs = maxRunningJobs;
	}
}
