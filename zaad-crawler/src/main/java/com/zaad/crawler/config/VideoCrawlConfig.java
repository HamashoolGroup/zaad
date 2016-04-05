package com.zaad.crawler.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.util.ZaadLogger;
import com.zaad.crawler.crawl.VideoCrawler;
import com.zaad.crawler.seed.CrawlFileLoader;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class VideoCrawlConfig extends ZaadAbstractCrawlConfig {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(VideoCrawlConfig.class);
	}
	
    public void runCrawler() throws Exception {
    	CrawlFileLoader seedLoader = new CrawlFileLoader(this.seedFilePath);
    	List<String> seeds = seedLoader.loadSeed();
    	
    	CrawlController controller = getZaadDefaultCrawlController();
        for ( String seed : seeds ) {
        	logger.info("add seed: " + ZaadCommonConstants.HTTP_YOUTUBE_PREFIX + seed);
        	controller.addSeed(ZaadCommonConstants.HTTP_YOUTUBE_PREFIX + seed);
        }

        logger.info("starting VideoCrawlConfig");
        controller.setCustomData(this.getCustomData());
        controller.start(VideoCrawler.class, NUMBER_OF_CRAWLERS);
    }
    
    /* (non-Javadoc)
	 * @see com.zaad.crawler.config.ZaadAbstractCrawlConfig#setCustomData()
	 */
	@Override
	protected Map<String, String> getCustomData() {
		Map<String, String> customData = new HashMap<String, String>();
		customData.put("tutorId", this.getTutorId());
		customData.put("playlistId", this.getPlaylistId());
		
		return customData;
	}
    
    public static void main(String[] args) throws Exception {
    	VideoCrawlConfig config = new VideoCrawlConfig();
    	config.setSeedFilePath("zaad/crawl/seed/ajhoge/PLvckz0aC6Uw09Ym5OPedEuOyC4P3W9AHt/seed.txt");
    	config.runCrawler();
    }
}