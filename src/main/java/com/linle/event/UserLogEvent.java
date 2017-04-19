package com.linle.event;

import org.springframework.context.ApplicationEvent;

import com.linle.entity.enumType.UserAction;
import com.linle.entity.sys.Users;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public class UserLogEvent  extends ApplicationEvent{
	
	/**操作类型**/
	private UserAction action;
	/**操作人**/
	private Users user;
	public UserLogEvent(String ip,UserAction action,Users user) {
		super(ip);
		this.action = action;
		this.user = user;
	}
	public UserAction getAction() {
		return action;
	}
	public void setAction(UserAction action) {
		this.action = action;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}

}
