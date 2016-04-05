package com.zaad.web.search.impl;

import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.existsFilter;
import static org.elasticsearch.index.query.FilterBuilders.nestedFilter;
import static org.elasticsearch.index.query.QueryBuilders.filteredQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.NestedFilterBuilder;
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
		
		BoolFilterBuilder boolFilter = boolFilter()
				.must(existsFilter(NESTED_FILED + ".adSite"))
				.must(existsFilter(NESTED_FILED + ".adSiteTitle"))
				.must(existsFilter(NESTED_FILED + ".profileImagePath"))
				.must(existsFilter(NESTED_FILED + ".bannerImagePath"))
		;		
		
				
		NestedFilterBuilder mainFilter = nestedFilter(NESTED_FILED, boolFilter);
		FilteredQueryBuilder filteredQuery = filteredQuery(mainQuery, mainFilter);
		
			
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
		        .setSearchType(SearchType.COUNT)
		        .setQuery(filteredQuery)
		        .addAggregation(mainAggs)
		        .execute()
		        .actionGet()
        ;
		
		Terms terms = response.getAggregations().get("tutor");
		List<Tutor> tutors = new ArrayList<Tutor>();
		for ( Bucket bucket : terms.getBuckets() ) {
			if ( size > tutors.size() ) {
				tutors.add(tutorSearch.getOneById(bucket.getKey()));
			}
		}
		
		return tutors;
	}
	
	
}
