package com.chinatelecom.server;

import java.io.IOException;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.chinatelecom.common.Constant;
import com.chinatelecom.common.PropertiesHelper.PropertiesConfig;
import com.chinatelecom.common.DBUtil;

public class HttpServer {
	private static final Log LOG = LogFactory.getLog(HttpServer.class);

	private PropertiesConfig conf;
	private Server server;
	private int port = 8080;
	private int maxHandler = 32;

	public HttpServer(PropertiesConfig conf) throws IOException {
		this.conf = conf;
		setup();
		initialize();
	}

	private void setup() {
		port = conf.get(Constant.HTTP_PORT_KEY, port);
	}

	private void initialize() throws IOException {
		//http
		//SelectChannelConnector con = new SelectChannelConnector();
		//https
		SslConnector con = new SslSelectChannelConnector();
		con.setHost("0.0.0.0");
		con.setPort(port);
		
		//https
		SslContextFactory cf = con.getSslContextFactory();
		//keytool -genkey -alias tomcat -keyalg RSA -keysize 1024 -validity 365 -keystore httpsService.keystore
	    cf.setKeyStorePath("conf/httpsService.keystore");
	    cf.setKeyStorePassword("si6wi8sf");
	    cf.setKeyManagerPassword("si6wi8sf");
		
		QueuedThreadPool pool = new QueuedThreadPool();
		pool.setMaxThreads(maxHandler);
		pool.setMinThreads(Math.min(maxHandler / 4, 2));

		server = new Server(port);
		server.setConnectors(new Connector[] { con });
		server.setThreadPool(pool);

		// 启动JRTTY的时候，创建数据库表格，用于记录用户对fair-scheduler.xml文件的修改
		Connection conn = DBUtil.getConnection();
		ScriptRunner runner = new ScriptRunner(conn);
		runner.runScript(Resources.getResourceAsReader("creat_table.sql"));
	}

	public void addWebApp(WebApp app) {
		server.setHandler(app);
		LOG.info("Add a WebApp" + app);
	}

	public void start() throws Exception {
		server.start();
		LOG.info("Start HttpServer at " + server.getConnectors()[0].getHost()
				+ ":" + server.getConnectors()[0].getPort());
	}

	public void stop() throws Exception {
		server.stop();
	}

	public void join() throws InterruptedException {
		server.join();
	}
}
