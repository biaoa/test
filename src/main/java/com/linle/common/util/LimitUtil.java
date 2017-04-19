package com.linle.common.util;

import java.util.Map;
/**
 * 
 * @author pangd
 * @Description 分页工具类
 */
public class LimitUtil {
	
	
	/**
	 * 计算分页
	 * @Description 把查询的map传入进来。追加分页参数。pageSize默认5,pageNumber默认1
	 * @param pageSize
	 * @param pageNumber
	 * @return map
	 */
	public static Map<String, Object> limit(Map<String, Object> map,Integer pageSize,Integer pageNumber){
		if (pageNumber==null) {
			pageNumber=0;
		}
		if (pageSize==null || pageSize==0) {
			pageSize=5;
		}
		pageNumber+=1;
		map.put("begin", pageSize*(pageNumber-1));
		map.put("end", pageSize);
		return map;
	}
}
