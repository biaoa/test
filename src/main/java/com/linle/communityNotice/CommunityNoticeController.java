package com.linle.communityNotice;

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

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.communityNotice.service.CommunityNoticeService;
import com.linle.entity.sys.CommunityNotice;
import com.linle.mobileapi.base.BaseResponse;

@Controller
@RequestMapping("/communityNotice")
public class CommunityNoticeController extends BaseController {

	@Autowired
	private CommunityNoticeService communityNoticeService;
	
	@RequiresPermissions("notice_manage")
	@RequestMapping("/list")
	public String list(Integer pageNo, Model model,Integer status,Integer typeSerach) {
		Page<CommunityNotice> page = new Page<CommunityNotice>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		if(status==null){
			status = 0;
		}
		params.put("status", status);
		if(typeSerach==null){
			typeSerach=0;
		}
		params.put("typeSerach", typeSerach);
		params.put("communityId", getCommunity().getId());
		page.setParams(params);
		try {
			communityNoticeService.getAllCommunityNotice(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			return "error/nodata";
		}
		model.addAttribute("status", status);
		model.addAttribute("typeSerach", typeSerach);
		model.addAttribute("pagelist", page);
		return "communityNotice/communityNotice_list";
	}
	
	@RequiresPermissions("notice_manage")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse addCommunityNotice(CommunityNotice communityNotice){
		if(communityNotice.getId()!=null){
			communityNotice.setUpdateDate(new Date());
			communityNoticeService.updateByPrimaryKeySelective(communityNotice);
		}else{
			communityNotice.setCreateUser(getCurrentUser().getId());
			communityNotice.setCommunityId(getCommunity().getId());
			communityNotice.setStatus(0);
			communityNotice.setCreateDate(new Date());
			communityNoticeService.insertSelective(communityNotice);
		}
		return BaseResponse.OperateSuccess;
	}
	
	@RequiresPermissions("notice_manage")
	@ResponseBody
	@RequestMapping(value="/send",method=RequestMethod.POST)
	public BaseResponse send(Long id){
		try {
			return communityNoticeService.send(id,getCommunity());
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("notice_manage")
	@RequestMapping(value="/del")
	@ResponseBody
	public BaseResponse del(Long id){
		try {
			communityNoticeService.del(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	@RequiresPermissions("notice_manage")
	@ResponseBody
	@RequestMapping(value="/batchsend/{type}",method=RequestMethod.POST)
	public BaseResponse batchsend(@PathVariable Integer type){
		try {
			BaseResponse baseResponse=new BaseResponse();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeSerach", type);
			map.put("community_id", getCommunity().getId());
			List<CommunityNotice> list = communityNoticeService.selectCommunityNoticeByCommunityId(map);
			for (CommunityNotice communityNotice : list) {
				 baseResponse= communityNoticeService.send(communityNotice.getId(),getCommunity());
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
}
