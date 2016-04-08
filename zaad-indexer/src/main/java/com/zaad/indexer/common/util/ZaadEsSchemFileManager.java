package com.zaad.indexer.common.util;

import java.io.InputStream;
import java.util.Scanner;

public class ZaadEsSchemFileManager {
	public static String getSettingsPath(String indexName, String typeName) {
		return "schema/" + indexName + "/" + indexName + "_" + typeName + "_settings.json";
	}
	
	public static String getMappingsPath(String indexName, String typeName) {
		return "schema/" + indexName + "/" + indexName + "_" + typeName + "_mappings.json";
	}
	
	public static String readSettings(String indexName, String typeName) {
		StringBuffer sb = new StringBuffer();
		InputStream in = ZaadEsSchemFileManager.class.getClassLoader().getResourceAsStream(getSettingsPath(indexName, typeName));
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(in);
			
			while ( scanner.hasNextLine() ) {
				sb.append(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}

		return sb.toString();
	}
	
	public static String readTypeMapping(String indexName, String typeName) {
		StringBuffer sb = new StringBuffer();
		InputStream in = ZaadEsSchemFileManager.class.getClassLoader().getResourceAsStream(getMappingsPath(indexName, typeName));
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(in);
			
			while ( scanner.hasNextLine() ) {
				sb.append(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}

		return sb.toString();
	}
}
