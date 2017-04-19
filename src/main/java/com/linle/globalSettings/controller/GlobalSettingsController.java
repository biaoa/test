package com.linle.globalSettings.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.Users;
import com.linle.globalSettings.model.GlobalSettings;
import com.linle.globalSettings.service.GlobalSettingsService;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @Description: 配置管理
 * @author chenkai
 *
 */
@Controller
@RequestMapping("globalSettings")
public class GlobalSettingsController extends BaseController{

	@Autowired
	private GlobalSettingsService globalSettingsService;
	@Autowired
	private CommunityService communityService;
	// 列表
	@RequiresPermissions("globalSettings_list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<GlobalSettings> page = new Page<GlobalSettings>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			Long communityId=null;
			if(getCommunity()!=null){
				communityId= getCommunity().getId();
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("communityId",communityId);
			page.setParams(params);
			globalSettingsService.getAllForPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/globalSettings/globalSettings_list";
	}

	@RequiresPermissions("globalSettings_list")
	@RequestMapping(value = "/del/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(@PathVariable Long id) {
		try {
			GlobalSettings record=new GlobalSettings();
			record.setId(id);
			record.setIsDel(1);
			globalSettingsService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("globalSettings_list")
	@RequestMapping(value = "/doOperate",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOperate(GlobalSettings globalSettings) {
		try {
			Community currCommunity=getCommunity();
			GlobalSettings record=new GlobalSettings();

			record=globalSettingsService.selectBySettingKey(currCommunity.getId(),globalSettings.getSettingKey());
			if(globalSettings.getId()==null){
//				判断数据库是否已存在该类型
				if(record!=null){
					return new BaseResponse(2, "该缴费类型已存在");
				}
				globalSettings.setCreateDate(new Date());
				globalSettings.setIsDel(0);
				globalSettings.setCommunityId(currCommunity.getId());
				globalSettingsService.insertSelective(globalSettings);
			}else{
				if(record!=null&&globalSettings.getId()!=record.getId()){
					return new BaseResponse(2, "该缴费类型已存在");
				}
				globalSettings.setUpdateDate(new Date());
				globalSettingsService.updateByPrimaryKeySelective(globalSettings);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//toAdd
	@RequiresPermissions("globalSettings_list")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView toAdd(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		GlobalSettings obj= new GlobalSettings();
		if (id != null) {
			 obj=globalSettingsService.selectByPrimaryKey(id);
		}
//		List<Community> communityList = communityService.selectAllCommunity();
//		model.addAttribute("communityList", communityList);
		model.addAttribute("globalSettings", obj);
		mv.setViewName("globalSettings/globalSettings_add");
		return mv;
	}
	
}
