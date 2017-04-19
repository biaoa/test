package com.linle.propertyCompany.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.SaltUtil;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.PropertyCompany;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.propertyCompany.mapper.PropertyCompanyMapper;
import com.linle.propertyCompany.service.PropertyCompanyService;
import com.linle.user.mapper.UserMapper;
import com.linle.user.service.UserRoleService;

@Service
@Transactional
public class PropertyCompanyServiceImpl extends CommonServiceAdpter<PropertyCompany> implements PropertyCompanyService {

	@Autowired
	private PropertyCompanyMapper mapper;
	
	@Autowired
	private UserRoleService roleService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Page<PropertyCompany> findAllPropertyCompanys(Page<PropertyCompany> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public boolean addPropertyCompany(PropertyCompany company) {
		// 新增用户
		String salt = SaltUtil.getRandomString(16);
		// FIXME 这里的用户密码 暂时写成123456 到时候要修改
		String password = new Sha1Hash("123456", salt).toString();
		Users user = new Users();

		user.setUserName(company.getName());
		user.setName(company.getName());
		user.setPassword(password);
		user.setSalt(salt);
		user.setStatus(UserStatusType.normal);
		user.setIdentity(UserType.WY);
		// 添加物业公司
		if (userMapper.insertSelective(user) > 0) {
			logger.info("新增用户：" + user.getUserName() + "成功");
			// 新增角色
			UserRoleRelation userRoleRelation = new UserRoleRelation();
			userRoleRelation.setUser(user);
			userRoleRelation.setUserRole(roleService.getRoleByename("property:company"));
			if (roleService.addUserRoleRelation(userRoleRelation) > 0) {
				logger.info("新增用户：" + user.getUserName()+ "，角色成功");
				// 新增物业
				company.setUser(user);
				company.setState((byte) 0);
				return mapper.insertSelective(company) > 0;
			}
		}
		return false;
	}

	@Override
	public PropertyCompany getPopertyCompanyByuserID(Long id) {
		return mapper.getPopertyCompanyByuserID(id);
	}

	@Override
	public List<PropertyCompany> getAllPropertyCompany() {
		return mapper.getAllPropertyCompany();
	}

	@Override
	public int getPropertyCompanyCountByDate(Map<String, Object> map) {
		return mapper.getPropertyCompanyCountByDate(map);
	}
	
	@Override
	public List<UserStatistics> getPropertyListByDate(Map<String, Object> map) {
		return mapper.getPropertyListByDate(map);
	}
	
}
