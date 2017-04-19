package com.linle.communityDepartment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.communityDepartment.mapper.CommunityDepartmentMapper;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.entity.sys.CommunityDepartment;
import com.linle.mobileapi.v1.model.DepartmentVO;

@Service
@Transactional
public class CommunityDepartmentServiceImpl extends CommonServiceAdpter<CommunityDepartment>
		implements CommunityDepartmentService {
	
	@Autowired
	private CommunityDepartmentMapper mapper;

	@Override
	public Page<CommunityDepartment> getAllDate(Page<CommunityDepartment> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<CommunityDepartment> getAllDepartment(Long communityId) {
		return mapper.getAllDepartment(communityId);
	}

	@Override
	public List<DepartmentVO> getDepartmentListForAPI(Map<String, Object> params) {
		return mapper.getDepartmentListForAPI(params);
	}

}
