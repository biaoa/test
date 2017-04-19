package com.linle.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class TimeCostInterceptor extends HandlerInterceptorAdapter {

	public final static Logger logger = LoggerFactory
			.getLogger(TimeCostInterceptor.class);
	private ThreadLocal<Long> timeThreadLocal = new ThreadLocal<Long>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		timeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		return true;// 继续流程
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = timeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		if (endTime - beginTime > 500) {// 此处认为处理时间超过500毫秒的请求为慢请求
			logger.error(request.getRequestURI() + " 比较耗时："
					+ (endTime - beginTime));
		}
	}
}
