package com.zaad.crawler.config;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.ZaadExecutionMode;
import com.zaad.common.util.ZaadCommonNamingManger;
import com.zaad.common.util.ZaadLogger;
import com.zaad.crawler.crawl.TutorPlaylistsCrawler;
import com.zaad.crawler.seed.CrawlFileLoader;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class TutorPlaylistsCrawlConfig extends ZaadAbstractCrawlConfig {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(TutorPlaylistsCrawlConfig.class);
	}
	
	static {
		PropertyConfigurator.configure(TutorPlaylistsCrawlConfig.class.getResourceAsStream("/log4j" + ZaadExecutionMode.getEnvSuffix(System.getProperty("mode")) + ".properties"));
	}
	
    public void runCrawler() throws Exception {
    	CrawlFileLoader seedLoader = new CrawlFileLoader(this.seedFilePath);
    	List<String> seeds = seedLoader.loadSeed();
    	
    	CrawlController controller = getZaadDefaultCrawlController();
        for ( String seed : seeds ) {
        	if ( ZaadCommonNamingManger.isChannel(seed) ) {
        		logger.info("add seed: " + ZaadCommonConstants.HTTP_YOUTUBE_CHANNEL_PREFIX + "/" + seed + "/" + ZaadCommonConstants.HTTP_YOUTUBE_PLAYLISTS_SUFFIX);
            	controller.addSeed(ZaadCommonConstants.HTTP_YOUTUBE_CHANNEL_PREFIX + "/" + seed + "/" + ZaadCommonConstants.HTTP_YOUTUBE_PLAYLISTS_SUFFIX);
        	} else {
        		logger.info("add seed: " + ZaadCommonConstants.HTTP_YOUTUBE_USER_PREFIX + "/" + seed + "/" + ZaadCommonConstants.HTTP_YOUTUBE_PLAYLISTS_SUFFIX);
            	controller.addSeed(ZaadCommonConstants.HTTP_YOUTUBE_USER_PREFIX + "/" + seed + "/" + ZaadCommonConstants.HTTP_YOUTUBE_PLAYLISTS_SUFFIX);
        	}
        }

        logger.info("starting TutorPlaylistsCrawler");
        controller.start(TutorPlaylistsCrawler.class, NUMBER_OF_CRAWLERS);
    }
}