//package com.linle.user.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.linle.common.util.Page;
//import com.linle.common.util.ResponseMsg;
//import com.linle.entity.ChildAccount;
//import com.linle.entity.enumType.UserStatusType;
//import com.linle.user.base.BaseUserController;
//import com.linle.user.model.ChildAccountInfo;
//import com.linle.user.service.ChildAccountService;
//import com.linle.user.service.UserRoleService;
//
///**
// * @描述:子帐号控制类
// * @作者:杨立忠
// * @创建时间：2015年8月25日
// **/
//
//@RequestMapping("/sys")
//@Controller
//public class ChildAccountController extends BaseUserController{
//	
//
//	@Autowired
//	private UserRoleService userRoleService;
//	@Autowired
//	private ChildAccountService childAccountService;
//	/**
//	 * 子帐号列表(系统管理员)
//	 * @param model
//	 * @return
//	 */
//	@RequiresPermissions("system_childAccount_Set")
//	@RequestMapping(value = "/childAccountlist", method = RequestMethod.GET)
//	public ModelAndView childAccountlist(Integer pageNo, Model model) {
//		ModelAndView mv = new ModelAndView();
//		Page<ChildAccount> page = new Page<ChildAccount>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		if(pageNo!=null){
//			page.setPageNo(pageNo);
//		}
//		params.put("status", UserStatusType.normal);
//		params.put("roleType", getCurrentUser().getIdentity());
//		params.put("objId", 0);
//		page.setParams(params);
//		page.setResults(childAccountService.findAllAccount(page));
//		model.addAttribute("pagelist", page);
//		mv.setViewName("system/childAccount_list");
//		return mv;
//	}
//	/**
//	 * 新增子帐号页面
//	 * 
//	 * @param 
//	 * @param model
//	 * @return
//	 */
//	@RequiresPermissions("system_childAccount_Set")
//	@RequestMapping(value = "/createChildAccountPage", method = RequestMethod.GET)
//	public ModelAndView createChildAccountPage(Model model) {
//		ModelAndView mv = new ModelAndView();
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("available", true);
//		params.put("suorceType", getCurrentUser().getIdentity());
//		params.put("sourceId", 0);
//		model.addAttribute("rolelist", userRoleService.findAllRoleList(params));
//		mv.setViewName("system/add_childAccount");
//		return mv;
//		
//	}
//	/**
//	 * 新增子帐号
//	 * 
//	 * @param 
//	 * @param model
//	 * @return
//	 */
//	@RequiresPermissions("system_childAccount_Set")
//	@RequestMapping(value = "/createChildAccount", method = RequestMethod.POST)
//	public @ResponseBody
//	ResponseMsg createChildAccount(@Valid ChildAccountInfo info, Model model) {
//		ResponseMsg errorMsg = new ResponseMsg(0, null, null);
//		if (childAccountService.addChildAccount(info,getCurrentUser(),null)) {
//			return new ResponseMsg(0, "子账号新增成功", null);
//		} 
//		return new ResponseMsg(1, "子账号新增失败", null);
//	}
//}
