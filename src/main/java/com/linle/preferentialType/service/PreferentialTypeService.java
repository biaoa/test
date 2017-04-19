package com.linle.preferentialType.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.PreferentialType;
import com.linle.mobileapi.v1.model.Privilege;

public interface PreferentialTypeService extends BaseService<PreferentialType> {

	Page<PreferentialType> findAllPreferentialType(Page<PreferentialType> page);
	
	 List<PreferentialType> getAllType();

	List<Privilege> getAllTypeForAPI();

}
