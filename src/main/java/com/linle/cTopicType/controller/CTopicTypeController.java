package com.linle.cTopicType.controller;

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

import com.linle.cTopicType.service.CTopicTypeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.CTopicType;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @ClassName: CTopicTypeController
 * @Description: 圈子类型管理
 * @author chenkai
 * @date 2016年7月18日
 *
 */
@Controller
@RequestMapping("topicType")
public class CTopicTypeController extends BaseController{

	@Autowired
	private CTopicTypeService typeService;

	// 列表
	@RequiresPermissions("topicType_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<CTopicType> page = new Page<CTopicType>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		page.setParams(params);
		try {
			typeService.getAllTypeForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/topicType/topicType_list";
	}

	//toAdd
	@RequiresPermissions("topicType_manage")
	@RequestMapping(value = "/toAddTopicType", method = RequestMethod.GET)
	public ModelAndView toAddTopicType(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		CTopicType type = new CTopicType();
		if (id != null) {
			type = typeService.selectByPrimaryKey(id);
		}
		model.addAttribute("topicType", type);
		mv.setViewName("topicType/topicType_add");
		return mv;
	}

	//save
	@RequiresPermissions("topicType_manage")
	@RequestMapping(value = "/addTopicType", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addTopicType(@Valid CTopicType topicType, BindingResult errors, Model model) {
		try {
			if (topicType.getTopicTypeId() != null) {//修改
				typeService.updateByPrimaryKeySelective(topicType);
				return BaseResponse.OperateSuccess;
			}else{//新增
				topicType.setIsDel(0);
				if (typeService.insertSelective(topicType) > 0) {
					return BaseResponse.OperateSuccess;
				}
			}
			return BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}
	
	@RequiresPermissions("topicType_manage")
	@RequestMapping(value = "/delTopicType/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delTopicType(@PathVariable Long id) {
		try {
			CTopicType topicType=new CTopicType();
			topicType.setTopicTypeId(id);
			topicType.setIsDel(1);
			typeService.updateByPrimaryKeySelective(topicType);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
