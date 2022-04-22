package com.zaad.common.parser;

import java.util.List;

/**
 * zaad 라인 파서
 *
 * @author socurites
 */
public class ZaadLineParser {
	public static String getLineAsString(List<String> lines, int index) {
		if ( lines.size() > index ) {
			return lines.get(index);
		} else {
			return null;
		}
	}
}
