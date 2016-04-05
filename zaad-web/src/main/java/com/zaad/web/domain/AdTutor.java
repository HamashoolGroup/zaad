package com.zaad.web.domain;

import com.zaad.common.domain.Tutor;

public class AdTutor extends Tutor {
	protected String site;
	
	protected String siteTitle;
	
	public AdTutor() {
		super();
	}
	
	public AdTutor(Tutor tutor) {
		this();
		
		this.tutorId = tutor.getTutorId();
		this.tutorName = tutor.getTutorName();
		this.profileImagePath = tutor.getProfileImagePath();
		this.bannerImagePath = tutor.getBannerImagePath();
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSiteTitle() {
		return siteTitle;
	}

	public void setSiteTitle(String siteTitle) {
		this.siteTitle = siteTitle;
	}
}
