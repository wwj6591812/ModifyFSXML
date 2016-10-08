package com.chinatelecom.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class QueuePlacementPolicy {
	@XmlElement(required = true, name = "rule")
	protected ArrayList<Rule> rules;

	public ArrayList<Rule> getRules() {
		if (rules == null) {
			rules = new ArrayList<Rule>();
		}
		return this.rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
}
