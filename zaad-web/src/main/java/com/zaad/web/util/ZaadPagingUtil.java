package com.zaad.web.util;

public class ZaadPagingUtil {
	public static int getEsFrom(int page, int size) {
		int esPage = page - 1;
		
		return esPage * size;
	}
}
