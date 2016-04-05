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
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping("/{cat}")
	public ModelAndView index(@PathVariable("cat") String cat) {
		return new ModelAndView("zaad.category.list")
			.addObject("mapping", "category")
			.addObject("cat", cat)
			.addObject("title", ZaadKeywordMapper.getTitleOfCategory(cat))
			.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
			.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
			.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
			.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/category/" + cat)
			.addObject("seo", generateHtmlSeo(cat))
		;
	}
	
	@RequestMapping(value="/{cat}/list", produces = "application/json; charset=utf8")
	public String list(@PathVariable("cat") String cat, @RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		String sortType = "A";
		
		List<String> tags = new ArrayList<String>();
		tags.add(cat);
		List<Video> videos = videoSearch.searchVideoByTags("userId", tags, page, size, sortType);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	@RequestMapping(value="/{cat}/{subCat}/list", produces = "application/json; charset=utf8")
	public String subList(@PathVariable("cat") String cat, @PathVariable("subCat") String subCat, @RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size, @RequestParam(value="sortType", required=false, defaultValue="R") String sortType) throws JsonProcessingException {
		List<String> tags = new ArrayList<String>();
		tags.add(cat);
		tags.add(subCat);
		List<Video> videos = videoSearch.searchVideoByTags("userId", tags, page, size, sortType);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	private HtmlSeo generateHtmlSeo(String cat) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(ZaadKeywordMapper.getTitleOfCategory(cat) + " English" + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription(ZaadKeywordMapper.getTitleOfCategory(cat) + " English");
		htmlSeo.addKeywords(ZaadKeywordMapper.getTermsOfCategory(cat));
		
		return htmlSeo;
	}
	
}
