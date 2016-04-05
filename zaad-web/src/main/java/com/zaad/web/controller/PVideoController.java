package com.zaad.web.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.common.domain.Video;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.exception.ZaadContentNotFoundException;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@RestController
@RequestMapping("/pvideo")
public class PVideoController {
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping("/{playlistId}/{videoId}")
	public ModelAndView index(@PathVariable("playlistId") String playlistId, @PathVariable("videoId") String videoId) {
		Video video = videoSearch.getOneById(playlistId + "_" + videoId);
		
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
	
	private HtmlSeo generateHtmlSeo(Video video) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(video.getTitle() + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription(video.getTitle() + " by " + video.getTutor().getTutorName());
		htmlSeo.addKeywords(new ArrayList<String>(video.getTags()));
		
		return htmlSeo;
	}
}
