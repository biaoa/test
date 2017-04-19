package com.linle.common.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: PhoneUtil
 * @Description: 手机号码相关util
 * @author pangd
 * @date 2016年3月11日 上午9:47:08
 *
 */
public class PhoneUtil {

	public static boolean verifyPhone(String phone) {
		//不为空
		if (!StringUtil.isNotNull(phone)) {
			return false;
		}
		//手机号码格式
		String regExp = "^1[3-9][0-9]\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		if (!m.find()) {
			return false;
		}
		return true;
	}
	
	  public static int CreateValidateCode() {
	        return new Random().nextInt(9000) + 1000;
	    }

}
