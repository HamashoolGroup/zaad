package com.zaad.crawler.crawl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.crawler.crawl.util.HtmlDocUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class TutorCrawler extends ZaadAbastractCrawler {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(TutorCrawler.class);
	}
	
    private static final String SELECTOR_DATA_TUTOR_IMAGE_ELEMENTS = ".channel-header-profile-image";
    private static final String SELECTOR_DATA_TUTOR_NAME_ELEMENTS = ".qualified-channel-title.ellipsized .qualified-channel-title-text a";
    private static final String SELECTOR_DATA_TUTOR_CNT_SUBSCRIBED = "#c4-primary-header-contents > div > div > div:nth-child(2) > div > span > span.yt-subscription-button-subscriber-count-branded-horizontal.subscribed.yt-uix-tooltip";
    private static final String SELECTOR_DATA_TUTOR_AD_SITE = "#header-links > ul.about-custom-links > li > a";
    private static final String SELECTOR_DATA_TUTOR_AD_SITE_TITLE = "#header-links > ul.about-custom-links > li > a > span";

     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         logger.info("visiting url: " + url);
         
         PrintWriter dataPw = null;
         try {
			dataPw = new PrintWriter(ZaadOutputDirectoryManager.getTutorDataFile(getTutorId(url)));
         } catch (FileNotFoundException e) {
			e.printStackTrace();
         }

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             Document doc = Jsoup.parse(htmlParseData.getHtml());
             
             Elements profileImageElements = doc.select(SELECTOR_DATA_TUTOR_IMAGE_ELEMENTS);
             profileImageElements.get(0).attr("src");
             
             Elements tutorNameElements = doc.select(SELECTOR_DATA_TUTOR_NAME_ELEMENTS);
             
             // tutorName;tutorProfileImage;tutorProfileImage
    		 dataPw.print(tutorNameElements.get(0).text());
    		 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
    		 dataPw.print(profileImageElements.get(0).attr("src"));
    		 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
    		 dataPw.print(getTutorBannerImage(doc.html()));
    		 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
    		 dataPw.print(HtmlDocUtil.getFirstElementCountTextAsString(doc.select(SELECTOR_DATA_TUTOR_CNT_SUBSCRIBED)));
    		 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
    		 dataPw.print(HtmlDocUtil.getFirstElementAttrAsString(doc.select(SELECTOR_DATA_TUTOR_AD_SITE), "href"));
    		 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
    		 dataPw.print(HtmlDocUtil.getFirstElementTextAsString(doc.select(SELECTOR_DATA_TUTOR_AD_SITE_TITLE)));
    		 dataPw.print(ZaadCommonConstants.DELIMETER_NEW_LINE);
    		 
         }
         
         dataPw.close();
     }

     protected String getTutorId(String url) {
    	 String[] tokens = StringUtils.split(url, ZaadCommonConstants.DELIMETER_SLASH);
    	 return tokens[3];
     }
     
     protected String getTutorBannerImage(String html) {
    	 String[] lines = html.split("\n");
    	 
    	 boolean found = false;
    	 for ( String line : lines ) {
    		 if ( found ) {
    			 return StringUtils.splitByWholeSeparator(line, "url(//")[1].replace(");", "");
    		 }
    		 
    		 if ( line.trim().startsWith("#c4-header-bg-container") ) {
    			 found = true;
    		 }
    	 }
    	 
    	 return null;
     }
}