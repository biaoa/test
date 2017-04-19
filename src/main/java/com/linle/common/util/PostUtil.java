package com.linle.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class PostUtil {
	public static final Logger _logger = LoggerFactory.getLogger(PostUtil.class);
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
			// log.error("http post请求出现io异常", e.getMessage());
			e.printStackTrace(); _logger.error("出错了", e);
		} catch (Exception e) {
			// log.error("http post请求出现异常", e.getMessage());
			e.printStackTrace(); _logger.error("出错了", e);
		} finally {
			postMethod.releaseConnection();
		}
		return msg;
	}
}
