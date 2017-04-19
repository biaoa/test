package com.linle.regionalAgency.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.RegionalAgency;
import com.linle.entity.sys.SysRegion;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.regionalAgency.service.RegionalAgencyService;
import com.linle.system.service.RegionService;
/**
 * 
* @ClassName: RegionalAgencyController 
* @Description: 区域代理商管理
* @author pangd
* @date 2016年3月17日 下午1:42:31 
*
 */
@Controller
@RequestMapping("regionalagency")
public class RegionalAgencyController  extends BaseController{
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RegionalAgencyService regionalagencyService;
	
	@RequiresPermissions("regionalagency_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<RegionalAgency> page = new Page<RegionalAgency>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		Users user = getCurrentUser();
		if (user.getIdentity()!=UserType.SYS) {
			params.put("createUserId", user.getId());
		}
		page.setParams(params);
		try {
			regionalagencyService.findAllPropertyCompanys(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/regionalagency/regionalagency_list";
	}
	
	
	/**
	 * 跳转到区域代理商添加的页面
	 * 
	 * @return
	 */
	@RequiresPermissions("add_regionalagency")
	@RequestMapping(value = "/addRegionalagenc", method = RequestMethod.GET)
	public ModelAndView addRolePage(Model model,Long id) {
		ModelAndView mv = new ModelAndView();
		RegionalAgency agency = new RegionalAgency();
		List<SysRegion> provinceList = regionService.getProvinces();
		List<SysRegion> cityList = new ArrayList<SysRegion>();
		if(id!=null){
			agency = regionalagencyService.selectByPrimaryKey(id);
			cityList = regionService.getCitys(agency.getProvince());
		}else{
			for (SysRegion province : provinceList) {
				cityList = regionService.getCitys(province);
				break;
			}
		}
		model.addAttribute("regionalAgency", agency);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		mv.setViewName("regionalagency/regionalagency_add");
		return mv;
	}
	/**
	 * 区域代理商添加
	 * 
	 * @return
	 */
	@RequiresPermissions("add_regionalagency")
	@RequestMapping(value = "/addRegionalagenc", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse  addRole(@Valid RegionalAgency agency,BindingResult errors, Model model) {
		if(agency.getId()!=null){
			regionalagencyService.updateByPrimaryKeySelective(agency);
			return BaseResponse.OperateSuccess;
		}
		agency.setCreateUserId(getCurrentUser().getId());
		if (regionalagencyService.addRegionalAgency(agency)) {
			return BaseResponse.AddSuccess;
		}
		return BaseResponse.AddFail;
		
	}
	
	//代理自己修改信息  
	@RequiresPermissions("_edit_regionalagency")
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Model model){
		Users user = getCurrentUser();
		RegionalAgency regionalAgency = regionalagencyService.getRegionalAgencyByuserID(user.getId());
		model.addAttribute("regionalAgency", regionalAgency);
		return "regionalagency/regionalagency_edit";
	}
	
	@RequiresPermissions("_edit_regionalagency")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse edit(Long id,String phone,String address){
		try {
			RegionalAgency regionalAgency = regionalagencyService.selectByPrimaryKey(id);
			regionalAgency.setPhone(phone);
			regionalAgency.setAddress(address);
			regionalagencyService.updateByPrimaryKeySelective(regionalAgency);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
}
