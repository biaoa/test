package com.linle.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonUtil {
	public static final Logger _logger = LoggerFactory.getLogger(JsonUtil.class);

	static ObjectMapper objectMapper = new ObjectMapper();

	static Gson gson = new Gson();

	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * (1)转换为普通JavaBean：readValue(json,Student.class)
	 * (2)转换为List:readValue(json,List.class).但是如果我们想把json转换为特定类型的List，比如List
	 * <Student>，就不能直接进行转换了。 因为readValue(json,List.class)返回的其实是List
	 * <Map>类型，你不能指定readValue()的第二个参数是List<Student>.class，所以不能直接转换。
	 * 我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList();
	 * 方法把得到的数组转换为特定类型的List。 (3)转换为Map：readValue(json,Map.class)
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们使用泛型，得到的也是泛型
	 *
	 * @param content
	 *            要转换的JavaBean类型
	 * @param valueType
	 *            原始json字符串数据
	 * @return JavaBean对象
	 */
	public static <T> T readValue(String content, Class<T> valueType) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return null;
	}

	/**
	 * 把JavaBean转换为json字符串 (1)普通对象转换：toJson(Student) (2)List转换：toJson(List)
	 * (3)Map转换:toJson(Map) 我们发现不管什么类型，都可以直接传入这个方法
	 *
	 * @param object
	 *            JavaBean对象
	 * @return json字符串
	 */
	public static String toJSon(Object object) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return null;
	}

	public static String toPrettyJSon(Object object) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return null;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static <T> List<T> StringToList(String str, JavaType javaType) {
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return (List<T>) objectMapper.readValue(str, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return null;

	}

	public static <T> T fromJson(String content, Class<T> valueType) {
		if (gson == null) {
			gson = new Gson();
		}
		try {
			return gson.fromJson(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		return null;
	}
	
	public static String ReadJsonFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
}
