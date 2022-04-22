package com.zaad.web.search;

import java.util.List;

import com.zaad.common.domain.Video;
import com.zaad.web.domain.TagCount;

public interface VideoSearch {
	List<Video> listRecentVideo(String userId, int page, int size);
	
	List<Video> listRecommendedVideo(String userId, int page, int size);

	List<Video> listPopularVideo(String userId, int page, int size);
	
	List<Video> listVideoByPlaylistId(String playlistId, int index, int size);
	
	List<Video> listPopularVideoByTutorId(String tutorId, int page, int size);
	
	Video getOneByVideoId(String videoId);
	
	Video getOneById(String id);
	
	List<Video> searchVideo(String userId, String text, int page, int size, String sortType);
	
	List<Video> searchVideoByTags(String userId, List<String> tags, int page, int size, String sortType);
	
	List<TagCount> listRelatedTags(String tag, int size);
	
	List<TagCount> listRelatedTagsOfSearch(String text, int size);
	
}
