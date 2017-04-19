package com.linle.propertyCompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
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
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.PropertyCompany;
import com.linle.entity.sys.SysRegion;
import com.linle.entity.sys.Users;
import com.linle.propertyCompany.service.PropertyCompanyService;
import com.linle.system.service.RegionService;
/**
 * 
* @ClassName: PropertyCompanyController 
* @Description: 物业管理
* @author pangd
* @date 2016年3月17日 下午1:42:31 
*
 */
@Controller
@RequestMapping("propertyCompany")
public class PropertyCompanyController  extends BaseController{
	
	
	@Autowired
	private PropertyCompanyService companyService;
	
	@Autowired
	private RegionService regionService;
	
	@RequiresPermissions("propertyCompany_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<PropertyCompany> page = new Page<PropertyCompany>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
//		params.put("available", true);
		Users user =getCurrentUser();
		if (user.getIdentity()!=UserType.SYS) {
			if(user.getIdentity()==UserType.WY){
				params.put("uid", user.getId());
			}else{
				params.put("createUserId", user.getId());
			}
		}
		page.setParams(params);
		try {
			companyService.findAllPropertyCompanys(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/propertyCompany/propertyCompany_list";
	}
	
	
	/**
	 * 跳转到添加物业的页面
	 * 
	 * @return
	 */
	@RequiresPermissions(value={"add_propertyCompany","edit_regionalagency"},logical=Logical.OR)
	@RequestMapping(value = "/addPropertyCompany", method = RequestMethod.GET)
	public ModelAndView addPropertyCompany(Model model,Long id) {
		ModelAndView mv = new ModelAndView();
		List<SysRegion> provinceList = regionService.getProvinces();
		List<SysRegion> cityList = new ArrayList<SysRegion>();
		List<SysRegion> countyList = new ArrayList<SysRegion>();
		
		PropertyCompany propertyCompany = new PropertyCompany();
		if(id!=null){
			propertyCompany = companyService.selectByPrimaryKey(id);
			if(StringUtil.isNotNull(propertyCompany.getPhone())){
				propertyCompany.setPhone1(propertyCompany.getPhone().split("-")[0]);
				propertyCompany.setPhone2(propertyCompany.getPhone().split("-")[1]);
			}
			propertyCompany.setWork1(propertyCompany.getWorkTime().split(",")[0]);
			propertyCompany.setWork2(propertyCompany.getWorkTime().split(",")[1]);
			cityList = regionService.getCitys(propertyCompany.getSysRegion().getParent().getParent());
			countyList = regionService.getCountys(propertyCompany.getSysRegion().getParent());
		}else{
			for (SysRegion province : provinceList) {
				cityList = regionService.getCitys(province);
				for (SysRegion city : cityList) {
					countyList = regionService.getCountys(city);
					break;
				}
				break;
			}
		}
		model.addAttribute("propertyCompany", propertyCompany);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("countyList", countyList);
		mv.setViewName("propertyCompany/propertyCompany_add");
		return mv;
	}
	/**
	 * 添加物业
	 * 
	 * @return
	 */
	@RequiresPermissions(value={"add_propertyCompany","_edit_propertyCompany"},logical=Logical.OR)
	@RequestMapping(value = "/addPropertyCompany", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseMsg addPropertyCompany(@Valid PropertyCompany company ,BindingResult errors, Model model) {
		company.setPhone(company.getPhone1()+"-"+company.getPhone2());
		company.setWorkTime(company.getWork1()+","+company.getWork2());
		company.setCreateUserId(getCurrentUser().getId());
		if(company.getId()!=null){
			companyService.updateByPrimaryKeySelective(company);
			return new ResponseMsg(0, "修改成功", null);
		}
		if (companyService.addPropertyCompany(company)) {
			return new ResponseMsg(0, "新增成功", null);
		}
		return new ResponseMsg(1, "新增失败", null);
		
	}
	
	@RequiresPermissions("_edit_propertyCompany")
	@RequestMapping("/edit")
	public String edit(Model model){
		List<SysRegion> provinceList = regionService.getProvinces();
		List<SysRegion> cityList = new ArrayList<SysRegion>();
		List<SysRegion> countyList = new ArrayList<SysRegion>();
		Users user = getCurrentUser();
		PropertyCompany propertyCompany = companyService.getPopertyCompanyByuserID(user.getId());
		propertyCompany.setPhone1(propertyCompany.getPhone().split("-")[0]);
		propertyCompany.setPhone2(propertyCompany.getPhone().split("-")[1]);
		propertyCompany.setWork1(propertyCompany.getWorkTime().split(",")[0]);
		propertyCompany.setWork2(propertyCompany.getWorkTime().split(",")[1]);
		cityList = regionService.getCitys(propertyCompany.getSysRegion().getParent().getParent());
		countyList = regionService.getCountys(propertyCompany.getSysRegion().getParent());
		model.addAttribute("propertyCompany", propertyCompany);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("countyList", countyList);
		model.addAttribute("type", "edit");
		return "propertyCompany/propertyCompany_add";
	}
	
}
