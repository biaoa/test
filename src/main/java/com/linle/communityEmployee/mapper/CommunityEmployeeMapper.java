package com.linle.communityEmployee.mapper;

import java.util.List;
import java.util.Map;

import com.linle.communityEmployee.model.CommunityEmployeeListVo;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.vo.EmployeeVO;
import com.linle.mobileapi.v1.model.CommunityEmployeeVO;

import component.BaseMapper;

public interface CommunityEmployeeMapper extends BaseMapper<CommunityEmployee> {

	List<CommunityEmployeeVO> selectEmployeeList(Map<String, Object> params);

	int updateStatusById(long id);

	List<EmployeeVO> getAllEmployee(Map<String, Object> map);

	List<EmployeeVO> getEmployeeByIds(String ids[]);
	
	CommunityEmployee selectByUid(long uid);

	List<CommunityEmployeeListVo> getCommunityEmployeeListPrvlg(Map<String, Object> map);
	
	List<String> getEmployeeByCommunityId(Long communityId);
 
}