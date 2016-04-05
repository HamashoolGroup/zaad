package com.zaad.crawler.control;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.crawler.config.PlaylistCrawlConfig;
import com.zaad.crawler.config.TutorCrawlConfig;
import com.zaad.crawler.config.TutorPlaylistsCrawlConfig;
import com.zaad.crawler.config.VideoCrawlConfig;

public class ZaadCrawlController {
	private static Logger logger;
	
	static {
		logger = ZaadLogger.getLogger(ZaadCrawlController.class);
	}

	public static void main(String[] args) throws Exception {
		ZaadCrawlController controller = new ZaadCrawlController();
		controller.runControl();
	}

	private void runControl() throws Exception {
		tutorCrawl();
		tutorPlaylistCrawl();
		
		Thread.sleep(20000); // for workspace refresh
		
		List<String> tutors = ZaadOutputDirectoryManager.getSeedTutorIds();
		
		System.out.println(tutors);
		
		for ( String tutor : tutors ) {
			playlistCrawl(tutor);
		}
		
		Thread.sleep(20000); // for workspace refresh
		
		List<String> tutorSlashPlaylists = ZaadOutputDirectoryManager.getSeedPlaylistIds();
		for ( String tutorSlashPlaylist : tutorSlashPlaylists ) {
			playlistVideo(tutorSlashPlaylist);
		}
	}

	public void tutorCrawl() throws Exception {
		logger.info("tutor crawling started");
		TutorCrawlConfig config = new TutorCrawlConfig();
		config.setSeedFilePath(ZaadOutputDirectoryManager.CRAWL_ROOT_PATH + "/" + ZaadOutputDirectoryManager.SEED_FILE_NAME);
		
		config.runCrawler();
		logger.info("tutor crawling ended");
	}
	
	public void tutorPlaylistCrawl() throws Exception {
		logger.info("tutorPlaylist crawling started");
    	TutorPlaylistsCrawlConfig config = new TutorPlaylistsCrawlConfig();
    	config.setSeedFilePath(ZaadOutputDirectoryManager.CRAWL_ROOT_PATH + "/" + ZaadOutputDirectoryManager.SEED_FILE_NAME);
    	
    	config.runCrawler();
    	logger.info("tutorPlaylist crawling ended");
    }
	
	public void playlistCrawl(String tutorId) throws Exception {
		logger.info("playlist crawling started: " + tutorId);
    	PlaylistCrawlConfig config = new PlaylistCrawlConfig();
    	config.setSeedFilePath(ZaadOutputDirectoryManager.CRAWL_ROOT_SEED_PATH + "/" + tutorId + "/" + ZaadOutputDirectoryManager.SEED_FILE_NAME);
    	config.setTutorId(tutorId);
    	
    	config.runCrawler();
    	logger.info("playlist crawling ended");
    }
	
	public void playlistVideo(String tutorSlashPlaylist) throws Exception {
		logger.info("video crawling started: " + tutorSlashPlaylist);
    	VideoCrawlConfig config = new VideoCrawlConfig();
    	config.setSeedFilePath(ZaadOutputDirectoryManager.CRAWL_ROOT_SEED_PATH + "/" + tutorSlashPlaylist + "/" + ZaadOutputDirectoryManager.SEED_FILE_NAME);
    	config.setTutorId(StringUtils.split(tutorSlashPlaylist, "/")[0]);
    	config.setPlaylistId(StringUtils.split(tutorSlashPlaylist, "/")[1]);
    	
    	config.runCrawler();
    	logger.info("video crawling stendedarted");
    }

}
