package com.linle.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.system.service.RegionService;
import com.linle.user.base.BaseUserController;


@RequestMapping("/sys")
@Controller
public class AccountController extends BaseUserController {
	
	@Autowired
	private RegionService regionService;
	
	
	
	/**
	 * 用户列表(系统管理员首页)
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system_accountManage")
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public ModelAndView userList(Integer pageNo, Model model,String userName,Integer userType) {
		ModelAndView mv = new ModelAndView();
		Page<Users> page = new Page<Users>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		try {
			if(userName!=null && !"".equals(userName)){
				userName=new String(userName.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
//		params.put("status", UserStatusType.normal);
//		params.put("identity", UserType.JM); //非居民
		params.put("userType", userType);
		params.put("userName", userName);
		page.setParams(params);
		page.setResults(userInfoService.findAllUsers(page));
		model.addAttribute("pagelist", page);
		model.addAttribute("userName", userName);
		model.addAttribute("userType", userType);
		mv.setViewName("system/user_list");
		return mv;
	}
	
	
	
	
    
	@RequiresPermissions("system_accountManage")
	@RequestMapping(value = "/resetPassword/{uid}", method = RequestMethod.POST)
	public @ResponseBody
	ResponseMsg resetPassword(@PathVariable Long uid) {
		if(uid!=null&&userInfoService.resetPassword(uid)>0){
			return new ResponseMsg(0, "密码重置成功", null);
		}
		return new ResponseMsg(1, "密码重置失败", null);
	}
	
	
	/**
	 * 修改密码页
	 * 
	 * @return
	 */
	@RequiresPermissions("modify_password")
	@RequestMapping(value = "/updatePasswdPage", method = RequestMethod.GET)
	public String updatePasswdPage() {
		return "system/update_passwd";
	}
	
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequiresPermissions("modify_password")
	@RequestMapping(value = "/savePasswd", method = RequestMethod.POST)
	public @ResponseBody ResponseMsg savePasswd(String oldpassword,String password) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users user = (Users) subject.getSession().getAttribute("cUser");
			if(StringUtil.isNull(oldpassword) || StringUtil.isNull(password)){
				return new ResponseMsg(1, "参数错误", null);
			}
			if(!userInfoService.checkPassword(oldpassword, user.getId())){
				return new ResponseMsg(1, "原密码不正确", null);
			}
			user.setPassword(password);
			if(!userInfoService.updatePassword(user)){
				return new ResponseMsg(1, "密码修改失败", null);
			}
		}
		return new ResponseMsg(0, "修改成功", null);
	}
	
	@RequestMapping(value="/checkUserExsit",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse checkUserName(String userName){
		boolean isExsit = userInfoService.checkUserExsit(userName);
		return isExsit?BaseResponse.UserExist:BaseResponse.UserNotExist;
	}
	
	
//	@RequiresPermissions("system_accountManage")
//	@RequestMapping(value = "/userStatus/{uid}/{status}", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseMsg userStatus(@PathVariable Long uid,@PathVariable int status) {
//		if(uid!=null&&!"".equals(uid)){
//			Users user = new Users();
//			user.setId(uid);
//			user.setStatus(UserStatusType.values()[status]);
//			userInfoService.modifyUserStatus(user);
//			return new ResponseMsg(0, "操作成功", null);
//		}
//		return new ResponseMsg(1, "操作失败", null);
//	}
	
	//禁用用户
	@RequiresPermissions("system_accountManage")
	@RequestMapping(value = "/userStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg userStatus( Long uid, int status,String reason) {
		if(uid!=null){
			Users user = new Users();
			user.setId(uid);
			user.setStatus(UserStatusType.values()[status]);
			user.setReason(reason);
			userInfoService.modifyUserStatus(user);
			return new ResponseMsg(0, "操作成功", null);
		}
		return new ResponseMsg(1, "操作失败", null);
	}
}
