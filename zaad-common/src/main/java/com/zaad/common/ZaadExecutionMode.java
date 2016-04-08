package com.zaad.common;

public class ZaadExecutionMode {
	public static final int T_UBUNTU = 0;
	public static final int T_MAC = 1;
	public static final int P_AWS = 2;
	public static final int P_AWS2 = 3;

	public static int currentMode = -1;


	public static boolean isTestUtuntu(int mode) {
		if ( T_UBUNTU == mode ) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isTestMac(int mode) {
		if ( T_MAC == mode ) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isProductAws(int mode) {
		if ( P_AWS == mode ) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isProductAws2(int mode) {
		if ( P_AWS2 == mode ) {
			return true;
		}
		
		return false;
	}
	
	public static String getEnvSuffix(String mode) {
		int modeInt = Integer.parseInt(mode);
		
		if ( isTestUtuntu(modeInt) ) {
			return "-t-ubuntu";
		} else if ( isTestMac(modeInt) ) {
			return "-t-mac";
		} else if ( isProductAws(modeInt) ) {
			return "-p-aws";
		} else if ( isProductAws2(modeInt) ) {
			return "-p-aws2";
		}
		
		return "";
	}
}