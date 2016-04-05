package com.zaad.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ZaadProperties {
	private static Properties prop =  new Properties();
	
	public static void loadProperties(InputStream in) {
		try {
			prop.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getAsString(String key) {
		return prop.getProperty(key);
	}
	
	public static int getAsInt(String key) {
		return Integer.parseInt(prop.getProperty(key));
	}
}
