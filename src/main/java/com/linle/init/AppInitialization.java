package com.linle.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppInitialization {
	
	private final static Logger logger=LoggerFactory.getLogger(AppInitialization.class);

	public static void main(String[] args) throws Exception {
		logger.info("AppInitialization:initApp");
		new EnvironmentBuilder().builder();
		logger.info("AppInitialization:initApp complete");
		System.exit(0);
	}

}
