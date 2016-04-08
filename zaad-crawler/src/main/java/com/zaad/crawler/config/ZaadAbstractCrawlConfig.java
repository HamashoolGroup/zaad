package com.zaad.crawler.config;

import com.zaad.common.util.ZaadProperties;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.http.message.BasicHeader;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class ZaadAbstractCrawlConfig {
	protected static int NUMBER_OF_CRAWLERS;
	protected static String CRAWL_STORAGE_FOLDER;
	protected static int POLITENESS_DELAY;
	protected static String USER_AGENT_STRING = "malsami-bots";
	
	static {
		ZaadProperties.loadProperties(
				ZaadAbstractCrawlConfig.class.getClassLoader().getResourceAsStream(
						"zaad.properties"));
		NUMBER_OF_CRAWLERS = ZaadProperties.getAsInt("crawler.number");
		CRAWL_STORAGE_FOLDER = ZaadProperties.getAsString("crawler.storage_folder");
		POLITENESS_DELAY = ZaadProperties.getAsInt("crawler.politeness_deplay");
	}
	
	protected String seedFilePath;
	protected String tutorId;
	protected String playlistId;

	public abstract void runCrawler() throws Exception;
    
    protected Collection<BasicHeader> getHeaders() {
        Collection<BasicHeader> defaultHeaders = new HashSet<BasicHeader>();
        BasicHeader header = new BasicHeader("Accept-Language", "en-US");
        defaultHeaders.add(header);
        
        return defaultHeaders;
    }

	/**
	 * @return the seedFilePath
	 */
	public String getSeedFilePath() {
		return seedFilePath;
	}

	/**
	 * @param seedFilePath the seedFilePath to set
	 */
	public void setSeedFilePath(String seedFilePath) {
		this.seedFilePath = seedFilePath;
	}
    
	
	protected CrawlController getZaadDefaultCrawlController() throws Exception {
		CrawlConfig zaadDefaultCrawlConfig = this.getZaadDefaultCrawlConfig();
		
		PageFetcher pageFetcher = new PageFetcher(zaadDefaultCrawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(zaadDefaultCrawlConfig, pageFetcher, robotstxtServer);
        
        return controller;
	}
	
	protected CrawlConfig getZaadDefaultCrawlConfig() {
		CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE_FOLDER);
        config.setDefaultHeaders(getHeaders());
        config.setPolitenessDelay(POLITENESS_DELAY);
        config.setMaxDepthOfCrawling(0);
        config.setMaxOutgoingLinksToFollow(0);
        config.setUserAgentString(USER_AGENT_STRING);
        
        return config;
	}
    
	protected Map<String, String> getCustomData() {
		return new HashMap<String, String>();
	}

	/**
	 * @return the tutorId
	 */
	public String getTutorId() {
		return tutorId;
	}

	/**
	 * @param tutorId the tutorId to set
	 */
	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}

	/**
	 * @return the playlistId
	 */
	public String getPlaylistId() {
		return playlistId;
	}

	/**
	 * @param playlistId the playlistId to set
	 */
	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}
}