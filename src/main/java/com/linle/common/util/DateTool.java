package com.linle.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
	
	/**
	 * 传入一个yyyyMMddhhmmss格式的数据(20150925105157) 返回Date
	 * @param timestr
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToDate(String timestr) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss"); 
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date = (Date) sdf1.parse(timestr);  
		Date d = sdf2.parse(sdf2.format(date));
		return d;
	}
	
}
