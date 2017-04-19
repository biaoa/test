package com.linle.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.base.BaseLoginToken;
import com.linle.common.base.CommonResponseMsg;

@Controller
@RequestMapping("sys")
public class SysLoginController  extends BaseController{
	
	@RequiresAuthentication
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/manage");
		return mv;
	}
	
	
    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String index1(Model model) {
    	Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			if(subject.isPermitted("SYS:*:*")){
				return "redirect:/sys/index";
			}else{
				subject.logout();
			}
		} else {
			subject.logout();
			return "redirect:/sys/login";
		}
		return "redirect:/sys/login";
    }
    
    @RequestMapping(value = {"login"}, method = RequestMethod.GET)
    public String login() {
        return "/system/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponseMsg login(BaseLoginToken token) {
    	userInfoService.login(token);
    	SecurityUtils.getSubject().getSession().getAttribute("user");
        return CommonResponseMsg.LoginSucInstance;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
    	userInfoService.logout();
        return "redirect:/sys/login";
    }
    
}
