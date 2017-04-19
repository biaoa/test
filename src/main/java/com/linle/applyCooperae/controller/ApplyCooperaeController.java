package com.linle.applyCooperae.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.linle.applyCooperae.model.ApplyCooperae;
import com.linle.applyCooperae.service.ApplyCooperaeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.mobileapi.base.BaseResponse;

@Controller
@RequestMapping("/applyCooperae")
public class ApplyCooperaeController extends BaseController {
	
	@Autowired
	private ApplyCooperaeService applyCooperaeService;
	
	//列表
	@RequiresPermissions("applyCooperae_list")
	@RequestMapping("/list")
	public String lsit(Integer pageNo,Model model){
		Page<ApplyCooperae> page = new Page<>();
		if (pageNo!=null) {
			page.setPageNo(pageNo);
		}
		applyCooperaeService.getAllApplyCooperaeService(page);
		model.addAttribute("pagelist", page);
		return "applyCooperae/list";
	}
	//新增
	@CrossOrigin(origins = {"http://hzlinle.com","http://www.hzlinle.com","http://localhost:10000"})
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(ApplyCooperae applyCooperae,HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		try {
			applyCooperae.setIp(request.getRemoteAddr());
			applyCooperae.setCreateDate(new Date());
			applyCooperaeService.insert(applyCooperae);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return new BaseResponse(0, "您的申请已提交，我们会尽快联系您。");
	}
}
