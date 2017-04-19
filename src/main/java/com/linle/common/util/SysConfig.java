package com.linle.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysConfig {
	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);
	private static Properties properties = null;

	private static Properties getConfigProperties() {
		if (properties == null) {
			properties = new Properties();
//			URL url = ClassLoader.getSystemResource("sysConfig.properties");
			InputStream in=SysConfig.class.getResourceAsStream("/sysConfig.properties");
			try {
				properties.load(in);
			} catch (IOException e) {
				logger.error("加载sysConfig.properties出错！");
			}
		}
		return properties;
	}

	

	/** 数据库初始化的ddl脚本 */
	public final static String DBINIT_SCRIPT_DDL = getConfigProperties()
			.getProperty("dbInit.ddl");

	/** 数据库初始化日志目录 */
	public final static String DBINIT_LOGS = getConfigProperties()
			.getProperty("dbInit.logs");
	
	/** 存放上传文件硬盘文件夹 */
	public static final String DISK_FOLDER = getConfigProperties()
			.getProperty("disk.folder");
	
	/** 存放上传文件的文件夹 */
	public static final String UPLOAD_FOLDER = getConfigProperties()
			.getProperty("upload.folder");
	/** 房屋租售图片存放*/
	public static final String HOUSE_RESOURCE_FOLDER = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("houseResouce.folder");
	
	/** 问题报修图片 */
	public static final String REPAIR_FOLDER = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("repair.folder");
	
	/** 圈子话题图片 */
	public static final String TOPIC_FOLDER = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("topic.folder");
	
	/** 二维码房号图片 */
	public static final String QRCODEROOM_FOLDER = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("qrCodeRoom.folder");
	
	/** 商家图片 */
	public static final String SHOP_FOLDER = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("shop.folder");
	/** 用户logo*/
	public static final String USERLOGO_FOLDER = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("userlogo.folder");
	
	/** 用户退款申请的图片*/
	public static final String ORDER_REFUND = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("orderRefund.folder");
	
	/** 小区意见反馈图片 */
	public static final String COMMUNITY_SUGGESTIONS = UPLOAD_FOLDER
			+ getConfigProperties().getProperty("communitySuggestions.folder");
	
	
	/** 极光推送key */
	public final static String JPUSH_KEY = getConfigProperties()
			.getProperty("jpush.key");

	/** 极光推送secret */
	public final static String JPUSH_SECRET = getConfigProperties()
			.getProperty("jpush.secret");
	
	/** 极光推送商家key */
	public final static String SHOP_JPUSH_KEY = getConfigProperties()
			.getProperty("shop.jpush.key");

	/** 极光推送商家secret */
	public final static String SHOP_JPUSH_SECRET = getConfigProperties()
			.getProperty("shop.jpush.secret");
	
	/** 极光推送物业key */
	public final static String COMMUNITY_JPUSH_KEY = getConfigProperties()
			.getProperty("community.jpush.key");

	/** 极光推送物业secret */
	public final static String COMMUNITY_JPUSH_SECRET = getConfigProperties()
			.getProperty("community.jpush.secret");
	
	/**
	 * app下载地址
	 */
	public final static String APP_FOLDER = getConfigProperties()
			.getProperty("app.folder");
	
	/**
	 * 考勤描述图片
	 */
	public final static String ATTENDANCE_FOLDER =UPLOAD_FOLDER +getConfigProperties()
			.getProperty("attendance.folder");
	
	
	
}
