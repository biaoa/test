package com.linle.communityExpress.controller;

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
import com.linle.common.util.ExpressUtil;
import com.linle.common.util.Page;
import com.linle.communityExpress.service.CommunityExpressService;
import com.linle.entity.sys.CommunityExpress;
import com.linle.mobileapi.base.BaseResponse;

@Controller
@RequestMapping("/communityExpress")
public class CommunityExpressController extends BaseController {
	
	@Autowired
	private CommunityExpressService expressService;

	@RequiresPermissions("express_manager")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model) {
		Page<CommunityExpress> page = new Page<CommunityExpress>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",getCommunity().getId());
		page.setParams(params);
		try {
			expressService.findAllExpress(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/communityExpress/communityExpress_list";
	}
	
	@RequiresPermissions("express_manager")
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseResponse add(CommunityExpress express){
		try {
			express.setCommunityId(getCommunity().getId());
			express.setCreateDate(new Date());
			express.setLogo(ExpressUtil.getPath(express.getExpressName()));
			if (express.getId()!=null) {
				expressService.updateByPrimaryKeySelective(express);
				return BaseResponse.OperateSuccess;
			}else{
				expressService.insertSelective(express);
				return BaseResponse.AddSuccess;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("添加站点信息出错："+e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("express_manager")
	@ResponseBody
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public BaseResponse del(Long eid){
		try {
			expressService.deleteByPrimaryKey(eid);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("添加站点信息出错："+e);
			return BaseResponse.ServerException;
		}
	}
}
