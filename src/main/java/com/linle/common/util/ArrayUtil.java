package com.linle.common.util;

public class ArrayUtil {
	
	
	/**
	 * 
	* @Description: 传入一个数组。传入要移除的字符串 返回移除之后的。如果没匹配 返回原字符串
	* @param @param a
	* @param @param str
	* @param @return
	* @return String[]
	 */
	public static String[] ArrayRemoveIndex(String[] a,String str){
		int index=0;
		for (int i = 0; i < a.length; i++) {
			if (str.equals(a[i])) {
				index=i;
				break;
			}
		}
		String[] ary = new String[a.length-1];
		System.arraycopy(a, 0, ary, 0, index);
		System.arraycopy(a, index+1, ary, index, ary.length-index);
		return ary;
	}
	
}
