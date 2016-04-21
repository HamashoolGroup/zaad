package com.zaad.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * data/
 * 	|---- tutorId#1/
 *         |--- zaad.txt
 *         |--- playlistId#1/
 *                |--- zaad.txt
 *                |--- videoId#1/
 *                       |--- zaad.txt
 *                |--- videoId#2/
 *                       |--- zaad.txt
 *                |--- videoId#3/
 *                       |--- zaad.txt
 *                |--- ...
 *         |--- playlistId#2/
 *         |--- playlistId#3/
 *         |---...
 *  |---- tutorId#2/
 *  |---- tutorId#3/
 *  |----...
 *  
 *  
 *  seed/
 *  |--- seed.txt
 * 	|---- tutorId#1/
 *         |--- seed.txt				# playlist url
 *         |--- playlistId#1/
 *                |--- seed.txt			# video url
 *         |--- playlistId#2/
 *         |--- playlistId#3/
 *         |---...
 *  |---- tutorId#2/
 *  |---- tutorId#3/
 *  |----...
 * 
 * @author socurites
 *
 */
public class ZaadOutputDirectoryManager {
	public static String CRAWL_ROOT_PATH;
	public static String CRAWL_ROOT_DATA_PATH;
	public static String CRAWL_ROOT_SEED_PATH;
	public static final String DATA_FILE_NAME = "zaad.txt"; 
	public static final String SEED_FILE_NAME = "seed.txt"; 
	
	public static final String VIDEO_DIR_PREFIX = "V";

    private static Logger logger = LoggerFactory.getLogger(ZaadOutputDirectoryManager.class);

    static {
        ZaadProperties.loadProperties(
                ZaadOutputDirectoryManager.class.getClassLoader().getResourceAsStream(
                        "zaad.properties"));
        CRAWL_ROOT_PATH = ZaadProperties.getAsString("crawler.root.path");
		CRAWL_ROOT_DATA_PATH = CRAWL_ROOT_PATH + "/data";
		CRAWL_ROOT_SEED_PATH = CRAWL_ROOT_PATH + "/seed";
	}
	
	private static File getFile(String pathPrefix, String fileName, String tutorId, String playlistId, String videoId) {
		String path = getDirectoryPath(pathPrefix, fileName, tutorId,  playlistId, videoId);
		File directory = new File(path);
		
		if ( !directory.exists() ) {
			directory.mkdirs();
		}
		
		File file = new File(directory.getAbsolutePath() + "/" + fileName);
		if ( !file.exists() ) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	private static String getDirectoryPath(String pathPrefix, String fileName, String tutorId, String playlistId, String videoId) {
		if ( playlistId == null ) {
			return pathPrefix + "/" + tutorId;
		} else if ( videoId == null ) {
			return pathPrefix + "/" + tutorId + "/" + playlistId;
		} else {
			return pathPrefix + "/" + tutorId + "/" + playlistId + "/" + VIDEO_DIR_PREFIX + videoId;
		}
	}
	
	public static File getTutorDataFile(String tutorId) {
		return getFile(CRAWL_ROOT_DATA_PATH, DATA_FILE_NAME, tutorId, null, null);
	}
	
	public static File getPlaylistsSeedFile(String tutorId) {
		return getFile(CRAWL_ROOT_SEED_PATH, SEED_FILE_NAME, tutorId, null, null);
	}
	
	public static File getPlaylistDataFile(String tutorId, String playlistId) {
		return getFile(CRAWL_ROOT_DATA_PATH, DATA_FILE_NAME, tutorId, playlistId, null);
		
	}
	
	public static File getPlaylistSeedFile(String tutorId, String playlistId) {
		return getFile(CRAWL_ROOT_SEED_PATH, SEED_FILE_NAME, tutorId, playlistId, null);
	}
	
	public static File getVideoDataFile(String tutorId, String playlistId, String videoId) {
		return getFile(CRAWL_ROOT_DATA_PATH, DATA_FILE_NAME, tutorId, playlistId, videoId);
	}
	
	
	private static List<String> getTutorIds(String pathPrefix) {
		String path = pathPrefix ;
        logger.info("tutor pathPre~fix = " + path);

		File directory = new File(path);

		File[] files = directory.listFiles();
		List<String> items = new ArrayList<String>();
		for ( File file : files ) {
			if ( file.isDirectory() ) {
				items.add(file.getName());
			}
		}
		
		return items;
	}
	
	public static List<String> getSeedTutorIds() {
		return getTutorIds(CRAWL_ROOT_SEED_PATH);
	}
	
	public static List<String> getDataTutorIds() {
		return getTutorIds(CRAWL_ROOT_DATA_PATH);
	}
	
	private static List<String> getPlaylistIds(String pathPrefix) {
		List<String> tutorIds = getTutorIds(pathPrefix);
		
		String path = pathPrefix ;
		File parentDirectory = null;
		
		List<String> items = new ArrayList<String>();
		for ( String tutor : tutorIds ) {
			parentDirectory = new File(path + "/" + tutor);
			
			File[] files = parentDirectory.listFiles();
			for ( File file : files ) {
				if ( file.isDirectory() ) {
					items.add(tutor + "/" + file.getName());
				}
			}
		}
		
		return items;
	}
	
	public static List<String> getSeedPlaylistIds() {
		return getPlaylistIds(CRAWL_ROOT_SEED_PATH);
	}
	
	public static List<String> getDataPlaylistIds() {
		return getPlaylistIds(CRAWL_ROOT_DATA_PATH);
	}
	
	private static List<String> getVideoIds(String pathPrefix) {
		List<String> playlistIds = getPlaylistIds(pathPrefix);
		
		String path = pathPrefix ;
		File parentDirectory = null;
		
		List<String> items = new ArrayList<String>();
		for ( String playlist : playlistIds ) {
			parentDirectory = new File(path + "/" + playlist);
			
			File[] files = parentDirectory.listFiles();
			for ( File file : files ) {
				if ( file.isDirectory() ) {
					items.add(playlist + "/" + file.getName().replace(VIDEO_DIR_PREFIX, ""));
				}
			}
		}
		
		return items;
	}
	
	public static List<String> getDataVideoIds() {
		System.out.println("CRAWL_ROOT_DATA_PATH = " + CRAWL_ROOT_DATA_PATH);
		return getVideoIds(CRAWL_ROOT_DATA_PATH);
	}
}
