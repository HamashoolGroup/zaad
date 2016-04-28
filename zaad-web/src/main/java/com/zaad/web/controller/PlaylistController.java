package com.zaad.web.controller;

import java.util.ArrayList;
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
import com.zaad.common.domain.Playlist;
import com.zaad.common.domain.Video;
import com.zaad.web.domain.HtmlSeo;
import com.zaad.web.exception.ZaadContentNotFoundException;
import com.zaad.web.search.PlaylistSearch;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadWebSeoUtil;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
	@Autowired
	private PlaylistSearch playlistSearch;
	
	@Autowired
	private VideoSearch videoSearch;
	
	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable("id") String playlistId) {
		Playlist playlist = playlistSearch.getOneById(playlistId);
		
		if ( playlist == null ) {
			throw new ZaadContentNotFoundException();
		} else {
			List<Video> videos = videoSearch.listVideoByPlaylistId(playlistId, 0, 10);
			playlist.setVideos(videos);
		}
		
		return new ModelAndView("zaad.playlist.detail")
				.addObject("playlist", playlist)
				.addAllObjects(ZaadWebSeoUtil.getCommonOgProperties())
				.addObject("og_type", ZaadCommonConstants.ZAAD_WEB_OG_TYPE_WEB)
				.addObject("og_img", ZaadCommonConstants.ZAAD_OG_DEFAULT_IMG)
				.addObject("url", ZaadCommonConstants.ZAAD_SITE_URL + "/playlist/" + playlistId)
				.addObject("seo", generateHtmlSeo(playlist))
		;
	}
	
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
	
	private HtmlSeo generateHtmlSeo(Playlist playlist) {
		HtmlSeo htmlSeo = new HtmlSeo();
		htmlSeo.setTitle(playlist.getTitle() + ZaadCommonConstants.ZAAD_SITE_NAME_WITH_HYPHEN);
		htmlSeo.setDescription(playlist.getTitle() + " by " + playlist.getTutor().getTutorName());
		htmlSeo.addKeywords(new ArrayList<String>(playlist.getTags()));
		
		return htmlSeo;
	}
}
