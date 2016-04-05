package com.zaad.common.domain;

import java.util.List;

import com.zaad.common.ZaadMetaDomain;

public class ZaadKeyword extends ZaadMetaDomain {
	protected String name;
	
	protected String tag;
	
	protected String title;
	
	protected List<String> relatedTerm;
	
	public ZaadKeyword(String name, String tag, String title, List<String> relatedTerm) {
		this.name = name;
		this.tag = tag;
		this.title = title;
		this.relatedTerm = relatedTerm;
	}
	
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the relatedTerm
	 */
	public List<String> getRelatedTerm() {
		return relatedTerm;
	}

	/**
	 * @param relatedTerm the relatedTerm to set
	 */
	public void setRelatedTerm(List<String> relatedTerm) {
		this.relatedTerm = relatedTerm;
	}
	
	public void addTerm(String term) {
		this.relatedTerm.add(term);
	}
}
