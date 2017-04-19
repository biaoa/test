package com.linle.communityEmployee.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.communityEmployee.model.CommunityEmployeeListVo;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.vo.EmployeeVO;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CommunityEmployeeVO;

public interface CommunityEmployeeService extends BaseService<CommunityEmployee> {

	Page<CommunityEmployee> getAllDate(Page<CommunityEmployee> page);

	List<CommunityEmployeeVO> selectEmployeeList(Map<String, Object> params);

	public boolean addCommunityEmployee(CommunityEmployee communityEmployee, Community community);

	public boolean updateStatusById(long id);
	
	public CommunityEmployee selectByUid(long uid);
	
	public BaseResponse update(CommunityEmployee communityEmployee);
	
	public List<CommunityEmployeeListVo> getCommunityEmployeeListPrvlg(Map<String, Object> map);

	/**
	 * 
	 * @Description
	 * @param map
	 * communityId 小区ID必传 departmentId 部门ID选传 inTemplet 是否在规则表中选传
	 * @return List<EmployeeVO> $
	 */
	List<EmployeeVO> getAllEmployee(Map<String, Object> map);
	
	/**
	 * 
	 * @Description
	 * @param ids
	 * @return List<EmployeeVO>
	 * $
	 */
	List<EmployeeVO> getEmployeeByIds(String ids);
	
	/**
	 * 
	 * @Description 根据某个小区ID 获得小区下的员工列表
	 * @param communityId
	 * @return List<String>
	 * $
	 */
	List<String> getEmployeeByCommunityId(Long communityId);

}
