package com.zaad.web.search.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaad.common.domain.Video;
import com.zaad.web.domain.TagCount;
import com.zaad.web.search.VideoSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*.xml")
public class VideoSearchImplTest {
	@Autowired
	VideoSearch videoSearch;
	
	
	@Test
	public void listRecentVideos() {
		List<Video> list = videoSearch.listRecentVideo("userId", 1, 4);
		System.out.println(list);
		
		assertNotNull(list);
		assertEquals(5, list.size());
	}
	
	@Test
	public void listRelatedTags() {
		List<TagCount> relatedTags = videoSearch.listRelatedTags("vocabulary", 5);
		
		System.out.println(relatedTags);
	}
	
	@Test
	public void listRelatedTagsOfSearch() {
		List<TagCount> relatedTags = videoSearch.listRelatedTagsOfSearch("interview", 5);
		
		System.out.println(relatedTags);
	}
}
