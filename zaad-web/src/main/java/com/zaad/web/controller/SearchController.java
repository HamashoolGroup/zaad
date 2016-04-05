package com.zaad.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.jackson.dataformat.yaml.snakeyaml.util.UriEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.domain.Video;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping("/list")
	public ModelAndView index(@RequestParam("text") String text) {
		return new ModelAndView("zaad.search.list")
			.addObject("text", text)
			.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
			.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
			.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
			.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/search/list?text=" + UriEncoder.encode(text))
			.addObject("seo", generateHtmlSeo(text))
		;
	}
	
	@RequestMapping(value="/list", produces = "application/json; charset=utf8")
	public String list(@RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size
			, @RequestParam("text") String text, @RequestParam(value="sortType", required=false, defaultValue="R") String sortType) throws JsonProcessingException, UnsupportedEncodingException {
		text = URLDecoder.decode(text, "UTF-8");
		text = StringUtils.replace(text, "_", " ");
		
		List<Video> videos = videoSearch.searchVideo("userId", text, page, size, sortType);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(videos);
	}
	
	private HtmlSeo generateHtmlSeo(String text) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle("Search for " + text + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN + " search");
		htmlSeo.setDescription("English videos for " + text);
		htmlSeo.addKeyword(text);
		
		return htmlSeo;
	}
}
