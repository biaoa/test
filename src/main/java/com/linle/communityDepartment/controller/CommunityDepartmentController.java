package com.linle.communityDepartment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.entity.sys.CommunityDepartment;
import com.linle.mobileapi.base.BaseResponse;
/**
 * 
 * @author pangd
 * @Description 小区部门管理
 */
@Controller
@RequestMapping("/communityDepartment")
public class CommunityDepartmentController extends BaseController {

	@Autowired
	private CommunityDepartmentService communityDepartmentService;
	
	@RequiresPermissions("community_department_manage")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model) {
		Page<CommunityDepartment> page = new Page<CommunityDepartment>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",getCommunity().getId());
		page.setParams(params);
		try {
			communityDepartmentService.getAllDate(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/communityInfo/community_department_list";
	}
	
	
	@RequiresPermissions(value="community_department_manage")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(CommunityDepartment communityDepartment){
		try {
			if(communityDepartment.getId()!=null){
				communityDepartmentService.updateByPrimaryKeySelective(communityDepartment);
			}else{
				communityDepartment.setCommunityId(getCommunity().getId());
				communityDepartment.setCreateDate(new Date());
				communityDepartmentService.insert(communityDepartment);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			_logger.info("添加部门出错:"+e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
}
