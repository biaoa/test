package com.linle.communityPresident.service.impl;

import java.util.Date;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.SaltUtil;
import com.linle.communityPresident.mapper.CommunityPresidentMapper;
import com.linle.communityPresident.service.CommunityPresidentService;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.RegionalAgency;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.regionalAgency.service.RegionalAgencyService;
import com.linle.user.mapper.UserMapper;
import com.linle.user.service.UserRoleService;

@Service
@Transactional
public class CommunityPresidentServiceImpl extends CommonServiceAdpter<CommunityPresident>
		implements CommunityPresidentService {
	
	@Autowired
	private CommunityPresidentMapper mapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleService roleService;
	
	@Autowired
	private  RegionalAgencyService agencyService;

	@Autowired
	private RongService rongService;
	
	@Override
	public Page<CommunityPresident> findAllCommunityPresident(Page<CommunityPresident> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public BaseResponse addCommunityPresident(CommunityPresident communityPresident) {
		// 新增用户
		String salt = SaltUtil.getRandomString(16);
		String password = new Sha1Hash("123456", salt).toString();
		Users user = new Users();

		user.setUserName(communityPresident.getName());
		user.setName(communityPresident.getName());
		user.setPassword(password);
		user.setSalt(salt);
		user.setStatus(UserStatusType.normal);
		user.setIdentity(UserType.SZ);
		boolean isok =false;
		if (userMapper.insertSelective(user)>0) {
			logger.info("新增用户：" + user.getUserName() + "成功");
			//获得token并将token赋值user表
			rongService.getUserRongToken(user);
			
			// 新增角色
			UserRoleRelation userRoleRelation = new UserRoleRelation();
			userRoleRelation.setUser(user);
			userRoleRelation.setUserRole(roleService.getRoleByename("president"));
			if (roleService.addUserRoleRelation(userRoleRelation) > 0) {
				logger.info("新增用户：" + user.getUserName() + "，角色成功");
				// 新增小区社长
				communityPresident.setUserId(user.getId());
				communityPresident.setCreateDate(new Date());
				communityPresident.setStatus(0);
				isok = mapper.insertSelective(communityPresident)>0;
			}
		}
		return isok?BaseResponse.AddSuccess:BaseResponse.AddFail;
	}

	@Override
	public CommunityPresident selectByUserId(Long uid) {
		return mapper.selectByUserId(uid);
	}

	@Override
	public Users getlevel2Proxy(CommunityPresident president) {
		//不管是 管理员，一级代理，二级代理创建的社长   他的二级都是创建人
		Users user = userMapper.selectByPrimaryKey(president.getSuperiorId());
		return user;
	}

	@Override
	public Users getlevel1Proxy(CommunityPresident president) {
		Users user = userMapper.selectByPrimaryKey(president.getSuperiorId());
		if(user.getIdentity()==UserType.SYS){
			return user;
		}else if(user.getIdentity()==UserType.YJDL){
			return user;
		}else if(user.getIdentity()==UserType.EJDL){
			//如果是二级代理 直接返回二级代理的创建人(创建人要么是系统，要么是一级代理)
			RegionalAgency agency = agencyService.getRegionalAgencyByuserID(user.getId());
			return userMapper.selectByPrimaryKey(agency.getCreateUserId());
		}
		return null;
	}

	@Override
	public CommunityPresident selectByCommunityUserId(Long id) {
		Users user = userMapper.selectByPrimaryKey(id);
		return user.getCommunity().getPresident();
	}

}
