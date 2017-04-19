package com.linle.init;

import com.linle.init.impl.DBInitialization;

public class EnvironmentBuilder extends InitializationsRunner {

	public EnvironmentBuilder() {
		addInitialization(new DBInitialization());
//		addInitialization(new UserAndRoleInitialization());
	}

	public void builder() throws Exception {
		logger.info("系统初始化开始!");
		executeInitialization();
		logger.info("系统初始化结束!");
	}
}
