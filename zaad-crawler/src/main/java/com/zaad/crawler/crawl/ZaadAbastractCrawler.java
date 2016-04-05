package com.zaad.crawler.crawl;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class ZaadAbastractCrawler extends WebCrawler {
	protected PrintWriter pw = null;
	protected Map<String, String> customData;
	
	/* (non-Javadoc)
	 * @see edu.uci.ics.crawler4j.crawler.WebCrawler#init(int, edu.uci.ics.crawler4j.crawler.CrawlController)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init(int id, CrawlController crawlController) {
		super.init(id, crawlController);
		
		this.customData = (HashMap<String, String>)crawlController.getCustomData();
	}



	@Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
   	 return false;
    }
}
