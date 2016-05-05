package com.zaad.common.parser;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 영상 라인파서
 *
 * @author socurites
 */
public class VideoLineParser {
	private static SimpleDateFormat DATE_FORMAT_CREATION = new SimpleDateFormat("MMM d, yyyy");
	
	public static Date getCreationDate(String str) throws ParseException {
		String dateStr = StringUtils.splitByWholeSeparator(str, "on")[1].trim();
		return DATE_FORMAT_CREATION.parse(dateStr);
	}
	
	public static int getIntValFromText(String str) {
		String strAsInt = str.replaceAll("\\D+","");
		if ( strAsInt == null || strAsInt.isEmpty() ) {
			return 0;
		} else {
			return Integer.parseInt(strAsInt);
		}
	}
	
	public static int getIndexAsInt(String str) {
		if ( str.matches("[0-9]+") ) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		String str = "Published on Jun 3, 2012";
	
		String dateStr = StringUtils.splitByWholeSeparator(str, "on")[1];
		System.out.println(dateStr);
	}
	
}
