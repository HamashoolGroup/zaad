package com.zaad.web.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zaad.common.ZaadCommonConstants;

public class HtmlSeo {
	protected String title;
	protected List<String> keywords = new ArrayList<String>();
	protected String description;
	protected String author = "naras.malsami@gmail.com";
	
	private String keywordsAsString;
	
	public HtmlSeo() {		
		this.addKeywords(ZaadCommonConstants.ZAAD_SITE_DEFAULT_KEYWORDS);
	}
	
	public HtmlSeo(String title, List<String> keywords, String description) {
		this();
		
		this.title = title;
		this.keywords = keywords;
		this.description = description;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the keywords
	 */
	public List<String> getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void addKeywords(List<String> keywords) {
		this.keywords.addAll(keywords);
		
		this.keywordsAsString = StringUtils.join(this.keywords, ", ");
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}	
	
	/**
	 * @return the keywordsAsString
	 */
	public String getKeywordsAsString() {
		return keywordsAsString;
	}

	/**
	 * @param keywordsAsString the keywordsAsString to set
	 */
	public void setKeywordsAsString(String keywordsAsString) {
		this.keywordsAsString = keywordsAsString;
	}

	public void addKeyword(String keyword) {
		if ( this.keywords == null ) {
			this.keywords = new ArrayList<String>();
		}
		
		this.keywords.add(keyword);
		
		this.keywordsAsString = StringUtils.join(this.keywords, ", ");
	}
}
