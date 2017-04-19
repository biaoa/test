package com.linle.user.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.linle.common.base.BaseController;
import com.linle.entity.sys.Users;
import com.linle.user.service.UserInfoService;



public abstract class BaseUserController extends BaseController {
	
	@Autowired
	protected UserInfoService userInfoService;
	
	@ModelAttribute
	public void getusername(Model model){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users user = (Users) subject.getSession().getAttribute("cUser");
			if(user!=null){
				model.addAttribute("name", user.getUserName());
				model.addAttribute("time", user.getCreateDate());
			}
		}
	}
	
	/**
	 * 获取当前登陆的用户
	 * @return
	 */
	public Users getCurrentUser(){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users userInfo = (Users) subject.getSession().getAttribute("cUser");
			return userInfo;
		}
		return null;
	}
	
	
}
