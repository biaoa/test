package com.linle.regionalAgency.service.impl;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.SaltUtil;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.RegionalAgency;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.regionalAgency.mapper.RegionalAgencyMapper;
import com.linle.regionalAgency.service.RegionalAgencyService;
import com.linle.user.mapper.UserMapper;
import com.linle.user.service.UserRoleService;

@Service("RegionalAgencyService")
@Transactional
public class RegionalAgencyServiceImpl extends CommonServiceAdpter<RegionalAgency> implements RegionalAgencyService {

	@Autowired
	private RegionalAgencyMapper regionalAgencyMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleService roleService;

	@Override
	public Page<RegionalAgency> findAllPropertyCompanys(Page<RegionalAgency> page) {
		page.setResults(regionalAgencyMapper.getAllData(page));
		return page;
	}

	/**
	 * 新增区域代理
	 */
	@Override
	public boolean addRegionalAgency(RegionalAgency agency) {
		// 新增用户
		String salt = SaltUtil.getRandomString(16);
		// FIXME 这里的用户密码 暂时写成123456 到时候要修改
		String password = new Sha1Hash("123456", salt).toString();
		Users user = new Users();

		user.setUserName(agency.getName());
		user.setName(agency.getName());
		user.setPassword(password);
		user.setSalt(salt);
		user.setStatus(UserStatusType.normal);
		if("1".equals(agency.getType())){
			user.setIdentity(UserType.YJDL);
		}else if("2".equals(agency.getType())){
			user.setIdentity(UserType.EJDL);
		}
		

		if (userMapper.insertSelective(user) > 0) {
			logger.info("新增用户：" + user.getUserName() + "成功");
			// 新增角色
			UserRoleRelation userRoleRelation = new UserRoleRelation();
			userRoleRelation.setUser(user);
			userRoleRelation.setUserRole(roleService.getRoleByename("regional:agency"));
			if (roleService.addUserRoleRelation(userRoleRelation) > 0) {
				logger.info("新增用户：" + user.getUserName() + "，角色成功");
				// 新增代理商
				agency.setUser(user);
				agency.setState((byte) 0);
				return regionalAgencyMapper.insertSelective(agency) > 0;
			}
		}
		return false;
	}

	@Override
	public RegionalAgency getRegionalAgencyByuserID(Long userid) {
		return regionalAgencyMapper.selectByUserid(userid);
	}

}
