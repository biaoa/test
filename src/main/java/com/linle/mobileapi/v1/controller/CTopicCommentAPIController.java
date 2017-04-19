package com.linle.mobileapi.v1.controller;

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

import com.linle.cTopic.service.CTopicService;
import com.linle.cTopicComment.service.CTopicCommentService;
import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CTopicVO;
import com.linle.mobileapi.v1.request.CommentOperateRequest;
import com.linle.mobileapi.v1.request.SupportOperateRequest;
import com.linle.mobileapi.v1.request.TopicDetailListRequest;
import com.linle.mobileapi.v1.request.TopicReplyRequest;
import com.linle.mobileapi.v1.response.TopicCommentListResponse;
import com.linle.mobileapi.v1.response.TopicCommentResponse;
import com.linle.mobileapi.v1.response.TopicReplyListResponse;
import com.linle.mobileapi.v1.response.TopicReplyResponse;
import com.linle.topicUserManager.model.TopicUserManager;
import com.linle.topicUserManager.service.TopicUserManagerService;

@Controller
@RequestMapping("/api/1")
public class CTopicCommentAPIController extends BaseController {

	@Autowired
	private CTopicCommentService cTopicCommentService;
	
	@Autowired
	private CTopicService cTopicService;
	
	@Autowired
	private TopicUserManagerService topicUserManagerService;
	
	// 获取话题的详情
	@ResponseBody
	@RequestMapping(value = "/getTopicDetail",method = RequestMethod.POST)
	public BaseResponse getTopicDetail(TopicDetailListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		TopicCommentListResponse res = new TopicCommentListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();//sid
				Map<String, Object> map = new HashMap<>();
				map.put("topicId", req.getTopicId());
				map.put("needDetails", req.getNeedDetails());
				map.put("userId", user.getId());
				if(req.getNeedDetails()==1){//是否需要把话题数据返回过来 0不需要1需要
					CTopicVO cTopicVo=cTopicService.getTopicByTopicIdForApi(map);
					res.setTopic(cTopicVo);
				}
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(cTopicCommentService.getTopicDetailForApi(map));
				res.setCode(0);
				res.setMsg("获取成功");
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
		return res;
	}
		
	/**
	 * 发布评论
	 * 1.增加评论记录
	 * 2.修改话题表评论字段
	 */
	@ResponseBody
	@RequestMapping(value = "/commentOperate")
	public BaseResponse commentOperate(@Valid CommentOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			TopicCommentResponse res=new TopicCommentResponse();
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();//sid
				CTopic topic=cTopicService.selectByPrimaryKey(req.getTopicId());
				if(topic.getIsDel()==1){//判断该话题是否删除
					return new BaseResponse(10,"该话题已删除");
				}
				TopicUserManager topicUserManager =topicUserManagerService.selectByUserIdAndTopicTypeId(user.getId(), topic.getTopicTypeId(), "reply");
				if(topicUserManager!=null){
					return new BaseResponse(1,"不能发布评论");
				}
				long commentId=cTopicCommentService.commentOperate(req, user);
				if(commentId==0){
					return BaseResponse.OperateFail;
				}
				res.setCode(0);
				res.setMsg("评论成功");
				res.setCommentId(commentId);
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	//获取别人回复我的
	@ResponseBody
	@RequestMapping(value = "/getTopicReplyList",method = RequestMethod.POST)
	public BaseResponse getTopicReplyList(TopicReplyRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		TopicReplyListResponse res = new TopicReplyListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("userId", user.getId());
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(cTopicCommentService.getTopicReplyListForApi(map));
				res.setCode(0);
				res.setMsg("获取成功");
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	
	//获取当前用户所有未读回复数
	@ResponseBody
	@RequestMapping(value = "/getUnReadCount",method = RequestMethod.POST)
	public BaseResponse getUnReadCount(TopicReplyRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		TopicReplyResponse res = new TopicReplyResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("userId", user.getId());
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setUnReadCount(cTopicCommentService.getUnReadCount(map));
				res.setCode(0);
				res.setMsg("获取成功");
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	//处理已读回复
	@ResponseBody
	@RequestMapping(value = "/updateReadCommentByUserid",method = RequestMethod.POST)
	public BaseResponse updateReadCommentByUserid(CommentOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();//sid
				boolean flag=cTopicCommentService.updateReadCommentByUserid(user);
				return flag ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	/**
	 * 别人回复我的tab列表  移除
	 */
	@ResponseBody
	@RequestMapping(value = "/removeReplyCommentById",method = RequestMethod.POST)
	public BaseResponse removeReplyCommentById(SupportOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				boolean flag=cTopicCommentService.removeReplyCommentById(req.getCommentId());
				return flag ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCommentById",method = RequestMethod.POST)
	public BaseResponse deleteCommentById(SupportOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				boolean flag=cTopicCommentService.deleteTopicCommentById(req.getCommentId());
				return flag ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}