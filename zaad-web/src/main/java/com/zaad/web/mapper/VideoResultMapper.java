package com.zaad.web.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Video;
import com.zaad.web.search.TutorSearch;

@Component
public class VideoResultMapper {
	@Resource
	private TutorSearch tutorSearch;
	
	private Class<Video> RESULT_CLASS = Video.class;

		public List<Video> mapList(SearchHits hits) {
			List<Video> list = new ArrayList<Video>();
			
			ObjectMapper mapper = new ObjectMapper();
			Video video = null;
			for ( SearchHit hit : hits ) {
				try {
					video = mapper.readValue(hit.getSourceAsString(), RESULT_CLASS);
					video.setTutor(tutorSearch.getOneById(video.getTutorId()));
					
					list.add(video);
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
		
		public Video mapOne(String hitSourceAsString) {
			Video video = null;
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				video = mapper.readValue(hitSourceAsString, RESULT_CLASS);
				video.setTutor(tutorSearch.getOneById(video.getTutorId()));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return video;
		}
}
