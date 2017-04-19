package com.linle.user.service;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.sys.UserLog;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public interface UserLogService{
	
	public Boolean addUserLog(UserLog log);
	/**
	 * 分页查询系统用户日志
	 * @param page
	 * @return
	 */
	public List<UserLog> findAllUserLogs(Page<UserLog> page);
}
