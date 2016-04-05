package com.zaad.web.search.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.existsQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Order;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaad.common.domain.Tutor;
import com.zaad.web.search.AdvertiseSearch;
import com.zaad.web.search.TutorSearch;

@Service
public class AdvertiseSearchImpl implements AdvertiseSearch {
	public static final String INDEX_NAME = "video_p";
	public static final String TYPE_NAME = "detail";
	
	private static final int MAX_AGG_SIZE = 100;
	
	@Resource
	private TransportClient client;
	
	@Autowired
	private TutorSearch tutorSearch;

	public List<Tutor> listBanner(int size, String sortType) {
		String NESTED_FILED = "tutor";
		String aggOrder = "R";
		if ( "R".equals(sortType) )  {
			aggOrder = "R";
		} else if ( "P".equals(sortType) )  {
			aggOrder = "P";
		}
	
		
		QueryBuilder mainQuery = matchAllQuery();
		;
		
		QueryBuilder boolFilter = boolQuery()
				.must(existsQuery(NESTED_FILED + ".adSite"))
				.must(existsQuery(NESTED_FILED + ".adSiteTitle"))
				.must(existsQuery(NESTED_FILED + ".profileImagePath"))
				.must(existsQuery(NESTED_FILED + ".bannerImagePath"))
		;		
		
				
		NestedQueryBuilder nestedQuery = nestedQuery(NESTED_FILED, boolFilter);
		
		QueryBuilder queryBuilder = boolQuery()
										.must(mainQuery)
										.filter(nestedQuery)
									;
		
			
		AvgBuilder subAggs = AggregationBuilders.avg("avg")
				.field(aggOrder)
		;
			
		TermsBuilder mainAggs = AggregationBuilders.terms("tutor")
			.field("tutorId")
			.size(MAX_AGG_SIZE)
			.order(Order.aggregation("avg", false))
			.subAggregation(subAggs)
		;
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(queryBuilder)
		        .addAggregation(mainAggs)
		        .setSize(0)
		        .execute()
		        .actionGet()
        ;
		
		Terms terms = response.getAggregations().get("tutor");
		List<Tutor> tutors = new ArrayList<Tutor>();
		for ( Bucket bucket : terms.getBuckets() ) {
			if ( size > tutors.size() ) {
				tutors.add(tutorSearch.getOneById(bucket.getKeyAsString()));
			}
		}
		
		return tutors;
	}
	
	
}
