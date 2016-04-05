package com.zaad.web.search.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Order;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Playlist;
import com.zaad.web.search.PlaylistSearch;
import com.zaad.web.search.TutorSearch;
import com.zaad.web.search.VideoSearch;

@Service
public class PlaylistSearchImpl implements PlaylistSearch {
	public static final String INDEX_NAME = "playlist_p";
	public static final String TYPE_NAME = "detail";
	
	private static final int MAX_AGG_SIZE = 30;
	
	@Resource
	private TransportClient client;
	
	@Resource
	private TutorSearch tutorSearch;
	@Resource
	private VideoSearch videoSearch;
	
	public List<Playlist> listRecommendedPlaylist(String userId, int page, int size) {
		List<Playlist> list = new ArrayList<Playlist>();
		
		QueryBuilder mainQuery = matchAllQuery();
		
		AvgBuilder subAggs = AggregationBuilders.avg("avg_popularity")
			.field("recommendarity")
		;
		
		TermsBuilder mainAggs = AggregationBuilders.terms("playlist")
			.field("playlistId")
			.size(MAX_AGG_SIZE)
			.order(Order.aggregation("avg_popularity", false))
			.subAggregation(subAggs)
		;
		
		SearchResponse response = client.prepareSearch(VideoSearchImpl.INDEX_NAME)
				.setTypes(VideoSearchImpl.TYPE_NAME)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .addAggregation(mainAggs)
		        .setSize(0)
		        .execute()
		        .actionGet()
        ;
		
		Terms terms = response.getAggregations().get("playlist");
		Playlist playlist = null;
		for ( Bucket bucket : terms.getBuckets() ) {
			playlist = this.getOneById(bucket.getKeyAsString());
			
			if ( playlist != null ) {
				playlist.setVideos(videoSearch.listVideoByPlaylistId(playlist.getPlaylistId(), 1, VideoSearchImpl.MAX_VIDEO_COUNT));
				if ( playlist.getVideos() != null && !playlist.getVideos().isEmpty() ) {
					list.add(playlist);
					
					if ( list.size() >= size ) {
						break;
					}
				}
			}
		}
		
		return list;
	}

	public List<Playlist> listRecommendedPlaylistByTutorId(String tutorId, int page, int size) {
		int AGG_SIZE = 20;
		List<Playlist> list = new ArrayList<Playlist>();
		
		QueryBuilder mainQuery = termQuery("tutorId", tutorId);
		
		AvgBuilder subAggs = AggregationBuilders.avg("avg_popularity")
			.field("recommendarity")
		;
		
		TermsBuilder mainAggs = AggregationBuilders.terms("playlist")
			.field("playlistId")
			.size(AGG_SIZE)
			.order(Order.aggregation("avg_popularity", false))
			.subAggregation(subAggs)
		;
		
		SearchResponse response = client.prepareSearch(VideoSearchImpl.INDEX_NAME)
				.setTypes(VideoSearchImpl.TYPE_NAME)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .addAggregation(mainAggs)
		        .setSize(0)
		        .execute()
		        .actionGet()
        ;
		
		Terms terms = response.getAggregations().get("playlist");
		Playlist playlist = null;
		for ( Bucket bucket : terms.getBuckets() ) {
			playlist = this.getOneById(bucket.getKeyAsString());
			
			if ( playlist != null ) {
				playlist.setVideos(videoSearch.listVideoByPlaylistId(playlist.getPlaylistId(), 1, VideoSearchImpl.MAX_VIDEO_COUNT));
				if ( playlist.getVideos() != null && !playlist.getVideos().isEmpty() ) {
					list.add(playlist);
					
					if ( list.size() >= size ) {
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	public Playlist getOneById(String id) {
		Playlist playlist = null;
		
		GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, id)
		        .execute()
		        .actionGet()
        ;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			playlist = mapper.readValue(response.getSourceAsString(), Playlist.class);
			playlist.setTutor(tutorSearch.getOneById(playlist.getTutorId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return playlist;
	}
}
