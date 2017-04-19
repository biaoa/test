package com.linle.serviceAgreement.controller;

import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.commonProblem.model.CommonProblem;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.serviceAgreement.model.ServiceAgreement;
import com.linle.serviceAgreement.service.ServiceAgreementService;
@Controller
@RequestMapping("/serviceAgreement")
public class ServiceAgreementController extends BaseController{
  
	@Autowired
    private ServiceAgreementService serviceAgreementService;
  
	@RequiresPermissions("serviceAgreement_manage")
	@RequestMapping("/list")
	public String list(Integer pageNo,Model model){
		Page<ServiceAgreement> page = new Page<ServiceAgreement>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			serviceAgreementService.getAllData(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/serviceAgreement/serviceAgreement_list";
	}
	
	@RequiresPermissions("serviceAgreement_manage")
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(Long id,Model model){
		ModelAndView mv = new ModelAndView();
		ServiceAgreement serviceAgreement=new ServiceAgreement();
		if (id != null) {
			serviceAgreement = serviceAgreementService.selectByPrimaryKey(id);
		}
		model.addAttribute("serviceAgreement", serviceAgreement);
		mv.setViewName("serviceAgreement/serviceAgreement_add");
		return mv;
	}
	
	@RequiresPermissions("serviceAgreement_manage")
	@RequestMapping("/doOperate")
	@ResponseBody
	public BaseResponse doOperate(ServiceAgreement serviceAgreement){
		try {                
			if (serviceAgreement.getId() != null) {//修改
				serviceAgreementService.updateByPrimaryKeySelective(serviceAgreement);
				return BaseResponse.OperateSuccess;
			}else{//新增
				HashMap<String, Object> map = new HashMap<>();
				map.put("typeId", serviceAgreement.getTypeId());
				ServiceAgreement record  = serviceAgreementService.selectByTypeId(map);
				if(record!=null){
					String typeName="";
					if(record.getTypeId()==0){
						typeName="用户板类型";
					}else if(record.getTypeId()==1){
						typeName="商家板类型";
					}else if(record.getTypeId()==2){
						typeName="物业板类型";
					}
					return new BaseResponse(2, "已存在"+typeName+",不能重复添加！");
				}
				if (serviceAgreementService.insertSelective(serviceAgreement) > 0) {
					return BaseResponse.OperateSuccess;
				}
			}
			return BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//预览
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable Long id,Model model){
		ServiceAgreement serviceAgreement  = serviceAgreementService.selectByPrimaryKey(id);
		model.addAttribute("serviceAgreement", serviceAgreement);
		return "/serviceAgreement/serviceAgreement_detail";
	}
	
	@RequiresPermissions("serviceAgreement_manage")
	@RequestMapping(value = "/del",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(Long id) {
		try {
			serviceAgreementService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
