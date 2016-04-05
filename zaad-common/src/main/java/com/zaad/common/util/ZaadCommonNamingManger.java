package com.zaad.common.util;

public class ZaadCommonNamingManger {
	private static final String TUTOR_CHANNEL_PREFIX = "UC"	;
	
	/**
	 * tutor 분류
	 * 
	 * @param tutorId
	 * @return
	 */
	public static boolean isChannel(String tutorId) {
		if ( tutorId.startsWith(TUTOR_CHANNEL_PREFIX) ) {
			return true;
		} else {
			return false;
		}
	}
}
