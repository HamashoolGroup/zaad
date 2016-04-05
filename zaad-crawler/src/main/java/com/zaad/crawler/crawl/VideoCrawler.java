package com.zaad.crawler.crawl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.crawler.crawl.util.HtmlDocUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class VideoCrawler extends ZaadAbastractCrawler {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(VideoCrawler.class);
	}
	
	private static final String SELECTOR_DATA_VIDEO_INDEX = "#playlist-current-index";
    private static final String SELECTOR_DATA_VIDEO_TITLE = "#eow-title";
    private static final String SELECTOR_DATA_VIDEO_PUBLISHED = "#watch-uploader-info > strong";
    private static final String SELECTOR_DATA_VIDEO_DESC = "#eow-description";
    private static final String SELECTOR_DATA_VIDEO_CNT_WATCHED = "#watch7-views-info > div.watch-view-count";
    private static final String SELECTOR_DATA_VIDEO_CNT_LIKED = "#watch8-sentiment-actions > span > span:nth-child(1) > button > span";
    private static final String SELECTOR_DATA_VIDEO_CNT_DISLIKED = "#watch8-sentiment-actions > span > span:nth-child(3) > button > span";
    private static final String SELECTOR_DATA_VIDEO_CNT_COMMENTED = "#comment-section-renderer > h2";
    
    private static final String SELECTOR_UNAVAILABLE_VIDEO = "#player-unavailable";
    
    

     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         logger.info("visiting url: " + url);

         PrintWriter dataPw = null;
         try {
			dataPw = new PrintWriter(ZaadOutputDirectoryManager.getVideoDataFile(customData.get("tutorId"), customData.get("playlistId"), this.getVideoId(url)));
         } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
         }

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             Document doc = Jsoup.parse(htmlParseData.getHtml());
             
             Set<String> classNames = doc.select(SELECTOR_UNAVAILABLE_VIDEO).get(0).classNames();
             boolean isDeleted = true;
             for ( String className : classNames ) {
            	 if ( "hid".equals(className.trim()) ) {
            		 isDeleted = false;
            	 }
             }
             
             if ( isDeleted == false ) {
            	 dataPw.print(doc.select(SELECTOR_DATA_VIDEO_INDEX).get(0).text());
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(doc.select(SELECTOR_DATA_VIDEO_TITLE).get(0).text());
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(doc.select(SELECTOR_DATA_VIDEO_PUBLISHED).get(0).text());
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementTextAsString(doc.select(SELECTOR_DATA_VIDEO_DESC)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementCountTextAsString(doc.select(SELECTOR_DATA_VIDEO_CNT_WATCHED)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementCountTextAsString(doc.select(SELECTOR_DATA_VIDEO_CNT_LIKED)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementCountTextAsString(doc.select(SELECTOR_DATA_VIDEO_CNT_DISLIKED)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
                 dataPw.print(HtmlDocUtil.getFirstElementCountTextAsString(doc.select(SELECTOR_DATA_VIDEO_CNT_COMMENTED)));
                 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
             }
         }
         
         dataPw.close();
     }
     
     protected String getVideoId(String url) {
    	 String[] tokens = StringUtils.split(url, ZaadCommonConstants.DELIMETER_SLASH);
    	 return tokens[2].split("v=")[1].split("\\&")[0];
     }
     
     public static void main(String[] args) {
		VideoCrawler c = new VideoCrawler();
		System.out.println(c.getVideoId("https://youtube.com/watch?v=Qo7PB-8iAg4&index=1&list=PLvckz0aC6Uw09Ym5OPedEuOyC4P3W9AHt"));
	}
}