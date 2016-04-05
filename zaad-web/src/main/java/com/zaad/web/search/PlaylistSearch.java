package com.zaad.web.search;

import java.util.List;

import com.zaad.common.domain.Playlist;

public interface PlaylistSearch {
	List<Playlist> listRecommendedPlaylist(String userId, int page, int size);
	
	List<Playlist> listRecommendedPlaylistByTutorId(String tutorId, int page, int size);
	
	public Playlist getOneById(String id);
}
