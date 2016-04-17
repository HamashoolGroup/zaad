package com.zaad.web.domain;

public class TagCount {
	protected String text;
	protected long count;
	
	public TagCount() {
		
	}
	
	public TagCount(String text, long count) {
		this.text = text;
		this.count = count;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
}
