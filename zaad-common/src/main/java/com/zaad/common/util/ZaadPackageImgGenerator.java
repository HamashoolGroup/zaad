package com.zaad.common.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;

public class ZaadPackageImgGenerator {
	private static final int TITLE_NUM = 60;
	
	public static void main(String[] args) {
		ZaadPackageImgGenerator generator = new ZaadPackageImgGenerator();
		generator.generate("business", "Business");
	}

	private void generate(String name, String packageTitle) {
		Set<String> cats = ZaadKeywordMapper.CATEGORIES.keySet();
		Set<String> levels = ZaadKeywordMapper.LEVELS.keySet();
		
		Set<String> packageTagTitles = new HashSet<String>();

		for ( String cat : cats ) {
			packageTagTitles.addAll(ZaadKeywordMapper.getTermsOfCategory(cat));
		}
		
		for ( String level : levels ) {
			packageTagTitles.addAll(ZaadKeywordMapper.getTermsOfCategory(level));
		}
		
		for ( String packageTagTitle : packageTagTitles ) {
			if ( name.equals(packageTagTitle) ) {
				for ( int i = 0; i < TITLE_NUM; i++ ) {
					System.out.println(packageTitle);
				}
			} else {
				int num = getRand();
				for ( int i = 0; i < num; i++ ) {
					System.out.println(packageTagTitle);
				}
			}
		}
	}
	
	private int getRand() {
		return RandomUtils.nextInt(1, 3);
	}
}
