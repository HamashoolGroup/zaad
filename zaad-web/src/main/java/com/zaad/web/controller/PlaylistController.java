package com.zaad.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Playlist;
import com.zaad.web.search.PlaylistSearch;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
	@Autowired
	private PlaylistSearch playlistSearch;
	
	@RequestMapping(value="/list/recommended", produces = "application/json; charset=utf8")
	public String listRecommendedPlaylist(@RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Playlist> playlists = playlistSearch.listRecommendedPlaylist("userId", page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(playlists);
	}
	
	@RequestMapping(value="/list/bytutor/{tutorId}", produces = "application/json; charset=utf8")
	public String listRecommendedPlaylistByTutorId(@PathVariable("tutorId") String tutorId, @RequestParam(value="page", required=false, defaultValue="1") int page, @RequestParam(value="size", required=false, defaultValue="4") int size) throws JsonProcessingException {
		List<Playlist> playlists = playlistSearch.listRecommendedPlaylistByTutorId(tutorId, page, size);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(playlists);
	}
}
