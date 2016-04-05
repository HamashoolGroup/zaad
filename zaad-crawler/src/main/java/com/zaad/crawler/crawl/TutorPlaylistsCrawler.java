package com.zaad.crawler.crawl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class TutorPlaylistsCrawler extends ZaadAbastractCrawler {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(TutorPlaylistsCrawler.class);
	}
	
    private final static Pattern PLYLIST_TITLE_STOP_FILTERS = Pattern.compile("Liked videos|Favorites");
    
    private static final String SELECTOR_LINK_PLAYIST_ELEMENTS = "div.yt-lockup-content > h3.yt-lockup-title > a.yt-uix-sessionlink";
   
    private static final String PLAYLIST_URL_FILTER = "/playlist";

     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         logger.info("visiting url: " + url);
         
         PrintWriter seedPw = null;
         try {
        	 seedPw = new PrintWriter(ZaadOutputDirectoryManager.getPlaylistsSeedFile(getTutorId(url)));
         } catch (FileNotFoundException e) {
			e.printStackTrace();
         }

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             Document doc = Jsoup.parse(htmlParseData.getHtml());
             Elements elements = doc.select(SELECTOR_LINK_PLAYIST_ELEMENTS);
             
             String title = null;
             String href = null;
             for ( Element element : elements ) {
            	 title = element.attr("title");
            	 href = element.attr("href");
            	 if ( !PLYLIST_TITLE_STOP_FILTERS.matcher(title).matches() && href.startsWith(PLAYLIST_URL_FILTER) ) {
            		 seedPw.print(element.attr("href"));
            		 seedPw.print(ZaadCommonConstants.DELIMETER_SEMI_COLON);
            		 seedPw.print(title);
            		 seedPw.println();
            	 }
             }
         }
         
         seedPw.close();
     }
     
     protected String getTutorId(String url) {
    	 String[] tokens = StringUtils.split(url, ZaadCommonConstants.DELIMETER_SLASH);
    	 return tokens[3];
     }
}