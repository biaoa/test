package com.linle.init;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InitializationsRunner {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private List<Initialization> inits=new ArrayList<Initialization>();

	protected void executeInitialization() throws Exception{
		logger.info("InitializationsRunner start");
		for (Initialization init:inits){
			init.init();
		}	
		logger.info("InitializationsRunner end");
	}
	
	protected void addInitialization(Initialization init){
		logger.info("addInitialization Initialization object");
		if (!inits.contains(init)){
			inits.add(init);
		}
	}

}
