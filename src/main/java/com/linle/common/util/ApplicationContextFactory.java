package com.linle.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ApplicationContextFactory {
	private static ApplicationContextFactory instance;

	private ApplicationContext context = null;

	public static ApplicationContextFactory getInstance() {
		if (instance == null) {
			instance = new ApplicationContextFactory();
		}
		return instance;
	}

	public ApplicationContext getApplicationContext() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					new String[] { "applicationContext.xml" });
		}
		return context;
	}

	public Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}
}
