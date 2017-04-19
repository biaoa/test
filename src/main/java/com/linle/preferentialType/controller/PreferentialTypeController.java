package com.linle.preferentialType.controller;

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
import com.linle.entity.sys.PreferentialType;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.preferentialType.service.PreferentialTypeService;

/**
 * 
 * @author pangd
 * @Description 优惠类型维护
 */
@Controller
@RequestMapping("/preferentialType")
public class PreferentialTypeController extends BaseController {
	
	@Autowired
	private  PreferentialTypeService typeService;
	
	@RequiresPermissions("preferentialType_list")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Integer pageNo,Model model){
		
		Page<PreferentialType> page = new Page<PreferentialType>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
//		params.put("", "");
		page.setParams(params);
		try {
			typeService.findAllPreferentialType(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/preferentialType/preferentialType_list";
	}
	
	
	@RequiresPermissions("preferentialType_list")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd() {
		return "/preferentialType/preferentialType_add";
	}

	@RequiresPermissions("preferentialType_list")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(PreferentialType preferentialType) {
		try {
			preferentialType.setStatus(0);
			preferentialType.setCreateDate(new Date());
			boolean isok = typeService.insertSelective(preferentialType)>0;
			_logger.info("新增商品分类的结果是：" + isok);
			;
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			_logger.info("新增商品出错：" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	
}
