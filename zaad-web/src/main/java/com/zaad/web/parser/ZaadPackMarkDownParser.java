package com.zaad.web.parser;

import java.util.List;

import com.zaad.web.domain.PackMarkDown;

public class ZaadPackMarkDownParser {
	private static final String ID_INDICATOR = "[id]";
	private static final String TITLE_INDICATOR = "[title]";
	private static final String DESC_INDICATOR = "[desc]";
	private static final String VIDEO_INDICATOR = "[pvideo]";
	private static final String SEO_INDICATOR = "[seo]";
	
	public PackMarkDown markdownToHtml(List<String> lines) {
		PackMarkDown markdown = new PackMarkDown();
		
		int mode = 0;
		for ( String line : lines ) {
			if ( ID_INDICATOR.equals(line) ) {
				mode = 0;
				continue;
			} else if ( TITLE_INDICATOR.equals(line) ) {
				mode = 1;
				continue;
			} else if ( DESC_INDICATOR.equals(line) ) {
				mode = 2;
				continue;
			} else if ( VIDEO_INDICATOR.equals(line) ) {
				mode = 3;
				continue;
			} else if ( SEO_INDICATOR.equals(line) ) {
				mode = 4;
				continue;
			} else {
				if ( mode == 0 ) {
					markdown.setId(line);
				} else if ( mode == 1 ) {
					markdown.setTitle(line);
				} else if ( mode == 2 ) {
					markdown.appendDescWithNewline(line);
				} else if ( mode == 3 ) {
					markdown.addVideoId(line);
				} else if ( mode == 4 ) {
					markdown.addSeoKeywords(line);
				}
			}
		}
		
		return markdown;
	}
}
