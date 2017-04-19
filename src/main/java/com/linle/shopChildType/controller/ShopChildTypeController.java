package com.linle.shopChildType.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopChildType;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.shopChildType.service.ShopChildTypeService;
import com.linle.shopMainType.service.ShopMainTypeService;


@Controller
@RequestMapping("/shopChildType")
public class ShopChildTypeController extends BaseController {
	
	@Autowired
	private ShopChildTypeService childService;
	
	@Autowired
	private ShopMainTypeService mainTypeService;
	
	
	@RequiresPermissions("shopChildType_list")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model) {
		Page<ShopChildType> page = new Page<ShopChildType>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		try {
			childService.findAllChildType(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("list", mainTypeService.findAllMainType());
		model.addAttribute("pagelist", page);
		return "/shopType/childType";
	}
	
	@RequiresPermissions("shopChildType_list")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(ShopChildType childType) {
		try {
			//判断分类名称是否存在
			if(childService.selectByTypeName(childType.getTypeName())!=null){
				return new BaseResponse(1,"分类名称已存在");
			}
			if(childType.getId()!=null){
				childType.setUpdateDate(new Date());
				childService.updateByPrimaryKeySelective(childType);
			}else{
				childType.setCreateDate(new Date());
				childService.insertSelective(childType);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="/selectBymain",method=RequestMethod.POST)
	@ResponseBody
	public List<ShopChildType> selectBymain(Long mid){
		List<ShopChildType> list = childService.selectByMain(mid);
		return list;
	}
}
