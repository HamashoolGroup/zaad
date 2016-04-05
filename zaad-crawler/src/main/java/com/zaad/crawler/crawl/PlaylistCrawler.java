package com.zaad.crawler.crawl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Sigmoid;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.crawler.crawl.util.HtmlDocUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class PlaylistCrawler extends ZaadAbastractCrawler {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(PlaylistCrawler.class);
	}
	
	private final static Pattern VIDEO_TITLE_STOP_FILTERS = Pattern.compile("\\[Private Video\\]");
	
    private static final String SELECTOR_LINK_VIDEO_ELEMENTS = ".pl-video-title-link";
    
    private static final String SELECTOR_DATA_PLAYLIST_TITLE_ELEMENTS = "#pl-header .pl-header-title";
    private static final String SELECTOR_DATA_PLAYLIST_DESC_ELEMENTS = "#pl-header .pl-header-description-text";
    private static final String SELECTOR_DATA_PLAYLIST_CNT_VIEWED = "#pl-header > div.pl-header-content > ul > li:nth-child(3)";

    
    @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         logger.info("visiting url: " + url);

         PrintWriter seedPw = null;
         PrintWriter dataPw = null;
		try {
			seedPw = new PrintWriter(ZaadOutputDirectoryManager.getPlaylistSeedFile(customData.get("tutorId"), this.getPlaylistId(url)));
			dataPw = new PrintWriter(ZaadOutputDirectoryManager.getPlaylistDataFile(customData.get("tutorId"), this.getPlaylistId(url)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             Document doc = Jsoup.parse(htmlParseData.getHtml());
             Elements linkVideoElements = doc.select(SELECTOR_LINK_VIDEO_ELEMENTS);
             
             String videoTitle = null;
             boolean isPrivate = false;
             for ( Element element : linkVideoElements ) {
            	 videoTitle = element.text().trim();
            	 if ( !VIDEO_TITLE_STOP_FILTERS.matcher(videoTitle).matches() ) {
            		 seedPw.print(element.attr("href"));
            		 seedPw.print(ZaadCommonConstants.DELIMETER_SEMI_COLON);
            		 seedPw.println();
            	 } else {
            		 isPrivate = true;
            	 }
             }
             
             if ( isPrivate == false ) {
            	 dataPw.print(doc.select(SELECTOR_DATA_PLAYLIST_TITLE_ELEMENTS).get(0).text());
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementTextAsString(doc.select(SELECTOR_DATA_PLAYLIST_DESC_ELEMENTS)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementCountTextAsString(doc.select(SELECTOR_DATA_PLAYLIST_CNT_VIEWED)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
             }
         }
         
         seedPw.close();
         dataPw.close();
     }
     
     protected String getPlaylistId(String url) {
    	 String[] tokens = StringUtils.split(url, ZaadCommonConstants.DELIMETER_SLASH);
    	 return tokens[2].split("\\?")[1].split("=")[1];
     }
     
     public static void main(String[] args) {
    	 Sigmoid aa = new Sigmoid();
    	 System.out.println(Math.log(5));
    	 System.out.println(Math.log(55));
    	 System.out.println(Math.log(555));
    	 System.out.println(Math.log(5555));
    	 System.out.println(Math.log(55555));
    	 System.out.println(Math.log(555555));
    	 System.out.println(Math.log(5555555));
    	 System.out.println(Math.log(55555555));
    	 System.out.println(Math.log(555555555));
	}
     
}