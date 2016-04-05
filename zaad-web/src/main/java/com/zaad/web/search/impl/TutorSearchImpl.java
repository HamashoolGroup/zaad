package com.zaad.web.search.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Tutor;
import com.zaad.web.search.TutorSearch;
import com.zaad.web.util.ZaadPagingUtil;

@Service
public class TutorSearchImpl implements TutorSearch {
	public static final String INDEX_NAME = "tutor_p";
	public static final String TYPE_NAME = "detail";
	
	
	@Resource
	private TransportClient client;


	public Tutor getOneById(String id) {
		Tutor tutor = null;
		
		GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, id)
		        .execute()
		        .actionGet()
        ;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			tutor = mapper.readValue(response.getSourceAsString(), Tutor.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tutor;
	}

	public List<Tutor> listRecommendedTutor(String userId, int page, int size) {
		List<Tutor> list = new ArrayList<Tutor>();
		
		QueryBuilder mainQuery = matchAllQuery();
		
		SearchResponse response = client.prepareSearch(INDEX_NAME)
				.setTypes(TYPE_NAME)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(mainQuery)
		        .setFrom(ZaadPagingUtil.getEsFrom(page, size))
		        .setSize(size)
		        .execute()
		        .actionGet()
        ;
		
		SearchHits hits = response.getHits();
		
		ObjectMapper mapper = new ObjectMapper();
		Tutor tutor = null;
		for ( SearchHit hit : hits ) {
			try {
				tutor = mapper.readValue(hit.getSourceAsString(), Tutor.class);
				list.add(tutor);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
}
