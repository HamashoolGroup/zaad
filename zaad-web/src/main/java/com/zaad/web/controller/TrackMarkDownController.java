package com.zaad.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.domain.Video;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.domain.PackMarkDown;
import com.zaad.web.parser.ZaadPackMarkDownParser;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.TrackUtils;
import com.zaad.web.util.ZaadWebSeoUtil;

@Controller
@RequestMapping("/track")
public class TrackMarkDownController {
	private static final ZaadPackMarkDownParser mdProcessor = new ZaadPackMarkDownParser();
	
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping(value="/index", produces = "application/json; charset=utf8")
	public ModelAndView index() throws JsonProcessingException {
		List<String> tracks = TrackUtils.listTrack();
		
		ModelAndView mnv = new ModelAndView("zaad.track.index");
		 
		for ( int i = 0; i < tracks.size(); i++ ) {
			mnv.addObject("track" + (i+1), tracks.get(i));
		}
	
		return mnv;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable("id") String id) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/markup/track/" + id + ".md").toURI()), Charset.forName("utf-8"));
		PackMarkDown trackMarkDown = mdProcessor.markdownToHtml(lines);
		
		ModelAndView mnv = new ModelAndView("zaad.track.detail")
				.addObject("md", trackMarkDown)
				.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
				.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
				.addObject("og_img", ZaadCommonConstants.ZAAD_SITE_URL + "/resources/img/zaad/track/" + id + ".jpg")
				.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/track/" + id)
				.addObject("seo", generateHtmlSeo(trackMarkDown))
		;
		
		Video video = null;
		int index = 1;
		for ( String zaadId : trackMarkDown.getVideoIds() ) {
			video = videoSearch.getOneById(zaadId);
			if ( video != null ) {
				mnv.addObject("video" + index, video);
				mnv.addObject("v" + index + "_display", "block");
				index++;
			}
		}
		
		if ( index <= 3 ) {
			for ( int i = 3; i > (index-1); i-- ) {
				mnv.addObject("v" + index + "_display", "none");
			}
		}
		
		
		return mnv;
	}
	
	private HtmlSeo generateHtmlSeo(PackMarkDown packMarkDown) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(packMarkDown.getTitle() + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription(packMarkDown.getTitle());
		htmlSeo.addKeywords(packMarkDown.getSeoKeywords());
		
		return htmlSeo;
	}
}
