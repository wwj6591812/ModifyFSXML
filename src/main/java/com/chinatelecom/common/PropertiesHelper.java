package com.chinatelecom.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesHelper {

	public static Properties load(String path) throws IOException {
		Properties prop = new Properties();
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		if (loader == null)
			System.out.println("null loader");
		InputStream in = loader.getResourceAsStream(path);
		if (in == null) {
			throw new IOException("Can't find " + path);
		}
		// System.out.println("##### " + loader.getResource(path).getPath());
		// FileReader fr = new FileReader(new File(path));
		prop.load(in);
		return prop;
	}
	
	public static PropertiesConfig createConfig(Properties prop) {
		return new PropertiesConfig(prop);
	}

	private static int get(Properties prop, String key, int _default) {
		String val = prop.getProperty(key);
		return val != null ? Integer.valueOf(val) : _default;
	}

	private static String get(Properties prop, String key, String _default) {
		String val = prop.getProperty(key);
		return val != null ? val : _default;
	}

	public static class PropertiesConfig {
		private Properties prop;

		PropertiesConfig(Properties prop) {
			this.prop = prop;
		}

		public final int get(String key, int _default) {
			return PropertiesHelper.get(prop, key, _default);
		}

		public final String get(String key, String _default) {
			return PropertiesHelper.get(prop, key, _default);
		}

	}
}
