package com.zaad.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.domain.Video;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.exception.ZaadContentNotFoundException;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@RestController
@RequestMapping("/video")
public class VideoController {
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable("id") String videoId) {
		Video video = videoSearch.getOneByVideoId(videoId);
		
		if ( video == null ) {
			throw new ZaadContentNotFoundException();
		}
		
		return new ModelAndView("zaad.video.detail")
				.addObject("video", video)
				.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
				.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
				.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
				.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/video/" + videoId)
				.addObject("seo", generateHtmlSeo(video))
		;
	}
	
	@RequestMapping(value="/list/recent", produces = "application/json; charset=utf8")
	public String listRecentVideo(@RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Video> videos = videoSearch.listRecentVideo("userId", page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/list/recommended", produces = "application/json; charset=utf8")
	public String listRecommendedVideo(@RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Video> videos = videoSearch.listRecommendedVideo("userId", page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/list/popular", produces = "application/json; charset=utf8")
	public String listPopularVideo(@RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Video> videos = videoSearch.listPopularVideo("userId", page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/list/byplaylist/{playlistId}", produces = "application/json; charset=utf8")
	public String listVideoByPlaylistId(@PathVariable("playlistId") String playlistId, @RequestParam(value="index", required=false, defaultValue="1") int index, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Video> videos = videoSearch.listVideoByPlaylistId(playlistId, index, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/list/bytutor/{tutorId}", produces = "application/json; charset=utf8")
	public String listPopularVideoByTutorId(@PathVariable("tutorId") String tutorId, @RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Video> videos = videoSearch.listPopularVideoByTutorId(tutorId, page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	private HtmlSeo generateHtmlSeo(Video video) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(video.getTitle() + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription(video.getTitle() + " by " + video.getTutor().getTutorName());
		htmlSeo.addKeywords(new ArrayList<String>(video.getTags()));
		
		return htmlSeo;
	}
}
