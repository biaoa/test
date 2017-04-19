package com.linle.mobileapi.v1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.common.util.SysConfig;
import com.linle.communitySuggestions.service.CommunitySuggestionsService;
import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;
import com.linle.communitySuggestionsComment.service.CommunitySuggestionsCommentService;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.CommunitySuggestionsRequest;
import com.linle.mobileapi.v1.request.GetAdviceRequest;
import com.linle.mobileapi.v1.request.SuggestionsCommentOperateRequest;
import com.linle.mobileapi.v1.response.CommunitySuggestionsResponse;
import com.linle.mobileapi.v1.response.GetAdviceResponse;
import com.linle.mobileapi.v1.response.SuggestionsCommentResponse;

@Controller
@RequestMapping("/api/1")
public class CommunitySuggestionsAPIController  extends BaseController {

	@Autowired
	private CommunitySuggestionsService suggestionsService;
	
	@Autowired
	private CommunitySuggestionsCommentService communitySuggestionsCommentService;
	
	
	/**
	 * 
	 * @Description 小区居民提交反馈建议
	 * 旧接口
	 */
	@RequestMapping(value = "postAdviceToManagement", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addSuggestions(CommunitySuggestionsRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommunitySuggestions Suggestions = new CommunitySuggestions();
				Suggestions.setUserId(getCurrentUser().getId());
				Suggestions.setCommunityId(getUserCommunity().getId());
				Suggestions.setCreateDate(new Date());
				Suggestions.setAdvice(req.getAdvice());
				if (req.getList() != null) {
					Long folderId = doFile(req.getList(), servletRequest, SysConfig.COMMUNITY_SUGGESTIONS);
					Suggestions.setFolderId(folderId);
				}
				suggestionsService.insertSelective(Suggestions);
				return BaseResponse.AddSuccess;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			_logger.error("提交反馈信息出现错误：" + e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 获得反馈建议列表
	 * @param req
	 * @param errors
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	@RequestMapping(value = "getAdvice", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getAdvice(@Valid GetAdviceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> map = new HashMap<>();
				map.put("communityId", userInfo.getCommunity().getId());
				map.put("uid", userInfo.getId());
				LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
				GetAdviceResponse res = new GetAdviceResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				res.setData(suggestionsService.getAdviceForAPI(map));
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	
	/**
	 * 获得反馈建议详情以及留言内容
	 * @param req
	 * @param errors
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	@RequestMapping(value = "getCommunitySuggestionsById", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getCommunitySuggestionsById(@Valid GetAdviceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommunitySuggestionsResponse res = new CommunitySuggestionsResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				res.setCommunitySuggestions(suggestionsService.selectById(req.getId()));
				Map<String, Object> map = new HashMap<>();
				map.put("suggestionsId", req.getId());
				LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
			    res.setCommentList(communitySuggestionsCommentService.getSuggestionsDetailForApi(map));
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	/**
	 * 反馈建议留言
	 * @param req
	 * @param errors
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commentSuggestionsOperate", method = RequestMethod.POST)
	public BaseResponse commentSuggestionsOperate(@Valid SuggestionsCommentOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			SuggestionsCommentResponse res=new SuggestionsCommentResponse();
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();//sid
				CommunitySuggestionsComment record=new CommunitySuggestionsComment();
				record.setSuggestionsId(req.getSuggestionsId());
				record.setContent(req.getContent());
				record.setCreateTime(new Date());
				record.setIsDel(0);
				record.setUserId(user.getId());
				communitySuggestionsCommentService.insertSelective(record);
				if(user.getLogo()==null||"".equals(user.getLogo())){
					res.setCommentUserImg("resources/images/default_user.png");
				}else{
					res.setCommentUserImg(user.getLogo());
				}
				//2.用户留言，则该条记录改为处理中
				Map<String,Object> map = new HashMap<>();
				map.put("id", req.getSuggestionsId());
				map.put("status", 1);
				suggestionsService.updateStatus(map);
				
				res.setCode(0);
				res.setMsg("留言成功");
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	

}
