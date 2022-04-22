package com.zaad.indexer.common.util;

/**
 * @author socurites
 */
public class ZaadEsNamingManager {
	public static final String INDEX_SUFFIX = "_1";
	public static final String ALIAS_SUFFIX = "_p";
	
	public static String getDefaultIndexName(String indexName) {
		return indexName +  INDEX_SUFFIX;
	}
	
	public static String getIndexAliasName(String indexName) {
		return indexName +  ALIAS_SUFFIX;
	}
}
