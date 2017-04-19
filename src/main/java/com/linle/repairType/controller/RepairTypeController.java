package com.linle.repairType.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.linle.entity.sys.RepairType;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.repairType.sercie.RepairTypeService;

/**
 * 
 * @ClassName: HouseResourceController
 * @Description: 报修类型管理
 * @author pangd
 * @date 2016年3月28日 上午9:48:13
 *
 */
@Controller
@RequestMapping("repairType")
public class RepairTypeController extends BaseController{

	@Autowired
	private RepairTypeService typeService;

	// 列表
	@RequiresPermissions("repairType_manager")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<RepairType> page = new Page<RepairType>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		// params.put("available", true);

		page.setParams(params);
		try {
			typeService.findAllRepairType(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/repairType/repairType_list";
	}

	// go 新增
	@RequiresPermissions("repairType_manager")
	@RequestMapping(value = "/addRepairType", method = RequestMethod.GET)
	public ModelAndView addrepairType(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		RepairType type = new RepairType();
		if (id != null) {
			type = typeService.selectByPrimaryKey(id);
		}
		model.addAttribute("repairType", type);
		mv.setViewName("repairType/repairType_add");
		return mv;
	}

	// 添加类型
	@RequiresPermissions("repairType_manager")
	@RequestMapping(value = "/addRepairType", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addRepairType(@Valid RepairType type, BindingResult errors, Model model) {
		try {
			if (type.getId() != null) {
				type.setUpdateDate(new Date());
				typeService.updateByPrimaryKeySelective(type);
				return BaseResponse.OperateSuccess;
			}
			if (typeService.insertSelective(type) > 0) {
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}

	@RequiresPermissions("repairType_manager")
	@RequestMapping(value = "/delRepairType/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delRepairType(@PathVariable Long id) {
		try {
			typeService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
