package com.linle.problemType.controller;

import java.util.HashMap;
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
import com.linle.mobileapi.base.BaseResponse;
import com.linle.problemType.model.ProblemType;
import com.linle.problemType.service.ProblemTypeService;

/**
 * 
 * @Description: 问题类型管理
 * @author chenkai
 * @date 2016年7月21日
 *
 */
@Controller
@RequestMapping("problemType")
public class ProblemTypeController extends BaseController{

	@Autowired
	private ProblemTypeService problemTypeService;
	
	// 列表
	@RequiresPermissions("problemType_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo,Integer belongTo, Model model) {
		Page<ProblemType> page = new Page<ProblemType>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		params.put("belongTo", belongTo);
		page.setParams(params);
		try {
			problemTypeService.getAllDataForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		model.addAttribute("belongTo", belongTo);
		return "/problemType/problemType_list";
	}

	//toAdd
	@RequiresPermissions("problemType_manage")
	@RequestMapping(value = "/toAddProblemType", method = RequestMethod.GET)
	public ModelAndView toAddProblemType(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		ProblemType type = new ProblemType();
		if (id != null) {
			type = problemTypeService.selectByPrimaryKey(id);
		}
		model.addAttribute("problemType", type);
		mv.setViewName("problemType/problemType_add");
		return mv;
	}

	//save
	@RequiresPermissions("problemType_manage")
	@RequestMapping(value = "/addProblemType", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addProblemType(@Valid ProblemType problemType, BindingResult errors, Model model) {
		try {
			if (problemType.getId() != null) {//修改
				problemTypeService.updateByPrimaryKeySelective(problemType);
				return BaseResponse.OperateSuccess;
			}else{//新增
				if (problemTypeService.insertSelective(problemType) > 0) {
					return BaseResponse.OperateSuccess;
				}
			}
			return BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}	
	
	
	@RequiresPermissions("problemType_manage")
	@RequestMapping(value = "/delProblemType",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delProblemType(Long id) {
		try {
			problemTypeService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

 }
