package com.linle.home.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.Users;
import com.linle.user.service.RolePermissionService;
import com.linle.user.service.UserRoleService;

import net.sf.json.JSONObject;

/**
 * 首页控制类
 * 
 * @author linle
 * 
 */
@Controller
public class HomePageController extends BaseController {

	@Autowired
	private UserRoleService roleService;
	@Autowired
	private RolePermissionService rolePermissionService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return "redirect:/sys/index";
		} else {
			return "redirect:/sys/login";
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Subject subject = SecurityUtils.getSubject();
		ModelAndView mv = new ModelAndView();
		if (subject.isAuthenticated()) {
			Users user = (Users) subject.getSession().getAttribute(
					"cUser");
			mv.addObject("name", user.getUserName());
			mv.addObject("time", user.getCreateDate());
			mv.addObject("uType",user.getIdentity());
			mv.setViewName("index");
//			model.addAttribute("name", user.getMobilePhone());
		}else {
			mv.setViewName("redirect:/login");
		}
		return mv;
	}

	@RequestMapping(value = "/res/regist", method = RequestMethod.GET)
	String regist(HttpServletRequest request,
			HttpServletResponse response) {
		return "/regist";
	}
	
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String Error404() {
		return "error/404";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String Error403(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String requestType = request.getHeader("X-Requested-With");  
		if (StringUtil.isNotNull(requestType) && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(JSONObject.fromObject(new ResponseMsg(1, "没有权限操作", null)).toString()); 
			return null;
		}
		return "error/403";
	}
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String Error500() {
		return "error/500";
	}
	
	@RequestMapping(value = "/res/forgotPassword", method = RequestMethod.GET)
	String forgotPassword(HttpServletRequest request,
			HttpServletResponse response) {
		return "/forgotPassword";
	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
			mv.setViewName("error/tologin");
		return mv;
	}
	
	@RequestMapping(value="/investors")
	public String investors(HttpServletRequest request,
			HttpServletResponse response, Model model){
		return "/investors/community_show";
	}
}
