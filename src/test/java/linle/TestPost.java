package linle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.StringUtils;


public class TestPost {
	public static final String local="http://localhost/";
	public static final String server="http://cloud.hzlinle.com/";
	public static final String test ="http://localhost:8080";
	/**
	 * 调用http post请求
	 * 
	 * @author Pangd
	 */
	public static String doPostRequest(String url, Map<String, String> map,
			String reqCode, String respCode) {
		if (StringUtils.isEmpty(url)) {
			throw new IllegalArgumentException("url cannot be null! ");
		}
		if (StringUtils.isEmpty(reqCode)) {
			reqCode = "UTF-8";// 如果为空话 默认编码UTF-8
		}
		if (StringUtils.isEmpty(respCode)) {
			respCode = "UTF-8";// 如果为空话 默认编码UTF-8
		}

		HttpClient httpClient = new HttpClient();
		// 设置响应超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(180000);
		PostMethod postMethod = new PostMethod(url);

		// 使用系统系统的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置参数编码
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, reqCode);

		String send_data = "";
		// 请求参数
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				postMethod.addParameter(entry.getKey(), entry.getValue());
				if (!"".equals(send_data)) {
					send_data += "&";
				}
				send_data += entry.getKey() + "=" + entry.getValue();
			}
		}
		String msg = "";
		try {
			httpClient.executeMethod(postMethod);
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream, respCode));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			msg = stringBuffer.toString();
		} catch (IOException e) {
			 System.out.print("http post请求出现io异常");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.print("http post请求出现io异常");
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return msg;
	}
	
	//
	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("userName", "15009911796");
    	map.put("password", "123456");
//    	map.put("userName", "15868978880");
//    	map.put("password", "15868978880");
//		map.put("userName", "15700170956");
//    	map.put("password", "123456");
		map.put("shopId","90");
    	map.put("typeId", "1");
    	map.put("pageSize", "5");
    	map.put("pageNumber", "2");
		map.put("sid","2ce585c5-f45f-44b5-ba8e-b0b0692ce490");
		map.put("cityName", "杭州市");
		map.put("knowledgeId", "13");
		map.put("status", "1");
		map.put("uid", "4558");
		
//		map.put("phone", "15009910020");
//		map.put("password", "123456");type
//		map.put("smsCode", "1234");
//		map.put("communityID", "68");
//		map.put("addressDetails", "8988");
		//getTopicList
		//getTopicList  hasNewTopic hasNewTopic getLittleKnowledge getTopicListByUserId 
		map.put("sortId", "2");
		System.out.println(doPostRequest(server+"api/1/login", map, "utf-8", "utf-8"));
//		map.put("id", "1");
//		map.put("name", "张三");
//		map.put("age", "18");
//		System.out.println(doPostRequest(test+"/users/", map, "utf-8", "utf-8"));
	}
	

	public static Map<String, String> login(){
	    	Map<String, String> map=new HashMap<String, String>();
//	    	map.put("userName", "222222222222222");
//	    	map.put("password", "123456");
//	    	map.put("userName", "123456789987654");
//	    	map.put("password", "123456");
//	    	map.put("userName", "admin");
//	    	map.put("password", "111111");
	    	map.put("userName", "999999999999999");
	    	map.put("password", "111111");
	    	return map;
	    }
	
	public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
	       ObjectMapper mapper = new ObjectMapper();
//	       mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

	       JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

	       return mapper.readValue(jsonStr, javaType);

	}
}
