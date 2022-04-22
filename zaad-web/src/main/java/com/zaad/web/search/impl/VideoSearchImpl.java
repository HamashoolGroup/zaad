package com.zaad.web.search.impl;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaad.common.domain.Video;
import com.zaad.common.util.ZaadKeywordMapper;
import com.zaad.web.domain.TagCount;
import com.zaad.web.mapper.VideoResultMapper;
import com.zaad.web.search.TutorSearch;
import com.zaad.web.search.VideoSearch;
import com.zaad.web.util.ZaadPagingUtil;

@Service
public class VideoSearchImpl implements VideoSearch {
	public static final String INDEX_NAME = "video_p";
	public static final String TYPE_NAME = "detail";
	
	public static final int MAX_VIDEO_COUNT = 100;
	private static final int MAX_AGG_SIZE = 30;
	
	private static List<String> SKIP_TAGS = new ArrayList<String>();	//exclusive tags
	
	private static Calendar cal = Calendar.getInstance();
	
	static {
		SKIP_TAGS.add("song");
		SKIP_TAGS.add("kid");
		SKIP_TAGS.add("news");
	}
	
	@Resource
	private TransportClient client;
	
	@Resource
	private TutorSearch tutorSearch;
	
	@Autowired
	private VideoResultMapper resultMapper;

	public List<Video> listRecentVideo(String userId, int page, int size) {
		cal.add(Calendar.DAY_OF_MONTH, -15);
		QueryBuilder mainQuery = rangeQuery("creationDate")
				.gte(cal.getTimeInMillis())
		;
				
		SortBuilder creationDateSort = SortBuilders.fieldSort("creationDate").order(SortOrder.DESC);
		SortBuilder recommendarityeSort = SortBuilders.fieldSort("recommendarity").order(SortOrder.DESC);
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .addSort(creationDateSort)
		        .addSort(recommendarityeSort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}

	public List<Video> listRecommendedVideo(String userId, int page, int size) {
		QueryBuilder mainQuery = getMainQueryWithSkipTags(matchAllQuery());
		SortBuilder sort = SortBuilders.fieldSort("recommendarity").order(SortOrder.DESC);
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .addSort(sort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}
	
	public List<Video> listPopularVideo(String userId, int page, int size) {
		QueryBuilder mainQuery = getMainQueryWithSkipTags(matchAllQuery());
		SortBuilder sort = SortBuilders.fieldSort("popularity").order(SortOrder.DESC);
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .addSort(sort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}

	public List<Video> listVideoByPlaylistId(String playlistId, int index, int size) {
		QueryBuilder mainQuery = termQuery("playlistId", playlistId);
		RangeQueryBuilder ragneQuery = rangeQuery("index").gt(index);
		
		BoolQueryBuilder boolQuery = boolQuery()
										.must(mainQuery)
										.filter(ragneQuery)
									;
		
		SortBuilder sort = SortBuilders.fieldSort("index").order(SortOrder.ASC);
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(boolQuery)
		        .setFrom(0)
		        .setSize(size)
		        .addSort(sort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}
	
	public List<Video> listPopularVideoByTutorId(String tutorId, int page, int size) {
		QueryBuilder mainQuery = termQuery("tutorId", tutorId);
		SortBuilder sort = SortBuilders.fieldSort("popularity").order(SortOrder.DESC);
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .addSort(sort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}

	public Video getOneByVideoId(String videoId) {
		QueryBuilder mainQuery = termQuery("videoId", videoId);
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(0)
		        .setSize(1)
		        .execute()
		        .actionGet()
        ;
		
		Video video = null;
		if ( response.getHits() != null && response.getHits().getTotalHits() > 0  ) {
			video = resultMapper.mapOne(response.getHits().getAt(0).getSourceAsString());
		}
		
		return video;
	}
	
	public Video getOneById(String id) {
		GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, id)
		        .execute()
		        .actionGet()
        ;

		Video video = null;
		if ( response.isExists() ) {
			video = resultMapper.mapOne(response.getSourceAsString());
		}
		
		return video;
	}
	
	public List<Video> searchVideo(String userId, String text, int page, int size, String sortType) {
		QueryBuilder mainQuery = boolQuery()
				.should(matchQuery("title", text))
		;
		
		SortBuilder scoreSort = SortBuilders.scoreSort();
		SortBuilder mainSort = null;
		if ( "R".equals(sortType) ) {
			mainSort = SortBuilders.fieldSort("recommendarity").order(SortOrder.DESC);
		} else {
			mainSort = SortBuilders.fieldSort("popularity").order(SortOrder.DESC);
		}
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .addSort(scoreSort)
		        .addSort(mainSort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}
	
	public List<Video> searchVideoByTags(String userId, List<String> tags, int page, int size, String sortType) {
		BoolQueryBuilder filteredQuery = boolQuery();
		for ( String tag : tags ) {
			if ( ZaadKeywordMapper.isLevel(tag) != null ) {
				filteredQuery = filteredQuery
						.must(termQuery("levels", tag))
				;
			} else {
				filteredQuery = filteredQuery
						.must(termQuery("tags", tag))
				;
			}
		}

		QueryBuilder mainQuery = null;
		mainQuery = filteredQuery;
//		if ( SKIP_TAGS.contains(tag) ) {
//			mainQuery = filteredQuery;
//		} else {
//			mainQuery = getMainQueryWithSkipTags(filteredQuery);
//		}
		
		SortBuilder mainSort = null;
		if ( "R".equals(sortType) ) {
			mainSort = SortBuilders.fieldSort("recommendarity").order(SortOrder.DESC);
		} else {
			mainSort = SortBuilders.fieldSort("popularity").order(SortOrder.DESC);
		}
		
		ScoreSortBuilder scoreSort = SortBuilders.scoreSort().order(SortOrder.DESC);
		
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .addSort(mainSort)
		        .addSort(scoreSort)
		        .execute()
		        .actionGet()
        ;
		
		List<Video> list = resultMapper.mapList(response.getHits());
		
		return list;
	}
	
	public List<TagCount> listRelatedTags(String tag, int size) {
		QueryBuilder mainQuery = boolQuery()
				.should(termQuery("tags", tag))
				.should(termQuery("levels", tag))
		;
			
		TermsBuilder mainAggs = AggregationBuilders.terms("related")
			.field("tags")
			.size(MAX_AGG_SIZE)
		;
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .addAggregation(mainAggs)
		        .setSize(0)
		        .execute()
		        .actionGet()
        ;
		
		Terms terms = response.getAggregations().get("related");
		List<TagCount> relatedTags = new ArrayList<TagCount>();
		TagCount tagCount = null;
		for ( Bucket bucket : terms.getBuckets() ) {
			if ( !tag.equals(bucket.getKey()) && size > relatedTags.size() ) {
				tagCount = new TagCount(bucket.getKeyAsString(), bucket.getDocCount());
				relatedTags.add(tagCount);
			}
		}
		
		return relatedTags;
	}
	
	public List<TagCount> listRelatedTagsOfSearch(String text, int size) {
		QueryBuilder mainQuery = boolQuery()
				.should(matchQuery("title", text))
		;
			
		TermsBuilder mainAggs = AggregationBuilders.terms("related")
			.field("tags")
			.size(MAX_AGG_SIZE)
		;
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .addAggregation(mainAggs)
		        .execute()
		        .actionGet()
        ;
		
		Terms terms = response.getAggregations().get("related");
		List<TagCount> relatedTags = new ArrayList<TagCount>();
		TagCount tagCount = null;
		for ( Bucket bucket : terms.getBuckets() ) {
			if ( !text.toLowerCase().equals(bucket.getKey()) && size > relatedTags.size() ) {
				tagCount = new TagCount(bucket.getKeyAsString(), bucket.getDocCount());
				relatedTags.add(tagCount);
			}
		}
		
		return relatedTags;
	}
	
	
	private QueryBuilder getMainQueryWithSkipTags(QueryBuilder queryBuilder) {
		List<String> skipTags = new ArrayList<String>();
		skipTags.add("song");
		skipTags.add("kid");
		skipTags.add("news");
		
		QueryBuilder mainQuery = boolQuery()
									.must(queryBuilder)
									.mustNot((termsQuery("tags", skipTags)))
								;
		return mainQuery;
	}
}
