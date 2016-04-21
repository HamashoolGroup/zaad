package com.zaad.indexer.common.util;

/**
 * 문자열이 영어인지 확인하는 유틸 클래스.
 *
 * @author socurites
 */
public class ZaadLanguageUtil {
	public static boolean isLangEnglish(String title) {
		boolean isEnglish = true; 
		
		int threshold = 0;
		for ( int i =0; i < title.length(); i++ ) {
			if ( ((int)title.charAt(i)) > 127 ) {
				threshold++;
				
				if ( threshold > title.length()/3 ) {
					isEnglish = false;
				}
			}
			
		}
		
		return isEnglish;
	}
	
	
	public static void main(String[] args) {
		System.out.println(isLangEnglish("3rd Conditional (Part 2 of 3) - Learn English with.."));
		System.out.println(isLangEnglish("Журчат Рубли - Уральские пельмени (2015)"));
		System.out.println(isLangEnglish("bible"));
	}
}
