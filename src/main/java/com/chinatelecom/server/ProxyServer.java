package com.chinatelecom.server;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinatelecom.common.PropertiesHelper;
import com.chinatelecom.common.PropertiesHelper.PropertiesConfig;

public class ProxyServer extends Thread implements Service {
	private static final Log LOG = LogFactory.getLog(ProxyServer.class);

	private PropertiesConfig conf;
	private HttpServer httpServer;
	private boolean running = false;

	protected ProxyServer() throws Exception {
		Properties prop = PropertiesHelper.load("modifyQueueACL.properties");
		conf = PropertiesHelper.createConfig(prop);
		httpServer = new HttpServer(conf);
		setupREST();
	}

	private void setupREST() {
		WebApp rest = new WebApp("ModifyQueueACL", "WEB-INF/web.xml");
		httpServer.addWebApp(rest);
	}

	private void startServices() throws Exception {
		httpServer.start();
	}

	private void stopServices() throws Exception {
		if (httpServer != null)
			httpServer.stop();
	}

	@Override
	public void run() {
		while (running) {
			// TODO
			// Do something else instead of joining.
			try {
				httpServer.join();
			} catch (InterruptedException ignore) {
			}
		}
	}

	public void doStart() {
		LOG.info("Start ProxyServer...");
		running = true;
		try {
			startServices();
		} catch (Exception e) {
			running = false;
			LOG.fatal("Failed to start ProxyServer.", e);
			doStop();
			return;
		}
		this.start();
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
	}

	public void doStop() {
		LOG.info("Start to stop ProxyServer...");
		running = false;
		try {
			stopServices();
		} catch (Exception e) {
			LOG.error("Failed to stop ProxyServer.", e);
		}
		this.interrupt();
	}

	public void abort(Throwable why) {
		LOG.fatal("Abort ProxyServer , because '" + why.getMessage() + "'", why);
		doStop();
	}

	public static void main(String[] args) {
		ProxyServer proxy = null;
		try {
			proxy = new ProxyServer();
			proxy.doStart();
		} catch (Throwable e) {
			if (proxy != null)
				proxy.abort(e);
			e.printStackTrace();
		}
	}

	private class ShutdownHook extends Thread {

		@Override
		public void run() {
			LOG.info("ProxyServer ShutdownHook START ");
			ProxyServer.this.doStop();
			try {
				ProxyServer.this.join();
			} catch (InterruptedException e) {
				LOG.error("ShutdownHook was interrupted.", e);
			}
			LOG.info("ProxyServer ShutdownHook FINISHED.");
		}

	}

}
