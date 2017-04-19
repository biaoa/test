package com.linle.commonProblem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.commonProblem.model.CommonProblem;
import com.linle.commonProblem.service.CommonProblemService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.problemType.model.ProblemType;
import com.linle.problemType.service.ProblemTypeService;

/**
 * 
 * @Description: 常见问题管理
 * @author chenkai
 * @date 2016年7月21日
 *
 */
@Controller
@RequestMapping("commonProblem")
public class CommonProblemController extends BaseController{

	@Autowired
	private CommonProblemService commonProblemService;

	@Autowired
	private ProblemTypeService problemTypeService;
	
	// 列表
	@RequiresPermissions("commonProblem_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo,Long typeId,Integer belongTo, Model model) {
		Page<CommonProblem> page = new Page<CommonProblem>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		
		params.put("typeId", typeId);
		params.put("belongTo", belongTo);
		
		page.setParams(params);
		try {
			commonProblemService.getAllDataForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		List<ProblemType> typeList=problemTypeService.getAllType();
		model.addAttribute("typeList", typeList);
		model.addAttribute("pagelist", page);
		model.addAttribute("typeId", typeId);
		model.addAttribute("belongTo", belongTo);
		return "/commonProblem/commonProblem_list";
	}

	//toAdd
	@RequiresPermissions("commonProblem_manage")
	@RequestMapping(value = "/toAddCommonProblem", method = RequestMethod.GET)
	public ModelAndView toAddcommonProblem(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		CommonProblem type = new CommonProblem();
		if (id != null) {
			type = commonProblemService.selectByPrimaryKey(id);
		}
		List<ProblemType> typeList=problemTypeService.getAllType();
		model.addAttribute("typeList", typeList);
		model.addAttribute("commonProblem", type);
		mv.setViewName("commonProblem/commonProblem_add");
		return mv;
	}

	//save
	@RequiresPermissions("commonProblem_manage")
	@RequestMapping(value = "/addCommonProblem", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addcommonProblem(@Valid CommonProblem commonProblem, BindingResult errors, Model model) {
		try {
			if (commonProblem.getId() != null) {//修改
				commonProblemService.updateByPrimaryKeySelective(commonProblem);
				return BaseResponse.OperateSuccess;
			}else{//新增
				if (commonProblemService.insertSelective(commonProblem) > 0) {
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
		CommonProblem commonProblem  = commonProblemService.selectByPrimaryKey(id);
		model.addAttribute("commonProblem", commonProblem);
		return "/commonProblem/commonProblem_detail";
	}
	
	@RequiresPermissions("commonProblem_manage")
	@RequestMapping(value = "/delCommonProblem",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delCommonProblem(Long id) {
		try {
			commonProblemService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

 }
