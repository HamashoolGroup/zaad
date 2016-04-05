package com.zaad.common.domain;

import java.util.List;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.ZaadDomain;
import com.zaad.common.parser.VideoLineParser;
import com.zaad.common.parser.ZaadLineParser;
import com.zaad.common.util.ZaadCommonNamingManger;

public class Tutor extends ZaadDomain {
	protected String tutorId;
	protected String tutorName;
	protected String profileImagePath;
	protected String bannerImagePath;
	protected int subscribeCount;
	protected String adSite;
	protected String adSiteTitle;
	
	protected String youtubeChannel;
	
	public Tutor() {
		super();
	};
	
	public Tutor(List<String> lines) {
		super(lines);
		
		int i = 0;
		this.tutorName = lines.get(i++);
		this.profileImagePath = lines.get(i++);
		this.bannerImagePath = lines.get(i++);
		this.subscribeCount = VideoLineParser.getIntValFromText(lines.get(i++));
		this.adSite = ZaadLineParser.getLineAsString(lines, i++);
		this.adSiteTitle = ZaadLineParser.getLineAsString(lines, i++);
	}
	
	/**
	 * @return the tutorId
	 */
	public String getTutorId() {
		return tutorId;
	}
	/**
	 * @param tutorId the tutorId to set
	 */
	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;

		if ( ZaadCommonNamingManger.isChannel(this.tutorId) ) {
			this.youtubeChannel = ZaadCommonConstants.HTTP_YOUTUBE_CHANNEL_PREFIX + "/" + this.tutorId;
		} else {
			this.youtubeChannel = ZaadCommonConstants.HTTP_YOUTUBE_USER_PREFIX + "/" + this.tutorId;
		}
	}
	/**
	 * @return the tutorName
	 */
	public String getTutorName() {
		return tutorName;
	}
	/**
	 * @param tutorName the tutorName to set
	 */
	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}
	/**
	 * @return the profileImagePath
	 */
	public String getProfileImagePath() {
		return profileImagePath;
	}
	/**
	 * @param profileImagePath the profileImagePath to set
	 */
	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	/**
	 * @return the bannerImagePath
	 */
	public String getBannerImagePath() {
		return bannerImagePath;
	}

	/**
	 * @param bannerImagePath the bannerImagePath to set
	 */
	public void setBannerImagePath(String bannerImagePath) {
		this.bannerImagePath = bannerImagePath;
	}

	/**
	 * @return the youtubeChannel
	 */
	public String getYoutubeChannel() {
		return youtubeChannel;
	}

	/**
	 * @param youtubeChannel the youtubeChannel to set
	 */
	public void setYoutubeChannel(String youtubeChannel) {
		this.youtubeChannel = youtubeChannel;
	}

	/**
	 * @return the subscribeCount
	 */
	public int getSubscribeCount() {
		return subscribeCount;
	}

	/**
	 * @param subscribeCount the subscribeCount to set
	 */
	public void setSubscribeCount(int subscribeCount) {
		this.subscribeCount = subscribeCount;
	}

	/**
	 * @return the adSite
	 */
	public String getAdSite() {
		return adSite;
	}

	/**
	 * @param adSite the adSite to set
	 */
	public void setAdSite(String adSite) {
		this.adSite = adSite;
	}

	/**
	 * @return the adSiteTitle
	 */
	public String getAdSiteTitle() {
		return adSiteTitle;
	}

	/**
	 * @param adSiteTitle the adSiteTitle to set
	 */
	public void setAdSiteTitle(String adSiteTitle) {
		this.adSiteTitle = adSiteTitle;
	}
}
