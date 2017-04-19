package com.linle.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class OrderCode {
	/***
	 * 
	 * 生成yyyyMMdd+4位随机数+hhmmssSSS+2位随机数
	 * 并且线程同步
	 * @return
	 */
	public static synchronized String GenerationOrderCode(){
		Date d=new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sd2 = new SimpleDateFormat("hhmmssSSS");
		int x;
		Random ne=new Random();
		x=ne.nextInt(9000)+1000;
		int y = ne.nextInt(90) +10 ;
		return sd.format(d)+x+sd2.format(d)+y;
	}
	
	public static  String oldGenerationOrderCode(){
		Date d=new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddhhmmss");
		int x;
		Random ne=new Random();
		x=ne.nextInt(9999-1000+1)+1000;
		return sd.format(d)+x;
	}
	
	
	
	public static void main(String[] args) {
//		Set<String> set = new HashSet<>();
//		for (int i = 0; i < 500; i++) {
//			new Thread(new Runnable() {
//			    @Override
//			    public void run() {
//			    	for (int j = 0; j < 5; j++) {
//			    		boolean b =set.add(GenerationOrderCode());
//			    		if(!b){
//			    			System.out.println("false");
//			    			continue;
//			    		}
//			    		System.out.println("ok="+set.size());
//					}
//			    }
//			}).start();
//		}
//		for (int i = 0; i < 500; i++) {
//			new Thread(new Runnable() {
//			    @Override
//			    public void run() {
//			    	for (int j = 0; j < 5; j++) {
//			    		boolean b= set.add(GenerationOrderCode());
//			    		if(!b){
//			    			System.out.println("false");
//			    			continue;
//			    		}
//			    		System.out.println("ok="+set.size());
//					}
//			    }
//			}).start();
//		}
//		
	}
}
