package com.linle.mobileapi.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseAPIunit {
	/**
	 * 计算分页
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public static Map<String, Object> limit(int pageSize,int pageNumber){
		Map<String,Object> map=new HashMap<String, Object>();
		if (pageNumber==0) {
			pageNumber=1;
		}
		if (pageSize==0) {
			pageSize=5;
		}
		map.put("begin", pageSize*(pageNumber-1));
		map.put("end", pageSize);
		System.out.println(map);
		return map;
	}
	
	/***
	 * 生成服务订单编号
	 * 生成yyyyMMddhhmmss+4位随机数的字符串
	 * @return
	 */
	public static String GenerationOrderCode(){
		Date d=new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddhhmmss");
		int x;
		Random ne=new Random();
		x=ne.nextInt(9999-1000+1)+1000;
		return sd.format(d)+x;
	}
}
