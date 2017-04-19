package com.linle.communityPresident;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.communityPresident.service.CommunityPresidentService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;

@Controller
@RequestMapping("/sys/communityPresident")
public class CommunityPresidentController extends BaseController {
	
	
	@Autowired
	private CommunityPresidentService communityPresidentService;
	
	@Autowired
	private CommunityService communityService;
	
	@RequestMapping(value="/list")
	public String list(Model model,Integer pageNo){
		Page<CommunityPresident> page = new Page<CommunityPresident>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		params.put("uid", getCurrentUser().getId());
		page.setParams(params);
		try {
			communityPresidentService.findAllCommunityPresident(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/communityPresident/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String toAdd(Long id,Model model){
		CommunityPresident communityPresident = new CommunityPresident();
		List<Community> communityList = new ArrayList<>();
		if(id!=null){
			communityPresident = communityPresidentService.selectByPrimaryKey(id);
			communityList = communityService.getCommunityByPresident(id);
		}
		model.addAttribute("communityPresident", communityPresident);
		model.addAttribute("communityList", communityList);
		return "/communityPresident/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(CommunityPresident communityPresident){
		try {
			if(communityPresident.getId()!=null){
				communityPresident.setUpdateDate(new Date());
				communityPresidentService.updateByPrimaryKeySelective(communityPresident);
				return BaseResponse.OperateSuccess;
			}
			communityPresident.setSuperiorId(getCurrentUser().getId());
			return communityPresidentService.addCommunityPresident(communityPresident);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//社长自己修改信息  
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Model model){
		CommunityPresident communityPresident = new CommunityPresident();
		Users user = getCurrentUser();
		if(user.getIdentity()==UserType.SZ){
			communityPresident = communityPresidentService.selectByUserId(user.getId());
			model.addAttribute("communityPresident", communityPresident);
			return "/communityPresident/add";
		}
		return "error/nodata";
	}
	
	//社长切换小区
	@RequiresPermissions("modify_president")
	@RequestMapping(value="/modifyCommunity",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse modifyCommunity(Long id){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("selectedCommunity", id);
		return BaseResponse.OperateSuccess;
	}
}
