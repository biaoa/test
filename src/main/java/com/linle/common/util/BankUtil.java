package com.linle.common.util;

import java.util.HashMap;
import java.util.Map;

public class BankUtil {
	public static String getBankInfo(String cardNo){
		String url="https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";
		Map<String, String> map = new HashMap<>();
		map.put("_input_charset", "utf-8");
		map.put("cardNo", cardNo);
		map.put("cardBinCheck", "true");
		return PostUtil.doPostRequest(url, map, "utf-8", "utf-8");
	}
}
