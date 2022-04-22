package com.zaad.web.controller;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.util.TrackUtils;
import com.zaad.web.util.ZaadWebSeoUtil;

@Controller
public class PageController {
	@RequestMapping("/")
	public ModelAndView index() {
		List<String> tracks = TrackUtils.listTrack();
		
		return new ModelAndView("zaad.index")
				.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
				.addObject("track1", tracks.get(RandomUtils.nextInt(0, 4)))
				.addObject("track2", tracks.get(RandomUtils.nextInt(4, 8)))
				.addObject("track3", tracks.get(RandomUtils.nextInt(8, 13)))
				.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
				.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
				.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL)
				.addObject("seo", generateHtmlSeo())
		;
	}
	
	private HtmlSeo generateHtmlSeo() {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(ZaadCommonConstants.ZAAD_SITE_DEFAULT_TITLE);
		htmlSeo.setDescription(ZaadCommonConstants.ZAAD_SITE_DEFAULT_DESC);
		
		return htmlSeo;
	}
}
