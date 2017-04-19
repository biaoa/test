package com.linle.event;

import org.springframework.context.ApplicationEvent;

import com.linle.entity.sys.Users;

public class CreateOrderFeeEvent extends ApplicationEvent {

	private Users user;

	public CreateOrderFeeEvent(Users user) {
		super(user);
		this.user = user;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	

}
