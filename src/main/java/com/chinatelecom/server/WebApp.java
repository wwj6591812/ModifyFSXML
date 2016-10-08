package com.chinatelecom.server;

import java.net.URL;

import org.eclipse.jetty.webapp.WebAppContext;

public class WebApp extends WebAppContext {

	public WebApp(String name, String pathOfwebXML) {
		this.setContextPath("/" + name);
		this.setDisplayName(name);
		URL XMLurl = ClassLoader.getSystemClassLoader().getResource(
				pathOfwebXML);
		if (XMLurl == null || pathOfwebXML.length() == 0) {
			throw new IllegalArgumentException("Can't find web.xml in "
					+ pathOfwebXML);
		}
		this.setDescriptor(XMLurl.getPath());
		URL base = ClassLoader.getSystemClassLoader().getResource("");
		this.setResourceBase(base.getPath());
	}

	@Override
	public String toString() {
		return "{name:" + this.getDisplayName() + ", contextPath:"
				+ this.getContextPath() + ", resourceBase:"
				+ this.getResourceBase() + "}";
	}
}
