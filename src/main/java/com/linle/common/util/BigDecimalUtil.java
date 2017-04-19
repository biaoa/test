package com.linle.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author pangd
 * @Description
 * @date 2016年10月21日下午5:18:08
 */
public class BigDecimalUtil {
	static BigDecimal b = new BigDecimal(1);

	BigDecimalUtil() {
	}

	public static BigDecimal add(BigDecimal args1, BigDecimal args2) {
		return args1.add(args2);
	}

	public static BigDecimal sub(BigDecimal args1, BigDecimal args2) {
		return args1.subtract(args2);
	}

	// 煞笔乘法没有保留两位小数的设置 所以除了1
	public static BigDecimal mul(BigDecimal args1, BigDecimal args2) {
		return args1.multiply(args2).divide(b, 2, RoundingMode.HALF_UP);
	}

	public static BigDecimal divide(BigDecimal args1, BigDecimal args2) {
		return args1.divide(args2, 2, RoundingMode.HALF_UP);
	}
	
	/**
	 * 
	 * @Description 0等于 1大于 1小于
	 * @param args1
	 * @param args2
	 * @return int
	 * $
	 */
	public static int compareTo(BigDecimal args1, BigDecimal args2){
		return args1.compareTo(args2);
	}
	
	/**
	 * string 转 BigDecimal
	 * @param string
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String string){
		return string==null||"".equals(string)?BigDecimal.ZERO:new BigDecimal(string);
	}
}
