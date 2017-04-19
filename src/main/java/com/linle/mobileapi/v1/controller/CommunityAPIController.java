package com.linle.mobileapi.v1.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.sys.PropertyCompany;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.CommunityDepartmentRequest;
import com.linle.mobileapi.v1.request.CommunityEmployeeRequest;
import com.linle.mobileapi.v1.response.CommunityDepartmentResponse;
import com.linle.mobileapi.v1.response.CommunityEmployeeResponse;

@Controller
@RequestMapping("/api/1")
public class CommunityAPIController extends BaseController {
	
	@Autowired
	private CommunityDepartmentService departmentService;
	
	@Autowired
	private CommunityEmployeeService employeeService;
	
	@RequestMapping("/getCommunityDepartment")
	@ResponseBody
	public BaseResponse getCommunityDepartment(CommunityDepartmentRequest req,Errors errors,
            HttpServletRequest servletRequest,HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommunityDepartmentResponse res = new CommunityDepartmentResponse();
				Map<String, Object> params = new HashMap<>();
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				PropertyCompany propertyCompany = getUserCommunity().getPropertyCompany();
				params.put("communityId", user.getCommunity().getId());
				res.setCode(0);
				res.setMsg("获取成功");
				res.setPropertyCompanyName(propertyCompany.getName());
				res.setPropertyCompanyLogo(propertyCompany.getLogo());
				res.setSynopsis(propertyCompany.getSynopsis());
				res.setOwnerNotice(user.getCommunity().getOwnerNotice());
				res.setDepartmentList(departmentService.getDepartmentListForAPI(params));
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
			
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("获得小区部门出现错误");
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping("/getCommunityEmployee")
	@ResponseBody
	public BaseResponse getCommunityEmployee(CommunityEmployeeRequest req,Errors errors,
            HttpServletRequest servletRequest,HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommunityEmployeeResponse res = new CommunityEmployeeResponse();
				Map<String, Object> params = new HashMap<>();
				params.put("departmentId", req.getDepartmentId());
				params.put("level", 11);
				res.setCode(0);
				res.setMsg("获取成功");
				res.setLeaderList(employeeService.selectEmployeeList(params));
				params.put("level", 10);
				res.setEmployeeList(employeeService.selectEmployeeList(params));
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("获得小区部门员工出现错误");
			return BaseResponse.ServerException;
		}
	}
}
