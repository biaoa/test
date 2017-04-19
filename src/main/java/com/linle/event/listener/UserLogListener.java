package com.linle.event.listener;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.entity.sys.UserLog;
import com.linle.entity.sys.Users;
import com.linle.event.UserLogEvent;
import com.linle.user.service.UserInfoService;
import com.linle.user.service.UserLogService;

/**
 * @描述:
 * @作者:杨立忠 @创建时间：2015年8月13日
 **/
@Component
public class UserLogListener implements ApplicationListener<UserLogEvent> {
	protected final Logger _logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserLogService userLogService;

	@Autowired
	private UserInfoService userInfoService;

	@Async
	@Override
	public void onApplicationEvent(UserLogEvent event) {
		try {
			Users user = event.getUser();
			UserLog log = new UserLog();
			log.setAction(event.getAction());
			log.setUser(user);
			log.setLoginIp((String) event.getSource());
			// 插入登陆日志
			userLogService.addUserLog(log);
			// 修改最近登陆时间
			user.setLastLoginDate(new Date());
			userInfoService.setLastLoginTime(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			_logger.error("新增用户日志错误！");
		}

	}

}
