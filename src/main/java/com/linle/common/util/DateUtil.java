package com.linle.common.util;

/**
 * @功能:时间格式转换Help类
 * @作者: RK
 * @创建时间：2008-2-19 下午02:55:53
 * @说明：请在这里加入说明！
 * @修改者：
 * @修改时间：
 * @说明：请在这里加入说明！
 **/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	public static final Logger _logger = LoggerFactory.getLogger(DateUtil.class);
	static int count = 0;
	
	public static List<String> getallyear_month(int round){
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month = 12;
		List<String> list = new ArrayList<String>();
		for(int j = round;j>0;j--){
			if(j==1){
				month = cal.get(Calendar.MONTH)+1;
			}
			for (int i = 1; i <=month; i++) {
				list.add("'"+(year-j+1)+"年"+i+"月'");
			}
		}
		return list;
	}

	public static String getCurrentDateString() {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		result = sdf.format(nowDate);
		return result;
	}

	public static String getCurrentDateString(String format) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date nowDate = new Date();
		result = sdf.format(nowDate);
		return result;
	}

	public static String convertDateToString(Date date) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		result = sdf.format(date);
		return result;
	}

	public static String convertDateToString(Date date, String format) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		result = sdf.format(date);
		return result;
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static String getCurrentTimeString() {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
		Date nowDate = new Date();
		result = sdf.format(nowDate);
		return result;
	}

	public static String getCurrentDateTime() {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = new Date();
		result = sdf.format(nowDate);
		return result;
	}

	public static String getTimeStamp() {
		StringBuffer result = new StringBuffer();
		if(count < 100) {
			count++;
		}else {
			count = 1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate = new Date();
		result.append(sdf.format(nowDate)).append(count);
		return result.toString();
	}
	public static String getTimeStamps() {
		StringBuffer result = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		result.append(sdf.format(nowDate));
		return result.toString();
	}
	

	public static String dateFormat(String date) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date theDate = null;
		try {
			theDate = (Date) sdf.parse(date);
			String dateFormat = "yyyy-MM-dd hh:mm:ss";
			sdf = new SimpleDateFormat(dateFormat);
			result = sdf.format(theDate);
		} catch (Exception e) {
		}
		return result;
	}

	public static String dateFormat(String date, String format) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date theDate = null;
		try {
			theDate = (Date) sdf.parse(date);
			sdf = new SimpleDateFormat(format);
			result = sdf.format(theDate);
		} catch (Exception e) {
		}
		return result;
	}

	public static Date convertStrToDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace(); _logger.error("出错了", e);
		}
		return date;
	}

	public static long getCurrentTime() {
		return (new Date()).getTime();
	}

	/*
	 * 增加1/1000秒
	 */
	public static Date addMillisecond(Date dt, int millisecond) {
		return addSecond(dt, (long) millisecond);
	}

	public static Date addMillisecond(Date dt, long millisecond) {
		dt.setTime(dt.getTime() + millisecond);
		return dt;
	}

	/*
	 * 增加秒
	 */
	public static Date addSecond(Date dt, int second) {
		return addSecond(dt, (long) second);
	}

	public static Date addSecond(Date dt, float second) {
		return addSecond(dt, (long) second);
	}

	public static Date addSecond(Date dt, long second) {
		return addMillisecond(dt, 1000L * second);
	}

	public static Date addSecond(Date dt, double second) {
		Double millisecond = new Double(1000D * second);
		return addMillisecond(dt, millisecond.longValue());
	}

	/*
	 * 增加分钟
	 */
	public static Date addMinute(Date dt, int minute) {
		return addMinute(dt, (long) minute);
	}

	public static Date addMinute(Date dt, float minute) {
		return addMinute(dt, (long) minute);
	}

	public static Date addMinute(Date dt, long minute) {
		return addMillisecond(dt, 60000L * minute);
	}

	public static Date addMinute(Date dt, double minute) {
		Double millisecond = new Double(60000D * minute);
		return addMillisecond(dt, millisecond.longValue());
	}

	/*
	 * 增加小时
	 */
	public static Date addHour(Date dt, int hour) {
		return addHour(dt, (long) hour);
	}

	public static Date addHour(Date dt, float hour) {
		return addHour(dt, (long) hour);
	}

	public static Date addHour(Date dt, long hour) {
		return addMillisecond(dt, 0x36ee80L * hour);
	}

	public static Date addHour(Date dt, double hour) {
		Double millisecond = new Double(3600000D * hour);
		return addMillisecond(dt, millisecond.longValue());
	}

	/*
	 * 增加天
	 */
	public static Date addDay(Date dt, int day) {
		return addDay(dt, (long) day);
	}

	public static Date addDay(Date dt, float day) {
		return addDay(dt, (long) day);
	}

	public static Date addDay(Date dt, long day) {
		return addMillisecond(dt, 0x5265c00L * day);
	}

	public static Date addDay(Date dt, double day) {
		Double millisecond = new Double(86400000D * day);
		return addMillisecond(dt, millisecond.longValue());
	}

	/*
	 * 增加月
	 */
	public static Date addMonth(Date dt, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(2, cal.get(2) + month);

		return cal.getTime();
	}

	public static Date addMonth(Date dt, float month) {
		return addMonth(dt, (int) month);
	}

	public static Date addMonth(Date dt, long month) {
		return addMonth(dt, (new Long(month)).intValue());
	}

	public static Date addMonth(Date dt, double month) {
		double floorMonth = Math.floor(month);
		double decimalMonth = month - floorMonth;
		dt = addMonth(dt, (new Double(floorMonth)).intValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(2, cal.get(2) + 1);
		Date nextdt = cal.getTime();
		long monthMillisecond = nextdt.getTime() - dt.getTime();
		double millisecond = (double) monthMillisecond * decimalMonth;
		return addMillisecond(dt, (long) millisecond);
	}

	/*
	 * 增加年
	 */
	public static Date addYear(Date dt, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(1, cal.get(1) + year);
		return cal.getTime();
	}

	public static Date addYear(Date dt, float year) {
		return addYear(dt, (int) year);
	}

	public static Date addYear(Date dt, long year) {
		return addYear(dt, (new Long(year)).intValue());
	}

	public static Date addYear(Date dt, double year) {
		double floorYear = Math.floor(year);
		double decimalYear = year - floorYear;
		dt = addYear(dt, (new Double(floorYear)).intValue());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(1, cal.get(1) + 1);
		Date nextdt = cal.getTime();
		long yearMillisecond = nextdt.getTime() - dt.getTime();
		double millisecond = (double) yearMillisecond * decimalYear;
		return addSecond(dt, (long) millisecond);
	}

	public static Date getFirstDateOfMonth(Date date) {
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.set(Calendar.DATE, 1);
		return calDate.getTime();
	}

	public static Date getFirstDateOfYear(Date date) {
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.set(Calendar.DATE, 1);
		calDate.set(Calendar.MONTH, 1);
		return calDate.getTime();
	}

	public static Calendar getCalendar(Date date) {
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return calDate;
	}

	public static Date getFirstDateOfWeek(Date date) {
		Date result = null;
		Calendar calDate = getCalendar(date);
		int firstDay = calDate.get(Calendar.DAY_OF_WEEK);
		result = addDay(calDate.getTime(), -firstDay + 1);
		return result;
	}

	public static Date getLastDateOfWeek(Date date) {
		Date result = null;
		Calendar calDate = getCalendar(date);
		int firstDay = calDate.get(Calendar.DAY_OF_WEEK);
		result = addDay(calDate.getTime(), 7 - firstDay);
		return result;
	}

	public static Date getLastDateOfMonth(Date date) {
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.set(Calendar.DATE, calDate.getActualMaximum(Calendar.DATE));

		return calDate.getTime();
	}

	/**
	 * 取得一个月的天数
	 * 
	 * @param strDate
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.getActualMaximum(Calendar.DATE);

	}

	public static boolean beforeIgnoreDay(Date a, Date b) {
		boolean result = false;
		long la = a.getHours() * 60 * 60 + a.getMinutes() * 60 + a.getSeconds();
		long lb = b.getHours() * 60 * 60 + b.getMinutes() * 60 + b.getSeconds();
		if (la <= lb) {
			return true;
		}
		return result;
	}

	/**
	 * 判断某一个时间是否在另一个时间之后
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean afterIgnoreDay(Date a, Date b) {
		boolean result = false;
		long la = a.getHours() * 60 * 60 + a.getMinutes() * 60 + a.getSeconds();
		long lb = b.getHours() * 60 * 60 + b.getMinutes() * 60 + b.getSeconds();
		if (la >= lb) {
			return true;
		}
		return result;
	}

	public static boolean equalDay(Date a, Date b) {
		boolean result = false;
		if (a.getYear() == b.getYear() && a.getMonth() == b.getMonth()
				&& a.getDay() == b.getDay()) {
			result = true;
		}
		return result;
	}

	public static boolean isNotifyDay(Date thisDate, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.addMonth(thisDate, month));
		return System.currentTimeMillis() - c.getTimeInMillis() > 0;
	}

	public static boolean isEffectiveDate(Date startDate, Date endDate)
			throws Exception {
		if (startDate == null || endDate == null)
			throw new Exception("日期不存在");
		Date now = new Date();
		Long mill = null;
		Long startMill = null;
		Long endMill = null;

		Calendar c = Calendar.getInstance();
		c.setTime(now);
		mill = c.getTimeInMillis();

		c.setTime(startDate);
		startMill = c.getTimeInMillis();

		c.setTime(endDate);
		endMill = c.getTimeInMillis();

		return mill >= startMill && mill <= endMill;
	}

	public static boolean isLateThanEndDate(Date endDate) throws Exception {
		if (endDate == null)
			throw new Exception("日期不正确");
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		Long nowMill = cal.getTimeInMillis();

		cal.setTime(endDate);
		Long endMill = cal.getTimeInMillis();

		return nowMill > endMill;
	}

	public static boolean isEarlyThanStartDate(Date startDate) throws Exception {
		if (startDate == null)
			throw new Exception("日期不正确");
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		Long nowMill = cal.getTimeInMillis();

		cal.setTime(startDate);
		Long endMill = cal.getTimeInMillis();

		return nowMill < endMill;
	}

	public static void main(String[] argv) throws Exception {
		System.out.println(addHour(new Date(), 30));
	}

	public static Date getTrueNotifyTime(String notifyTime) throws Exception {
		Calendar cal = Calendar.getInstance();
		Date now = new Date();// 当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sNow = format.format(now);
		notifyTime = sNow + " " + notifyTime;

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date notifyDateTime = format1.parse(notifyTime);
		cal.setTime(notifyDateTime);
		Long notifyMill = cal.getTimeInMillis();

		cal.setTime(now);
		Long nowMill = cal.getTimeInMillis();
		if (nowMill <= notifyMill) {
			return notifyDateTime;
		}
		return DateUtil.addDay(notifyDateTime, 1);

	}

	public static String getTimeDistance(Date dt1, Date dt2) {
		long preSec = dt1.getTime() - dt2.getTime();
		if (preSec < 0) {
			preSec = -preSec;
		}
		long lDay = preSec / (3600 * 24 * 1000);
		preSec = preSec - lDay * (3600 * 24 * 1000);
		long lHour = preSec / (3600 * 1000);
		preSec = preSec - lHour * (3600 * 1000);
		long minute = preSec / (60 * 1000);
		String returnValue = Long.toString(lDay) + "天" + Long.toString(lHour)
				+ "小时" + Long.toString(minute) + "分钟";
		return returnValue;
	}
	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	 public static int daysBetween(Date smdate,Date bdate) throws ParseException{    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	       return Integer.parseInt(String.valueOf(between_days));           
	    } 
	 
	 /***
	  * 字符串lang改成“yyyy-MM-dd HH:mm:ss”
	  * @param dateS
	  * @return
	  */
	 public static String strDateFormat(String dateStr){
			long dateL = Long.parseLong(dateStr);
	        Date dat=new Date(dateL);
	        Calendar gc = new GregorianCalendar();   
	        gc.setTime(dat);
	        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        return df.format(gc.getTime());
		}
	 
	 public static int  getYear(){
		 LocalDate today = LocalDate.now(); 
		 int year = today.getYear(); 
		 return year;
	 }
	 
	 public static int  getMonth(){
		 LocalDate today = LocalDate.now(); 
		 int month = today.getMonthValue(); 
		 return month;
	 }
	 
	 public static int  getDay(){
		 LocalDate today = LocalDate.now(); 
		 int day = today.getDayOfMonth();
		 return day;
	 }
	 
	 public static int getHour(){
		 LocalTime time = LocalTime.now();
		 return time.getHour();
	 }
	 
	 public static int getMinute(){
		 LocalTime time = LocalTime.now();
		 return time.getMinute();
	 }
	 
	 
	 public static Date StringToDate(String str) {
		if ("".equals(str)||null==str) {
			 return null;
		}
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sf.parse(str);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return null;
	 }
	 
	 public static Date ShortStringToDate(String str){
		if ("".equals(str)||null==str) {
			 return null;
		}
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			return sf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return null;
		}
	 }
	 //这里季度操作有问题的  没找到季度函数 就暂时用3个月代替了
	 public static Date DateAddByType(Date d,String type){
		 Calendar c = Calendar.getInstance(); 
		 c.setTime(d);
		 if ("0".equals(type)) {
			 c.add(Calendar.MONTH, 1);
		}else if ("1".equals(type)) {
			c.add(Calendar.MONTH, 3);
		}else if("2".equals(type)){
			c.add(Calendar.YEAR, 1);
		}
		 return c.getTime();
	 }
	 
	 public static int DateDiffReturnMinute(Date d1,Date d2){
		 Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(d1);
		 
		 Calendar cal2 = Calendar.getInstance();
		 cal2.setTime(d2);
		 
		 long l=cal1.getTimeInMillis()-cal2.getTimeInMillis();
		 int Hour=new Long(l/(1000*60)).intValue();
		 return Hour;
	 }
	 
	 
	 /**
	  * 两个时间之间的小时差 d1-d2
	  * @Description 
	  * @param d1
	  * @param d2
	  * @return int
	  * $
	  */
	 public static int DateDiffReturnHour(Date d1,Date d2){
		 Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(d1);
		 
		 Calendar cal2 = Calendar.getInstance();
		 cal2.setTime(d2);
		 
		 long l=cal1.getTimeInMillis()-cal2.getTimeInMillis();
		 int Hour=new Long(l/(1000*60*60)).intValue();
		 return Hour;
	 }
	 
	 
	 public static int DateDiffReturnDay(Date d1,Date d2){
		 Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(d1);
		 
		 Calendar cal2 = Calendar.getInstance();
		 cal2.setTime(d2);
		 
		 long l=cal1.getTimeInMillis()-cal2.getTimeInMillis();
		 int day=new Long(l/(1000*60*60*24)).intValue();
		 return day;
	 }
	 
	 public static int compare_date(String DATE1, String DATE2) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	               // System.out.println("dt1大于dt2");
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                //System.out.println("dt1小于dt2");
	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }
	 
		public static Map<String, Object> compareDateForVote(String startDate,String endDate){
			try {
				Map<String, Object> map=new HashMap<String, Object>();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date starTime=sdf.parse(startDate);
				Date endTime=sdf.parse(endDate);
				
				String startDateStr=sdf2.format(starTime);//2016-07-28 10:00:00  传入开始时间
				String endDateStr=sdf2.format(endTime);//2016-07-29 10:00:00  传入结束时间
				//i==1 dt1 大于dt2
				int i= compare_date(getCurrentDateTime(),startDateStr);
				int m= compare_date(getCurrentDateTime(),endDateStr);
				map.put("i", i);
				map.put("m", m);
				return map;
			} catch (Exception e) {
				e.printStackTrace(); _logger.error("出错了", e);
				throw new RuntimeException("1");//有异常全部回滚
			}
			
		}
		/**
		 * 
		 * @Description 考勤时间转换 传入mm:ss 返回一个标准的Date
		 * @param str mm:ss 
		 * @return Date
		 * $
		 */
		public static Date attendanceDateConvert(String str){
			String[] array = str.split(":");
			Integer hour = Integer.parseInt(array[0]);
			Integer minute = Integer.parseInt(array[1]);
			Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例  
			ca.set(Calendar.HOUR_OF_DAY, hour);
			ca.set(Calendar.MINUTE,minute);
			ca.set(Calendar.SECOND,00);
			return ca.getTime();
		}
		
		/**
		 * 
		 * @Description 获得今天是星期几
		 * @return int
		 * $
		 */
		public static int getDayofWeek(){
			Calendar now = Calendar.getInstance();
			//一周第一天是否为星期天
			boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
			//获取周几
			int weekDay = now.get(Calendar.DAY_OF_WEEK);
			//若一周第一天为星期天，则-1
			if(isFirstSunday){
			  weekDay = weekDay - 1;
			  if(weekDay == 0){
			    weekDay = 7;
			  }
			}
			return weekDay;
		}
		/**
		 * 
		 * @Description 获得某一天是星期几
		 * @param date
		 * @return int
		 * $
		 */
		public static int getSomeDayofWeek(Date date){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			//一周第一天是否为星期天
			boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
			//获取周几
			int weekDay = cal.get(Calendar.DAY_OF_WEEK);
			//若一周第一天为星期天，则-1
			if(isFirstSunday){
			  weekDay = weekDay - 1;
			  if(weekDay == 0){
			    weekDay = 7;
			  }
			}
			return weekDay;
		}
		
		 public static int  getYear(Date date){
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 return cal.get(Calendar.YEAR);
		 }
		 
		 public static int  getMonth(Date date){
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 return cal.get(Calendar.MONTH)+1;
		 }
		 
		 public static int  getDay(Date date){
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 return cal.get(Calendar.DAY_OF_MONTH);
		 }
		
}

