package com.linle.communityEmployee.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.community.model.Community;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;

@Controller
@RequestMapping("/communityEmployee")
public class CommunityEmployeeController extends BaseController {
	
	@Autowired
	private CommunityEmployeeService communityEmployeeService;
	
	@Autowired
	private CommunityDepartmentService communityDepartmentService;

	@RequiresPermissions("community_person_manage")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model) {
		Page<CommunityEmployee> page = new Page<CommunityEmployee>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		params.put("communityId", getCommunity().getId());
		page.setParams(params);
		try {
			communityEmployeeService.getAllDate(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("departmentList",communityDepartmentService.getAllDepartment(getCommunity().getId()));
		model.addAttribute("pagelist", page);
		return "/communityInfo/community_employee_list";
	}

	@RequiresPermissions(value = "community_person_manage")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(CommunityEmployee communityEmployee) {
		BaseResponse res=new BaseResponse();
		try {
			if (communityEmployee.getId() != null) {
				res=communityEmployeeService.update(communityEmployee);
			} else {
				Users userInfo = userInfoService.findUserInfoByUserName(communityEmployee.getName());
				if(userInfo==null){
					communityEmployee.setCommunityId(getCommunity().getId());
					communityEmployee.setCreateDate(new Date());
					communityEmployeeService.addCommunityEmployee(communityEmployee,getCommunity());
					return BaseResponse.OperateSuccess;
				}else{
					return new BaseResponse(1, "该用户名已注册！");
				}
			
			}
			return res;
		} catch (Exception e) {
			_logger.info("添加出错:" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}
	
	@RequiresPermissions(value="community_person_manage")
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(long id){
		try {
			boolean flag=communityEmployeeService.updateStatusById(id);
			return flag?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	@RequiresPermissions(value="community_person_resetPassword")
	@RequestMapping(value = "/resetPassword",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg resetPassword(String uid) {
		if(uid!=null&&!"".equals(uid)&&userInfoService.resetPassword(Long.valueOf(uid))>0){
			return new ResponseMsg(0, "密码重置成功", null);
		}
		return new ResponseMsg(1, "密码重置失败", null);
	}
	
	//员工定位监控
	@RequiresPermissions(value="community_person_positioning")
	@RequestMapping(value = "/toBaiduHtml", method = RequestMethod.GET)
	public String toBaiduHtml( Model model) {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			Community community= getCommunity();
			model.addAttribute("community",community);
			model.addAttribute("departmentList",communityDepartmentService.getAllDepartment(community.getId()));
		}
		return "/baiduYinyyan/baidu_yingyan";
	}
			
}
