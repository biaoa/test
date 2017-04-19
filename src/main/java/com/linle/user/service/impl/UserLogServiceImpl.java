package com.linle.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.Page;
import com.linle.entity.sys.UserLog;
import com.linle.user.mapper.UserLogMapper;
import com.linle.user.service.UserLogService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
@Service("userLogService")
@Transactional
public class UserLogServiceImpl  implements UserLogService{
	@Autowired
	private UserLogMapper mapper;
	
	public UserLogMapper getMapper() {
		return mapper;
	}

	@Override
	public List<UserLog> findAllUserLogs(Page<UserLog> page) {
		return mapper.getAllData(page);
	}

	@Override
	public Boolean addUserLog(UserLog log) {
		return mapper.insertSelective(log)>0;
	}

}
