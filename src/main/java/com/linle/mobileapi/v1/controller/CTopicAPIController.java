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

import com.linle.cSupport.service.CSupportService;
import com.linle.cTopic.service.CTopicService;
import com.linle.cTopicReadRecord.service.CTopicReadRecordService;
import com.linle.cTopicType.service.CTopicTypeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UserVO;
import com.linle.mobileapi.v1.request.HasNewTopicRequest;
import com.linle.mobileapi.v1.request.ReportOperateRequest;
import com.linle.mobileapi.v1.request.SupportOperateRequest;
import com.linle.mobileapi.v1.request.TopicListRequest;
import com.linle.mobileapi.v1.request.TopicRequest;
import com.linle.mobileapi.v1.request.UserRequest;
import com.linle.mobileapi.v1.response.CTopicTypeResponse;
import com.linle.mobileapi.v1.response.HasNewTopicResponse;
import com.linle.mobileapi.v1.response.TopicListResponse;
import com.linle.mobileapi.v1.response.UserResponse;
import com.linle.topicReport.service.TopicReportService;

@Controller
@RequestMapping("/api/1")
public class CTopicAPIController extends BaseController {

	@Autowired
	private CTopicTypeService cTopicTypeService;


	@Autowired
	private CTopicService cTopicService;
	
	@Autowired
	private CSupportService cSupportService;
	
	@Autowired
	private CTopicReadRecordService readRecordService;
	
	@Autowired
	private TopicReportService topicReportService;
	
	// 获得话题类型列表
	@ResponseBody
	@RequestMapping(value = "/getTopicType" ,method = RequestMethod.POST)
	public BaseResponse getTopicType(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		CTopicTypeResponse res = new CTopicTypeResponse();
		try {
			res.setData(cTopicTypeService.getAllType());
			res.setCode(0);
			res.setMsg("获取成功");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return res;
	}

	// 获取圈子话题列表
	@ResponseBody
	@RequestMapping(value = "/getTopicList",method = RequestMethod.POST)
	public BaseResponse getTopicList(TopicListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		TopicListResponse res = new TopicListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("typeId", req.getTypeId());
				map.put("userId", user.getId());
				//是否查看所有小区 1：查看所有小区  0:查看当前小区
				if(req.getCommunityPrivg()==0){
					map.put("communityId",getUserCommunity().getId());//显示当前用户小区圈子
				}
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setLastRequestDate(new Date());
				res.setData(cTopicService.getTopicListForApi(map));
				res.setCode(0);
				res.setMsg("获取成功");
				//更新圈子阅读记录
				readRecordService.updateReadRecord(user.getId(),req.getTypeId());
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("获取圈子话题出错:"+e);
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	// 个人资料 ---个人详情
	@ResponseBody
	@RequestMapping(value = "/getUserInfoByUserId",method = RequestMethod.POST)
	public BaseResponse getUserInfoByUserId(UserRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		UserResponse res = new UserResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				if(req.getUserId()!=null&&!"".equals(req.getUserId())){
					Users user = userInfoService.getById(Long.parseLong(req.getUserId()));
					if(user!=null){
						UserVO userVO=new UserVO();
						userVO.setUserId(user.getId());
						userVO.setNickname(user.getName());
						userVO.setSex(user.getSex());
						userVO.setPicture(user.getLogo());
						res.setData(userVO);
						res.setCode(0);
						res.setMsg("获取成功");
					}else{
						return new BaseResponse(1,"该用户不存在");
					}
				}else{
					return new BaseResponse(1,"查看的用户id不能为空");
				}
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	//获取对方用户发布所有话题 --个人详情(个人发布) 
	@ResponseBody
	@RequestMapping(value = "/getTopicListByToUserId",method = RequestMethod.POST)
	public BaseResponse getTopicListByToUserId(TopicListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		TopicListResponse res = new TopicListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("userId", user.getId());// 当前用户userId
				map.put("toUserId", req.getToUserId());//查看那个人toUserId 
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(cTopicService.getTopicListByUserIdForApi(map));
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
	
	
	// 获取本人发布所有话题 
	@ResponseBody
	@RequestMapping(value = "/getTopicListByUserId",method = RequestMethod.POST)
	public BaseResponse getTopicListByUserId(TopicListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		TopicListResponse res = new TopicListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("userId", user.getId());// 当前用户userId
				map.put("toUserId",user.getId());//当前用户toUserId 
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(cTopicService.getTopicListByUserIdForApi(map));
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
	 * 创建话题
	 */
	@ResponseBody
	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	public BaseResponse addTopic(@Valid TopicRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Long folderID = doFile(req.getList(), servletRequest,SysConfig.TOPIC_FOLDER);
				CTopic record = new CTopic();
				record.setUserId(user.getId());
				record.setTopicContent(req.getContent());
				record.setFolderId(folderID);
				record.setTopicTypeId(req.getTopicTypeId());
				record.setCreateTime(new Date());
				record.setIsDel(0);
				record.setIsTop(0);
				boolean isok = cTopicService.insertSelective(record) > 0;
				return isok ? BaseResponse.AddSuccess : BaseResponse.AddFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("创建话题出错了");
			return BaseResponse.ServerException;
		}

	}


	/**
	 * 点赞 
	 * 1.增加点赞记录
	 * 2.增加话题表点赞字段总数
	 */
	@ResponseBody
	@RequestMapping(value = "/supportOperate")
	public BaseResponse supportOperate(SupportOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				//判断该话题该用户点赞过没有
				int count=cSupportService.selectByTopicIdAndUserid(req, user);
				if(count>0){
					return new BaseResponse(1, "不能重复点赞");
				}
				boolean flag=cSupportService.supportOperate(req, user);
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
	 * 话题举报
	 * @param req
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addTopicReport",method=RequestMethod.POST)
	public BaseResponse addTopicReport(ReportOperateRequest req, Errors errors,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CTopic cTopic=cTopicService.selectByPrimaryKey(req.getTopicId());
				if(cTopic==null){
					return new BaseResponse(1, "该话题不存在！");
				}
				Users user=userInfoService.getById(cTopic.getUserId());
				if(!user.getIdentity().equals(UserType.JM)){
					return new BaseResponse(1, "官方发布的话题不允许举报！");
				}
				//判断该话题该用户举报过没有
				int count=topicReportService.selectByTopicIdAndUserid(req, user);
				if(count>0){
					return new BaseResponse(1, "不能重复举报！");
				}
				Users currentUser = getCurrentUser();
				boolean flag=topicReportService.addTopicReport(req, currentUser);
				return flag ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
		
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteTopicById",method = RequestMethod.POST)
	public BaseResponse deleteTopicById(SupportOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				boolean flag=cTopicService.deleteTopicById(req.getTopicId());
				return flag ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="/hasNewTopic",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse hasNewTopic(HasNewTopicRequest req,Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				HasNewTopicResponse res = new HasNewTopicResponse();
				Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String,Object> map = new HashMap<>();
				map.put("communityId", userInfo.getCommunity().getId());
				map.put("userId", userInfo.getId());
				res.setHasNewTopic(cTopicService.hasNewTopic(map));
				res.setUnreadList(readRecordService.getTopicUnreadList(map));
				res.setCode(0);
				res.setMsg("获取成功");
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("获得是否有新圈子内容出错:");
			return BaseResponse.ServerException;
		}
	}

}