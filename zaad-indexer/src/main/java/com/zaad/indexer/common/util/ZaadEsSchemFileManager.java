package com.zaad.indexer.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ZaadEsSchemFileManager {

    private static Logger logger = LoggerFactory.getLogger(ZaadEsSchemFileManager.class);

    public static String getSettingsPath(String indexName, String typeName) {
        return "schema/" + indexName + "/" + indexName + "_" + typeName + "_settings.json";
    }

    public static String getMappingsPath(String indexName, String typeName) {
        return "schema/" + indexName + "/" + indexName + "_" + typeName + "_mappings.json";
    }

    public static String readSettings(String indexName, String typeName) {
        StringBuffer sb = new StringBuffer();
        logger.info("getSettingsPath = " + getSettingsPath(indexName, typeName));
        InputStream in = ZaadEsSchemFileManager.class.getClassLoader().getResourceAsStream(getSettingsPath(indexName, typeName));
        if (in == null) {
            throw new NullPointerException("input stream null");
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(in);

            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        logger.info("settings : " + "\n" + sb.toString());
        return sb.toString();
    }

    public static String readTypeMapping(String indexName, String typeName) {
        StringBuffer sb = new StringBuffer();
        logger.info("getMappingPath = " + getMappingsPath(indexName, typeName));

        InputStream in = ZaadEsSchemFileManager.class.getClassLoader().getResourceAsStream(getMappingsPath(indexName, typeName));
        if (in == null) {
            throw new NullPointerException("input stream null");
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(in);

            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        logger.info("mapping : " + "\n" + sb.toString());
        return sb.toString();
    }
}
