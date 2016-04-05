package com.zaad.web.search;

import java.util.List;

import com.zaad.common.domain.Tutor;

public interface AdvertiseSearch {
	List<Tutor> listBanner(int size, String sortType);
}
