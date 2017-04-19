package com.linle.common.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class LngAndLatUtil {
	/**
	 * 返回输入地址的经纬度坐标
	 * key lng(经度),lat(纬度)
	 */
	public static Map<String,Double> getLngAndLat(String address){
		Map<String,Double> map=new HashMap<String, Double>();
		 String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=CGHr4PR7AaXS1wea4ZxYLmHp";
	        String json = loadJSON(url);
	        if ("".equals(json)) {
				return map;
			}
	        System.out.println(json);
	        JSONObject obj = JSONObject.fromObject(json);
	        if(obj.get("status").toString().equals("0")){
	        	double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
	        	double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
	        	map.put("lng", lng);
	        	map.put("lat", lat);
	        }else{
//	        	System.out.println("未找到相匹配的经纬度！");
	        }
		return map;
	}
	
	 public static String loadJSON (String url) {
	        StringBuilder json = new StringBuilder();
	        try {
	            URL oracle = new URL(url);
	            URLConnection yc = oracle.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                                        yc.getInputStream()));
	            String inputLine = null;
	            while ( (inputLine = in.readLine()) != null) {
	                json.append(inputLine);
	            }
	            in.close();
	        } catch (MalformedURLException e) {
	        } catch (IOException e) {
	        }
	        return json.toString();
	    }
	 
	 public static void main(String[] args) {
//		 System.out.println(getGeocoderLatitude("杭州市s区文三西路120号"));
		Map<String,Double> map = getLngAndLat("文一西路西斗门3号天堂软件园15楼E座");
		System.out.println(map.get("lng")+","+map.get("lat"));
//		 System.out.println(getGeocoderLatitude("asdas三西州市路120号")!=null);
	}

}