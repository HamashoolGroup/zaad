package com.zaad.web.search.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaad.common.domain.Playlist;
import com.zaad.web.search.PlaylistSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*.xml")
public class PlaylistSearchImplTest {
	@Autowired
	PlaylistSearch playlistSearch;
	
	
	@Test
	public void listRecommendedPlaylist() {
		List<Playlist> list = playlistSearch.listRecommendedPlaylist("userId", 1, 4);
		System.out.println(list);
		
		assertNotNull(list);
		assertEquals(4, list.size());
	}
}
