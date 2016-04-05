package com.zaad.crawler.seed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.zaad.common.ZaadCommonConstants;

public class CrawlFileLoader {
	protected String filePath;
	
	public CrawlFileLoader() {}
	
	public CrawlFileLoader(String filePath) {
		this.filePath = filePath;
	}
	
	public List<String> loadSeed() {
		List<String> items = new ArrayList<String>();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(this.filePath));
			
			String line = null;
			String[] tokens = null;
			String item = null;
			while ( scanner.hasNext() ) {
				line = scanner.nextLine();
				if ( !isComment(line) ) {
					tokens = StringUtils.split(line, ZaadCommonConstants.DELIMETER_SEMI_COLON);
					item = tokens[0];
					items.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		
		return items;
	}
	
	private boolean isComment(String line) {
		if ( line.startsWith("#") ) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static void main(String[] args) {
		CrawlFileLoader loader = new CrawlFileLoader();
		loader.loadSeed();
	}
}
