package com.zaad.common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zaad.common.ZaadExecutionMode;

public class ZaadLogger {
	public static <T> Logger getLogger(Class<T> cls) {
		PropertyConfigurator.configure(cls.getResourceAsStream("/log4j" + ZaadExecutionMode.getEnvSuffix(System.getProperty("mode")) + ".properties"));
		return Logger.getLogger(cls);
	}
}
