package com.linle.common.util;

public class ExpressUtil {
	/**
	* @Description: 传入快递名称 返回对应路径。没有建表维护快递信息
	* @param expressName 快递名称
	* @return String
	 */
	public static String getPath(String expressName){
		String path="resources/images/express/mail_else.JPG";
		switch (expressName) {
		case "顺丰快递":
			path="resources/images/express/mail_shunfeng.JPG";
			break;
		case "申通快递":
			path="resources/images/express/mail_shentong.JPG";
			break;
		case "中通快递":
			path="resources/images/express/mail_zhongtong.JPG";
			break;
		case "圆通快递":
			path="resources/images/express/mail_yuantong.JPG";
			break;
		case "邮政速运":
			path="resources/images/express/mail_ems.JPG";
			break;
		case "百世快递":
			path="resources/images/express/mail_baishi.JPG";
			break;
		case "韵达快递":
			path="resources/images/express/mail_yunda.JPG";
			break;
		case "菜鸟驿站":
			path="resources/images/express/mail_cainiao.JPG";
			break;
		default:
			break;
		}
		return path;
	}
}
