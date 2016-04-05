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
import com.zaad.common.util.ZaadKeywordMapper;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@RestController
@RequestMapping("/tag")
public class TagController {
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping("/{tag}")
	public ModelAndView index(@PathVariable("tag") String tag) {
		return new ModelAndView("zaad.category.list")
			.addObject("mapping", "tag")
			.addObject("cat", tag)	// use same ui for tag/category list
			.addObject("title", ZaadKeywordMapper.getTitleOfCategory(tag))
			.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
			.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
			.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
			.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/tag/" + tag)
			.addObject("seo", generateHtmlSeo(tag))
		;
	}
	
	@RequestMapping(value="/{tag}/list", produces = "application/json; charset=utf8")
	public String list(@PathVariable("tag") String tag, @RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		String sortType = "A";
		
		List<String> tags = new ArrayList<String>();
		tags.add(tag);
		List<Video> videos = videoSearch.searchVideoByTags("userId", tags, page, size, sortType);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/{tag}/{subTag}/list", produces = "application/json; charset=utf8")
	public String subList(@PathVariable("tag") String tag, @PathVariable("subTag") String subTag, @RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size, @RequestParam(value="sortType", required=false, defaultValue="R") String sortType) throws JsonProcessingException {
		List<String> tags = new ArrayList<String>();
		tags.add(tag);
		tags.add(subTag);
		List<Video> videos = videoSearch.searchVideoByTags("userId", tags, page, size, sortType);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/related/{tag}", produces = "application/json; charset=utf8")
	public String listRelatedTags(@PathVariable("tag") String tag, @RequestParam(value="size", required=false, defaultValue="5") int size) throws JsonProcessingException {
		List<String> relatedTags = videoSearch.listRelatedTags(tag, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(relatedTags);
	}
	
	@RequestMapping(value="/related", produces = "application/json; charset=utf8")
	public String listRelatedTagsOfSearch(@RequestParam("text") String text, @RequestParam(value="size", required=false, defaultValue="5") int size) throws JsonProcessingException {
		List<String> relatedTags = videoSearch.listRelatedTagsOfSearch(text, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(relatedTags);
	}
	
	private HtmlSeo generateHtmlSeo(String tag) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(ZaadKeywordMapper.getTitleOfCategory(tag) + " English" + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription(ZaadKeywordMapper.getTitleOfCategory(tag) + " English");
		htmlSeo.addKeywords(ZaadKeywordMapper.getTermsOfCategory(tag));
		
		return htmlSeo;
	}
}
