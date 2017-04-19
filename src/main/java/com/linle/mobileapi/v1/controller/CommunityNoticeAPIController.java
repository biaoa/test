package com.linle.mobileapi.v1.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.communityNotice.service.CommunityNoticeService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.CommunityNoticeRequest;
import com.linle.mobileapi.v1.response.CommunityNoticeListResponse;

@Controller
@RequestMapping("/api/1")
public class CommunityNoticeAPIController extends BaseController {
	
	@Autowired
	private CommunityNoticeService communityNoticeService;
	
	@RequestMapping(value="/getCommunityNoticeList",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getCommunityNoticeList(CommunityNoticeRequest req,Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommunityNoticeListResponse res = new CommunityNoticeListResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				Map<String, Object> map = new HashMap<>();
				map.put("communityId",getUserCommunity().getId());
				map.put("type",req.getType());
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(communityNoticeService.getAllForAPI(map));
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	//获得显示首页物业公告内容
	@RequestMapping(value="/getCommunityNewPublicNoticeList",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getCommunityNewPublicNoticeList(CommunityNoticeRequest req,Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommunityNoticeListResponse res = new CommunityNoticeListResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				Map<String, Object> map = new HashMap<>();
				map.put("communityId",getUserCommunity().getId());
//				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(communityNoticeService.getAllPublicForAPI(map));
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	@RequestMapping(value="/getCommunityNoticeById/{id}",method=RequestMethod.GET)
	public String getCommunityNoticeById(@PathVariable long id,Model model){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("id",id);
			model.addAttribute("communityNotice", communityNoticeService.selectByIdForAPI(map));
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return "error/500";
		}
		return "communityNotice/communityNotice_detail";
		
	}
	
	
}
