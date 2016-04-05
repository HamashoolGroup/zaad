package com.zaad.crawler.crawl.util;

import org.jsoup.select.Elements;

public class HtmlDocUtil {
	private static final String NULL_ELEMENTS_TEXT = "";
	private static final String NULL_ELEMENTS_COUNT_TEXT = "0";
	
	public static String getFirstElementTextAsString(Elements elements) {
		if ( elements.size() > 0 ) {
			return elements.get(0).text();
		} else {
			return NULL_ELEMENTS_TEXT;
		}
	}
	
	public static String getFirstElementAttrAsString(Elements elements, String attrName) {
		if ( elements.size() > 0 ) {
			return elements.get(0).attr(attrName);
		} else {
			return NULL_ELEMENTS_TEXT;
		}
	}
	
	public static String getFirstElementCountTextAsString(Elements elements) {
		if ( elements.size() > 0 ) {
			return elements.get(0).text();
		} else {
			return NULL_ELEMENTS_COUNT_TEXT;
		}
	}
}
