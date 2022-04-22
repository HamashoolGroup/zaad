package com.zaad.common;

import java.util.ArrayList;
import java.util.List;

/**
 * zaad 공통 상수
 *
 * @author socurites
 */
public class ZaadCommonConstants {
	public static final String DELIMETER_SEMI_COLON = ";";
	public static final String DELIMETER_SLASH = "/";
	public static final String DELIMETER_NEW_LINE = "\n";
	
	public static final String HTTP_YOUTUBE_PREFIX = "https://www.youtube.com/";
	public static final String HTTP_YOUTUBE_USER_PREFIX = "https://www.youtube.com/user";
	public static final String HTTP_YOUTUBE_CHANNEL_PREFIX = "https://www.youtube.com/channel";
	public static final String HTTP_YOUTUBE_PLAYLISTS_SUFFIX = "playlists?view=1&sort=dd";
	
	public static final String ZAAD_SITE_URL = "http://www.malsami.com";
	public static final String ZAAD_OG_DEFAULT_IMG = ZAAD_SITE_URL + "/resources/img/zaad/logo_og_black_facebook.jpg";
	public static final String ZAAD_SITE_NAME = "malSami";
	public static final String ZAAD_SITE_NAME_WITH_HYPHEN = " - malSami" + " - learn free & real english";
	public static final String ZAAD_SITE_DEFAULT_TITLE = ZAAD_SITE_NAME + " - learn free & real english";
	public static final String ZAAD_SITE_DEFAULT_DESC = "Get real english videos for free";
	public static final List<String> ZAAD_SITE_DEFAULT_KEYWORDS = new ArrayList<String>();
	
	public static final String ZAAD_WEB_OG_TYPE_WEB = "website";
	
	static {
		ZAAD_SITE_DEFAULT_KEYWORDS.add("english");
		ZAAD_SITE_DEFAULT_KEYWORDS.add("free");
		ZAAD_SITE_DEFAULT_KEYWORDS.add("video");
		ZAAD_SITE_DEFAULT_KEYWORDS.add("learning");
	}
}
zaad-slash-plus-633770943
