package com.zaad.indexer.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;

/**
 * settings, mapping 파일을 읽어 스트링으로 반환.
 *
 * @author socurites, lks21c
 */
public class ZaadEsSchemFileManager {
    private static Logger logger = LoggerFactory.getLogger(ZaadEsSchemFileManager.class);

    private static String SCHEMA_DIR = "schema";
    private static String SETTINGS_FILENAME = "settings.json";
    private static String MAPPING_FILENAME = "mappings.json";


    public static String getSettingsPath(String indexName, String typeName) {
        return SCHEMA_DIR + "/" + indexName + "/" + indexName + "_" + typeName + "_" + SETTINGS_FILENAME;
    }

    public static String getMappingsPath(String indexName, String typeName) {
        return SCHEMA_DIR + "/" + indexName + "/" + indexName + "_" + typeName + "_" + MAPPING_FILENAME;
    }

    public static String readSettings(String indexName, String typeName) {
        String settingsPath = getSettingsPath(indexName, typeName);
        logger.info("getSettingsPath = " + settingsPath);
        return getStringFromPath(settingsPath);
    }

    public static String readTypeMapping(String indexName, String typeName) {
        String mappingPath = getMappingsPath(indexName, typeName);
        logger.info("getMappingPath = " + mappingPath);
        return getStringFromPath(mappingPath);
    }

    public static String getStringFromPath(String path) {
        InputStream in = ZaadEsSchemFileManager.class.getClassLoader().getResourceAsStream(path);
        if (in == null) {
            throw new NullPointerException("input stream null");
        }
        Scanner scanner = null;
        StringBuffer sb = new StringBuffer();
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
