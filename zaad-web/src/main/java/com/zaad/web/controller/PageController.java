package com.zaad.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zaad.common.ZaadCommonConstants;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.util.ZaadWebSeoUtil;

@Controller
public class PageController {
	@RequestMapping("/")
	public ModelAndView index() {
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
		
		return new ModelAndView("zaad.index")
				.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
				.addObject("track1", tracks.get(RandomUtils.nextInt(0, 3)))
				.addObject("track2", tracks.get(RandomUtils.nextInt(3, 6)))
				.addObject("track3", tracks.get(RandomUtils.nextInt(6, 9)))
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
