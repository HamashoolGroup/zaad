package com.zaad.web.controller;

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
import com.zaad.common.domain.Tutor;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.search.TutorSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@RestController
@RequestMapping("/tutor")
public class TutorController {
	@Autowired
	private TutorSearch tutorSearch;
	
	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable("id") String tutorId) {
		Tutor tutor = tutorSearch.getOneById(tutorId);
		return new ModelAndView("zaad.tutor.detail")
				.addObject("tutor", tutor)
				.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
				.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
				.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
				.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/tutor/" + tutorId)
				.addObject("seo", generateHtmlSeo(tutor))
		;
	}
	
	@RequestMapping(value="/list/recommended", produces = "application/json; charset=utf8")
	public String listRecommendedTutor(@RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Tutor> tutors = tutorSearch.listRecommendedTutor("userId", page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(tutors);
	}
	
	private HtmlSeo generateHtmlSeo(Tutor tutor) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(tutor.getTutorName() + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription("English tutor - " + tutor.getTutorName());
		htmlSeo.addKeyword(tutor.getTutorName());
		
		return htmlSeo;
	}
}
