package com.zaad.web.search.impl;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaad.common.domain.Tutor;
import com.zaad.web.search.TutorSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*.xml")
public class TutorSearchImplTest {
	@Autowired
	TutorSearch tutorSearch;
	
	
	@Test
	public void getById() {
		Tutor tutor = tutorSearch.getOneById("abilityeducation");
		System.out.println(tutor);
		
		assertNotNull(tutor);
	}
	
	@Test
	public void listRecommendedTutor() {
		List<Tutor> list = tutorSearch.listRecommendedTutor("userId", 1, 4);
		System.out.println(list);
		
		assertNotNull(list);
//		assertEquals(5, list.size());
	}
}
