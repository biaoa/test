package com.linle.versionManager.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.mobileapi.v1.response.VersionResponse;
import com.linle.versionManager.model.VersionManager;

public interface VersionManagerService  extends BaseService<VersionManager>  {
	
	public Page<VersionManager> getAllForPage(Page<VersionManager> page) ;
	
	public VersionManager selectBySoftwareFlag(String softwareFlag,String deviceType);
	
	public VersionResponse checkVersion(String softwareFlag,String deviceType,String currVersion);
	
}
