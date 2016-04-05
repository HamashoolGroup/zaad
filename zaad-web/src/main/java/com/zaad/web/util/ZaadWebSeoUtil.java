package com.zaad.web.util;

import java.util.HashMap;
import java.util.Map;

import com.zaad.common.ZaadCommonConstants;

public class ZaadWebSeoUtil {
	public static Map<String, String> getCommonOgProperties() {
		Map<String, String> commonProperties = new HashMap<String, String>();
		commonProperties.put("site_name", ZaadCommonConstants.ZAAD_SITE_NAME);
		
		return commonProperties;
	}
}
