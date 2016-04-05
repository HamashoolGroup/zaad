package com.zaad.web.search;

import java.util.List;

import com.zaad.common.domain.Tutor;

public interface TutorSearch {
	Tutor getOneById(String id);
	
	List<Tutor> listRecommendedTutor(String userId, int page, int size);
}
