package com.linle.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class SimpleMappingExceptionResolver extends org.springframework.web.servlet.handler.SimpleMappingExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 *  打印简单错误日志
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		logger.debug(getExceptionContent(exception));
		return super.doResolveException(request, response, handler, exception);
	}
	
	private String getExceptionContent(Exception exception) {
		StringBuilder builder = new StringBuilder(exception.toString());
		StackTraceElement[] elements = exception.getStackTrace();
		builder.append("\n##########错误开始##########");
		for (int i = 0; i < 4; i++) {
			StackTraceElement element = elements[i];
			builder.append("\n#####类:" + element.getClassName() + "#####方法:" + element.getMethodName() + "#####行:" +element.getLineNumber());
		}
		builder.append("\n##########错误结束##########");
		return builder.toString();
	}
}
