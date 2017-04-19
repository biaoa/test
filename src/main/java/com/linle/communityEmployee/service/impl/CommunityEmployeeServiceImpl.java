package com.linle.communityEmployee.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.SaltUtil;
import com.linle.community.model.Community;
import com.linle.communityEmployee.mapper.CommunityEmployeeMapper;
import com.linle.communityEmployee.model.CommunityEmployeeListVo;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.enumType.StatusType;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.EmployeeVO;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CommunityEmployeeVO;
import com.linle.user.mapper.UserMapper;
import com.linle.user.service.UserInfoService;
import com.linle.user.service.UserRoleService;

@Service
@Transactional
public class CommunityEmployeeServiceImpl extends CommonServiceAdpter<CommunityEmployee>
		implements CommunityEmployeeService {
	
	@Autowired
	private CommunityEmployeeMapper mapper;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleService roleService;
	
	@Autowired
	private RongService rongService;
	
	@Autowired
	private UserInfoService userInfoService; 
	
	@Override
	public Page<CommunityEmployee> getAllDate(Page<CommunityEmployee> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<CommunityEmployeeVO> selectEmployeeList(Map<String, Object> params) {
		return mapper.selectEmployeeList(params);
	}
	
	public BaseResponse update(CommunityEmployee communityEmployee) {
		try {
			CommunityEmployee oldEmp=mapper.selectByPrimaryKey(communityEmployee.getId());//旧名
			if(!oldEmp.getName().equals(communityEmployee.getName())){//旧名和新名不一样，判断新名用户表是否存在
				Users userInfo = userInfoService.findUserInfoByUserName(communityEmployee.getName());
				if(userInfo!=null){
					return new BaseResponse(1, "该用户名已注册！");
				}
			}
			mapper.updateByPrimaryKeySelective(communityEmployee);
			//修改用户
			communityEmployee.setUser(oldEmp.getUser());
			userInfoService.updateUserFromCommunityEmployee(communityEmployee);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
		
		return BaseResponse.OperateSuccess;
	}
	 
	@Override
	public boolean addCommunityEmployee(CommunityEmployee communityEmployee,Community community) {
		// 添加用户信息
		String salt = SaltUtil.getRandomString(16);
		// FIXME 这里的用户密码 暂时写成123456 到时候要修改
		String password = new Sha1Hash("123456", salt).toString();
		
		Users user = new Users();
		user.setUserName(communityEmployee.getName());
		user.setName(communityEmployee.getName());
		user.setPassword(password);
		user.setSalt(salt);
		user.setStatus(UserStatusType.normal);
		user.setCommunity(community);
		if("11".equals(communityEmployee.getLevel())){
			user.setIdentity(UserType.XQBMFZR);//小区部门负责人
		}else{
			user.setIdentity(UserType.XQPTYG);//小区普通员工
		}
		
		int userID = userMapper.insertSelective(user);
		try {
			if (userID > 0) {
				logger.info("新增用户：" + user.getUserName() + "成功");
				// 获得token并将token赋值user表
				rongService.getUserRongToken(user);
				
				// 新增用户角色关联
				UserRoleRelation userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUser(user);
				userRoleRelation.setUserRole(roleService.getRoleByename("CommunityEmployee"));
				if (roleService.addUserRoleRelation(userRoleRelation) > 0) {
					logger.info("新增用户：" + user.getUserName() + "，角色成功");
					// 新增小区员工
					communityEmployee.setUser(user);
					communityEmployee.setStatus(StatusType.normal);
					return mapper.insertSelective(communityEmployee) > 0;
				}
			}
		} catch (Exception e) {
			logger.error("新增小区人员出错了");
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("新增小区人员出错了");//有异常全部回滚
		}
		return false;
	}
	
	//删除
	public boolean updateStatusById(long id){
		try {
			int count=mapper.updateStatusById(id);//禁用小区员工状态
			if(count>0){
				CommunityEmployee obj=mapper.selectByPrimaryKey(id);
				Users user=obj.getUser();
				user.setStatus(UserStatusType.deleted);
				userInfoService.modifyUserStatus(user);//修改用户状态
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("删除小区员工出错");
		}
		return false;
	}
	
	public CommunityEmployee selectByUid(long uid){
		return mapper.selectByUid(uid);
	}
	public List<CommunityEmployeeListVo> getCommunityEmployeeListPrvlg(Map<String, Object> map){
		return mapper.getCommunityEmployeeListPrvlg(map);
	}
	

	@Override
	public List<EmployeeVO> getAllEmployee(Map<String, Object> map) {
		return mapper.getAllEmployee(map);
	}

	@Override
	public List<EmployeeVO> getEmployeeByIds(String ids) {
		return mapper.getEmployeeByIds(ids.split(","));
	}

	@Override
	public List<String> getEmployeeByCommunityId(Long communityId) {
		return mapper.getEmployeeByCommunityId(communityId);
	}

}
