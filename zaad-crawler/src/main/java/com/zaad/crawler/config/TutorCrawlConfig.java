package com.zaad.crawler.config;

import java.util.List;

import org.apache.log4j.Logger;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.util.ZaadCommonNamingManger;
import com.zaad.common.util.ZaadLogger;
import com.zaad.crawler.crawl.TutorCrawler;
import com.zaad.crawler.seed.CrawlFileLoader;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class TutorCrawlConfig extends ZaadAbstractCrawlConfig {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(TutorCrawlConfig.class);
	}
	
    public void runCrawler() throws Exception {
    	CrawlFileLoader seedLoader = new CrawlFileLoader(this.seedFilePath);
    	List<String> seeds = seedLoader.loadSeed();
    	
        CrawlController controller = getZaadDefaultCrawlController();
        for ( String seed : seeds ) {
        	if ( ZaadCommonNamingManger.isChannel(seed) ) {
        		logger.info("add seed: " + ZaadCommonConstants.HTTP_YOUTUBE_CHANNEL_PREFIX + "/" + seed);
        		controller.addSeed(ZaadCommonConstants.HTTP_YOUTUBE_CHANNEL_PREFIX + "/" + seed);
        	} else {
        		logger.info("add seed: " + ZaadCommonConstants.HTTP_YOUTUBE_USER_PREFIX + "/" + seed);
        		controller.addSeed(ZaadCommonConstants.HTTP_YOUTUBE_USER_PREFIX + "/" + seed);
        	}
        	
        }

        logger.info("starting TutorCrawler");
        controller.start(TutorCrawler.class, NUMBER_OF_CRAWLERS);
    }
}