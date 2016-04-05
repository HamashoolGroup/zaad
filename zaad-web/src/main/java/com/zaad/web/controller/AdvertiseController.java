package com.zaad.web.controller;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Tutor;
import com.zaad.web.search.AdvertiseSearch;

@RestController
@RequestMapping("/advertise")
public class AdvertiseController {
	@Autowired
	private AdvertiseSearch advertiseSearch;
	
	@RequestMapping(value="/main", produces = "application/json; charset=utf8")
	public String main() throws JsonProcessingException {
		List<Tutor> banners = advertiseSearch.listBanner(20, "P");
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(banners.get(RandomUtils.nextInt(0, banners.size())));
	}
	
	@RequestMapping(value="/side", produces = "application/json; charset=utf8")
	public String side(@RequestParam(value="size", required=false, defaultValue="5") int size) throws JsonProcessingException {
		List<Tutor> banners = advertiseSearch.listBanner(30, "P");
		
		int fromIndex = RandomUtils.nextInt(0, banners.size() - 5);
		int toIndex = fromIndex + 5;
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(banners.subList(fromIndex, toIndex));
	}
}
