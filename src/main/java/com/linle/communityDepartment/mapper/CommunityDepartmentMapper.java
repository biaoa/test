package com.linle.communityDepartment.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CommunityDepartment;
import com.linle.mobileapi.v1.model.DepartmentVO;

import component.BaseMapper;

public interface CommunityDepartmentMapper extends BaseMapper<CommunityDepartment>{

	List<CommunityDepartment> getAllDepartment(Long communityId);

	List<DepartmentVO> getDepartmentListForAPI(Map<String, Object> params);
    
}