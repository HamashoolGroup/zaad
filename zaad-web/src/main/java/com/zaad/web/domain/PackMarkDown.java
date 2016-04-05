package com.zaad.web.domain;

import java.util.ArrayList;
import java.util.List;

import com.zaad.common.domain.Video;

public class PackMarkDown {
	protected String id;
	protected String title;
	protected String desc="";
	protected List<Video> videos;
	protected List<String> videoIds;
	protected List<String> seoKeywords;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	public List<String> getVideoIds() {
		return videoIds;
	}
	public void setVideoIds(List<String> videoIds) {
		this.videoIds = videoIds;
	}
	
	public List<String> getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(List<String> seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public void appendDescWithNewline(String line) {
		this.desc += line + "<br/>";
	}
	
	public void addVideo(Video video) {
		if ( this.videos == null ) {
			this.videos = new ArrayList<Video>();
		}
		this.videos.add(video);
	}
	
	public void addVideoId(String videoId) {
		if ( this.videoIds == null ) {
			this.videoIds = new ArrayList<String>();
		}
		this.videoIds.add(videoId);
	}
	
	public void addSeoKeywords(String seoKeyword) {
		if ( this.seoKeywords == null ) {
			this.seoKeywords = new ArrayList<String>();
		}
		this.seoKeywords.add(seoKeyword);
	}
}
