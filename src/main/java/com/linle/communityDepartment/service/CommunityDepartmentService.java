package com.linle.communityDepartment.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommunityDepartment;
import com.linle.mobileapi.v1.model.DepartmentVO;

public interface CommunityDepartmentService extends BaseService<CommunityDepartment> {

	Page<CommunityDepartment> getAllDate(Page<CommunityDepartment> page);
	
	/**
	 * 
	 * @Description 通过小区Id
	 * @param communityId
	 * @return List<CommunityDepartment>
	 */
	List<CommunityDepartment> getAllDepartment(Long communityId);
	/**
	 * 
	 * @Description  根据小区ID 获得小区部门列表 forAPI
	 * @param params
	 * @return List<DepartmentVO>
	 * $
	 */
	List<DepartmentVO> getDepartmentListForAPI(Map<String, Object> params);

}
