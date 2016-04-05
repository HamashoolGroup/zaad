package com.zaad.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.domain.Video;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.domain.PackMarkDown;
import com.zaad.web.parser.ZaadPackMarkDownParser;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@Controller
@RequestMapping("/track")
public class TrackMarkDownController {
	private static final ZaadPackMarkDownParser mdProcessor = new ZaadPackMarkDownParser();
	
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping(value="/index", produces = "application/json; charset=utf8")
	public ModelAndView index() throws JsonProcessingException {
		List<String> tracks = new ArrayList<String>();
		tracks.add("british_vs_american_accent");
		tracks.add("daily_words_power");
		tracks.add("enjoy_business_english");
		tracks.add("how_to_learn_english");
		tracks.add("irregular_verb");
		tracks.add("killing_two_birds_with_news");
		tracks.add("prepping_job_interview");
		tracks.add("run_into_phrasal_verb");
		tracks.add("slang_for_survival");
		
		return new ModelAndView("zaad.track.index")
				.addObject("track1", tracks.get(0))
				.addObject("track2", tracks.get(1))
				.addObject("track3", tracks.get(2))
				.addObject("track4", tracks.get(3))
				.addObject("track5", tracks.get(4))
				.addObject("track6", tracks.get(5))
				.addObject("track7", tracks.get(6))
				.addObject("track8", tracks.get(7))
				.addObject("track9", tracks.get(8))
		;
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
