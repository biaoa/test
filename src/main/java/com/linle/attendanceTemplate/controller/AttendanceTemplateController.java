package com.linle.attendanceTemplate.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.attendanceAddress.model.AttendanceAddress;
import com.linle.attendanceAddress.service.AttendanceAddressService;
import com.linle.attendanceSpecialDate.model.AttendanceSpecialDate;
import com.linle.attendanceSpecialDate.service.AttendanceSpecialDateService;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.sys.CommunityDepartment;
import com.linle.entity.vo.EmployeeVO;
import com.linle.mobileapi.base.BaseResponse;

@RequestMapping("/attendanceTemplate")
@Controller
public class AttendanceTemplateController extends BaseController {

	@Autowired
	private AttendanceTemplateService templetService;
	
	@Autowired
	private CommunityEmployeeService employeeService;
	
	@Autowired
	private CommunityDepartmentService departmentService;
	
	@Autowired
	private AttendanceSpecialDateService dateService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private AttendanceAddressService addressService;

	// List方法
	@RequiresPermissions("attendance_manage")
	@RequestMapping("/list")
	public String list(Integer pageNo, Model model) {
		Page<AttendanceTemplate> page = new Page<AttendanceTemplate>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		params.put("communityId", getCommunity().getId());
		page.setParams(params);
		try {
			templetService.getAllData(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/attendanceTemplate/template_list";
	}
	// add
	@RequiresPermissions("attendance_setting")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String toadd(Long id,Model model){
		AttendanceTemplate template = new AttendanceTemplate();
		Long communityId = getCommunity().getId();
		//某个规则下的员工
		List<EmployeeVO> userList = new ArrayList<>();
		//部门集合
		List<CommunityDepartment> departmentList = departmentService.getAllDepartment(communityId);
		//某个部门下的员工集合
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", communityId);
		if(!departmentList.isEmpty()){
			map.put("departmentId", departmentList.get(0).getId());
		}
		List<EmployeeVO> duserList  = employeeService.getAllEmployee(map);
		//特殊日期集合
		List<AttendanceSpecialDate> dateList = new ArrayList<>();
		//考勤地址集合
		List<AttendanceAddress> addressList = new ArrayList<>();
		if(id!=null){
			template = templetService.selectByPrimaryKey(id);
			userList = employeeService.getEmployeeByIds(template.getUids());
			dateList = dateService.selectByTemplate(template.getId());
			addressList = addressService.selectByTemplateId(template.getId());
		}
		model.addAttribute("duserList", duserList);
		model.addAttribute("departmentList",departmentList);
		model.addAttribute("userList", userList);
		model.addAttribute("attendanceTemplate", template);
		model.addAttribute("dateList", dateList);
		model.addAttribute("addressList", addressList);
		return "attendanceTemplate/template_add";
	}
	
	@RequiresPermissions("attendance_manage")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(AttendanceTemplate temp){
		try {
			boolean isok = templetService.operateTemplate(temp);
			return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	// del
	@RequiresPermissions("attendance_manage")
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delTemplate(Long id){
		try {
			AttendanceTemplate template=  templetService.selectByPrimaryKey(id);
			if(template.getType()==0){
				return new BaseResponse(1,"默认模板不能删除");
			}
			template.setDelFlg(1);
			template.setUpdateDate(new Date());
			templetService.updateByPrimaryKeySelective(template);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="getEmployeeByDepartmentId",method=RequestMethod.POST)
	@ResponseBody
	public List<EmployeeVO> getEmployeeByDepartmentId(Long id){
		Map<String, Object> map = new HashMap<>();
		List<EmployeeVO> duserList = new ArrayList<>();
		Long communityId = getCommunity().getId();
		String uids = templetService.getAllTemplateUser(communityId);
		try {
			map.put("communityId", communityId);
			map.put("departmentId", id);
			if(uids!=null){
				map.put("uids", uids.split(","));
			}else{
				map.put("uids", null);
			}
			duserList  = employeeService.getAllEmployee(map);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return duserList; 
	}
	
	//给以前录入的小区 增加默认考勤模板
	@RequestMapping(value="/addDefaultTemplate",method=RequestMethod.GET)
	@ResponseBody
	public BaseResponse addDefaultTemplate(){
		try {
			List<Community> list = communityService.getNoTemplateCommunity();
			for (Community community : list) {
				templetService.addDefaultTemplate(community);
			}
			return new BaseResponse(0, "ok");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
