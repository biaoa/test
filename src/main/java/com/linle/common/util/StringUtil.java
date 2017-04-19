package com.linle.common.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	public static final Logger _logger = LoggerFactory.getLogger(StringUtil.class);
	private final static Pattern DYNAMIC_PATTERN = Pattern.compile("@\\d+\\s+");

	public static String addPrefix(int num, String prefix, int length) {
		return String.format("%04d", num);
	}

	public static boolean isStringAvaliable(String string) {
		return string != null && !"".equals(string.trim());
	}

	/**
	 * 判断当前字符串是否为NUll或空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断当前字符串是否不为NUll或空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		return str != null && str.trim().length() != 0;
	}

	/**
	 * 
	 * 判断链接是否以http开头的
	 * 
	 * @param link
	 * @return
	 */
	public static boolean isHttpAvaliable(String link) {
		Pattern pattern2 = Pattern.compile("(http|ftp|https):\\/\\/([\\w.]+\\/?)\\S*");
		Matcher matcher2 = pattern2.matcher(link);
		if (!matcher2.find()) {
			return false;
		}
		return true;
	}

	/**
	 * 将字符串转换为整形
	 *
	 * @param str
	 * @return
	 */
	public static int toInt(final String str) {
		return toInt(str, 0);
	}

	/**
	 * 将字符串转换为整形
	 *
	 * @param str
	 * @param i
	 * @return
	 */
	public static int toInt(final String str, final int i) {
		int result = 0;

		try {
			result = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			result = i;
		}
		return result;
	}

	/**
	 * 将字符串转换为整形
	 *
	 * @param str
	 * @return
	 */
	public static Integer toInteger(final String str) {
		Integer o = null;
		if (str == null)
			return null;
		try {
			o = Integer.valueOf(str.trim());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return o;
	}

	/**
	 * 将字符串转换为整形
	 *
	 * @param str
	 * @return
	 */
	public static Long toLong(final String str) {
		Long o = null;
		if (str == null)
			return null;
		try {
			o = Long.valueOf(str.trim());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return o;
	}

	/**
	 * 
	 */
	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	/**
	 * 去掉字符串的html代码
	 * 
	 * @param htmlStr
	 *            字符串
	 * @return 结果
	 */
	public static String htmlToStr(String htmlStr) {
		String result = "";
		boolean flag = true;
		if (htmlStr == null) {
			return null;
		}
		char[] a = htmlStr.toCharArray();
		int length = a.length;
		for (int i = 0; i < length; i++) {
			if (a[i] == '<') {
				flag = false;
				continue;
			}
			if (a[i] == '>') {
				flag = true;
				continue;
			}
			if (flag == true) {
				result += a[i];
			}
		}
		return result.toString();
	}

	/**
	 * 字符串按字节截取
	 * 
	 * @param text
	 *            原字符
	 * @param length
	 *            截取长度
	 * @return String
	 * @author 张能军
	 */

	public static String splitString(String text, int length) {
		return splitString(text, length, "...");

	}

	/**
	 * 字符串按字节截取
	 * 
	 * @param text
	 *            原字符
	 * @param length
	 *            截取长度
	 * @param endWith
	 *            替代符
	 * @return String
	 */

	public static String splitString(String text, int length, String endWith) {
		if (text == null || "".equals(text)) {
			return "";
		}
		if (length <= 0) {
			return "";
		}
		int textLength = text.length();
		int byteLength = 0;
		StringBuffer returnStr = new StringBuffer();
		String returnString = "";
		for (int i = 0; i < textLength && byteLength < length * 2; i++) {
			String str_i = text.substring(i, i + 1);
			if (str_i.getBytes().length == 1) {// 英文
				byteLength++;
			} else {// 中文
				byteLength += 2;
			}
			returnStr.append(str_i);
		}
		try {
			if (byteLength < text.getBytes("GBK").length) {// getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3
				returnStr.append(endWith);
			}

			returnString = returnStr.toString();
			if (returnString.getBytes("GBK").length % 2 == 1) { // 存在单数英文字符少截取一个字，避免显示错位。例如:“KHS单车生活馆”截取4个汉字时
				returnString = returnString.substring(0, returnString.length() - 1);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}

		return returnString;

	}

	public static String leftTime(Date RegistrationDeadline) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String systemTime = sdf.format(new Date()).toString();
		Date sysTime = sdf.parse(systemTime);
		// 将截取到的时间字符串转化为时间格式的字符串
		Date beginTime = RegistrationDeadline;
		String bgTime = sdf.format(beginTime).toString();
		Date bgeTime = sdf.parse(bgTime);
		// 默认为毫秒，除以1000是为了转换成秒
		long interval = (bgeTime.getTime() - sysTime.getTime()) / 1000;// 秒
		long day = interval / (24 * 3600);// 天
		long hour = interval % (24 * 3600) / 3600;// 小时
		long minute = interval % 3600 / 60;// 分钟
		if (interval < 0) {
			return "";
		}
		return "还有" + day + "天" + hour + "小时" + minute + "分";
	}

	public static String escapeSQLLike(String likeStr) {
		String str = StringUtils.replace(likeStr, "_", "\\_");
		str = StringUtils.replace(str, "%", "\\%");
		return str;
	}

	/***
	 * 抽取在动态内容中@id的userId
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> getUserIdsFromContent(String content) {
		if (content == null || content.trim().length() == 0)
			return null;
		Matcher mc = DYNAMIC_PATTERN.matcher(content);
		List<String> ids = new ArrayList<String>();
		while (mc.find()) {
			String userUrl = mc.group();
			String userId = userUrl.substring(1).trim();
			if (!ids.contains(userId))
				ids.add(userId);
		}
		return ids;
	}

	public static void main(String[] args) {

		// String content="<p class=&quot;p0&quot; style=&quot;MARGIN: 0pt
		// 40.8pt; WORD-BREAK: break-all; LINE-HEIGHT: 15.6pt; TEXT-ALIGN:
		// left&quot;><span style=&quot;FONT-SIZE: 8pt; COLOR: rgb(0,0,0);
		// FONT-FAMILY: &#8217;&#718;&#805;&#8217;; mso-spacerun:
		// &#8217;yes&#8217;&quot;>有居民来咨询，男方户籍在本地，女方户籍在省外且是农业户籍的，<font
		// face=&quot;Arial&quot;>如何办理生殖健康服务证</font>。</span><span
		// style=&quot;FONT-SIZE: 8pt; COLOR: rgb(0,0,0); FONT-FAMILY:
		// &#8217;&#718;&#805;&#8217;; mso-spacerun:
		// &#8217;yes&#8217;&quot;><o:p></o:p></span></p><p class=&quot;p0&quot;
		// style=&quot;MARGIN-TOP: 0pt; MARGIN-BOTTOM:
		// 0pt&quot;>&nbsp;</p><!--EndFragment-->";
		// content=htmlToStr(content);
		// System.out.println(content);

		// String text = "。发.。篇>所q阿s似hf的f＊*发千万s";
		// for(int i = 0; i< text.length();i++){
		// String s = StringUtil.splitString(text,i+1,"...");
		// System.out.println(s+"--------------------------"+(i+1));
		// }

		System.out.println(splitString("KHS单车生活馆", 4, ""));
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 * 
	 * @param stringArray
	 *            原数组
	 * @param source
	 *            查找的字符串
	 * @return 是否找到
	 */
	public static boolean contains(String[] stringArray, String source) {
		// 转换为list
		List<String> tempList = Arrays.asList(stringArray);

		// 利用list的包含方法,进行判断
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}

	public static String join(Object[] o, String flag) {
		StringBuffer str_buff = new StringBuffer();

		for (int i = 0, len = o.length; i < len; i++) {
			str_buff.append(String.valueOf(o[i]));
			if (i < len - 1)
				str_buff.append(flag);
		}

		return str_buff.toString();
	}

	/**
	 * @param <T>
	 *            数组中的元素类型
	 * @param arrs
	 *            需要删除元素的数组。
	 * @param index
	 *            需要删除的元素的索引[]（出界时抛异常）。
	 * @return 指定类型的新数组。
	 */
	public static <T> T[] removeArrayItem(T[] arrs, int index[]) {
		int len = arrs.length;
		if (index.length < 0 || index.length > len) {
			throw new IllegalArgumentException("索引出界");
		}
		List<T> list = new LinkedList<T>();
		for (int i = 0; i < len; i++) {
			for (int j : index) {
				if (i != j) {
					list.add(arrs[i]);
				}
			}
		}
		// 这里将改变传入的数组参数 arrs 的值
		arrs = list.toArray(arrs);
		return java.util.Arrays.copyOf(arrs, arrs.length - 1);
	}

	/**
	 * 
	 * @Description 多个数组合并
	 * @param first
	 * @param rest
	 * @return T[] $
	 */
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public static <T> int ArrayIndexOf(T[] arrs, String str) {
		for (int i = 0; i < arrs.length; i++) {
			if (str.equals(arrs[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @Description 查找目标字符串在源字符串中出现的次数。如果源字符串/目标字符串为null 返回0
	 * @param source
	 *            源字符串
	 * @param str
	 *            目标字符
	 * @return int
	 */
	public static int charInStringCount(String source, String str) {
		if (source == null || str == null) {
			return 0;
		}
		int count = 0;
		for (String s : source.split("")) {
			if (str.equals(s)) {
				count++;
			}
			continue;
		}
		return count;
	}

	/**
	 * 
	 * @Description 数组去重
	 * @param source
	 * @return String[]
	 */
	public static String[] arrayUnique(String[] source) {
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(source));
		return set.toArray(new String[0]);
	}

	public static String[] arrayTrim(String[] source) {
		List<String> tmp = new ArrayList<String>();
		for (String str : source) {
			if (str != null && str.length() != 0) {
				tmp.add(str);
			}
		}
		source = tmp.toArray(new String[0]);
		return source;
	}

	/**
	 * 隐藏部分手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static String hidePhoneNum(String phone) {
		String result = "";
		if (phone != null && !"".equals(phone)) {
			result = phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return result;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 
	 * @Description 用户传入7-101 返回7-1-101
	 * @param str
	 * @return String $
	 */
	public static String returnNewHouseNo(String str) {
		if (StringUtil.isNotNull(str)) {
			int index = StringUtil.charInStringCount(str, "-");
			if (index == 1) {
				// 原来的
				String old1 = str.substring(0, index);
				String old2 = str.substring(index);
				return old1 + "-1" + old2;
			}
		}
		return str;
	}

}
