package com.linle.cTopic.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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

import com.linle.cSupport.service.CSupportService;
import com.linle.cTopic.service.CTopicService;
import com.linle.cTopicComment.service.CTopicCommentService;
import com.linle.cTopicType.service.CTopicTypeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.community.service.CommunityService;
import com.linle.disableUser.model.DisableUser;
import com.linle.disableUser.service.DisableUserService;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CSupport;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.CTopicComment;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.topicReport.model.CTopicReport;
import com.linle.topicReport.service.TopicReportService;

/**
 * 
 * @Description: 圈子管理
 * @author chenkai
 * @date 2016年8月29日
 *
 */
@Controller
@RequestMapping("topic")
public class TopicController extends BaseController {

	@Autowired
	private CTopicService cTopicService;
	@Autowired
	private CTopicTypeService cTopicTypeService;
	@Autowired
	private CTopicCommentService cTopicCommentService;
	
	@Autowired
	private CSupportService cSupportService;
	
	@Autowired
	private CommunityService communityService;
	
	
	@Autowired
	private TopicReportService topicReportService;
	// 列表
	@RequiresPermissions("topic_list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model,String communityId,String userTypeId,
			String topicTypeId,String userName,String beginDate,String endDate,String isReport) {
		
		Page<CTopic> page = new Page<CTopic>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		if (userName != null&&!"".equals(userName)) {
			try {
				userName = new String(userName.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace(); _logger.error("出错了", e);
			}
		}
		Users user=getCurrentUser();
		params.put("identityId", user.getIdentity().getCode());
		if(user.getIdentity()==UserType.XQ){//小区只能看自己小区的
			params.put("communityId", getCommunity().getId());
			model.addAttribute("communityList", null);
		}else{
			params.put("communityId", communityId);
			model.addAttribute("communityList",  communityService.selectAllCommunity());
		}
		
		params.put("isReport", isReport);
		params.put("userTypeId", userTypeId);
		params.put("topicTypeId", topicTypeId);
		params.put("userName", userName);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		page.setParams(params);
		try {
			cTopicService.getAllTopicForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("communityId", communityId);
		model.addAttribute("userTypeId", userTypeId);
		model.addAttribute("topicTypeId", topicTypeId);
		model.addAttribute("userName", userName);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("isReport", isReport);
		
		
		model.addAttribute("tipicTypeList", cTopicTypeService.getAllType());
		model.addAttribute("pagelist", page);
		return "/topic/topic_list";
	}

	//toAdd
	@RequiresPermissions("topic_list")
	@RequestMapping(value = "/toAddTopic", method = RequestMethod.GET)
	public ModelAndView toAddTopic(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		CTopic 	topic=new CTopic();
		if(id!=null){
			topic=cTopicService.selectByPrimaryKey(id);
		}
		model.addAttribute("typeList", cTopicTypeService.getAllType());
		model.addAttribute("topic", topic);
		mv.setViewName("topic/topic_add");
		return mv;
	}

	//发布话题
	@RequiresPermissions("topic_list")
	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addTopicType(@Valid CTopic topic, BindingResult errors, Model model) {
		BaseResponse res=new BaseResponse();
		try {
			if(topic.getTopicId()==null){
				res=cTopicService.add(topic,getCurrentUser());
			}else{
				cTopicService.updateByPrimaryKeySelective(topic);
				return BaseResponse.OperateSuccess;
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}
	
	//删除话题
	@RequiresPermissions("topic_list")
	@RequestMapping(value = "/delTopic/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delTopicType(@PathVariable Long id) {
		try {
			boolean flag=cTopicService.deleteTopicById(id);
			return flag ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 
	 * @Description  置顶/取消置顶
	 * $
	 */
	@RequiresPermissions(value="topic_list")
	@RequestMapping(value="/setTop",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse setTop(CTopic topic){
		try {
			cTopicService.updateByPrimaryKeySelective(topic);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//获得话题详情，评论与回复内容
	@RequiresPermissions(value="topic_list")
	@RequestMapping(value="/getTopicDetail",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg getTopicDetail(long topicId){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("topicId", topicId);
			map.put("needDetails",0);
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			paramsMap.put("commentlist", cTopicCommentService.getTopicDetailForApi(map));
			return new ResponseMsg(0,"获取成功",paramsMap);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
	}
	
	//新增评论/回复
	@RequiresPermissions(value="topic_list")
	@RequestMapping(value="/addTopicComment",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg addTopicComment(CTopicComment topicComment){
		try {
			CTopic topic=cTopicService.selectByPrimaryKey(topicComment.getTopicId());
			if(topic.getIsDel()==1){//判断该话题是否删除
				return new ResponseMsg(1,"该话题已删除",null);
			}
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			paramsMap.put("comment", cTopicCommentService.addTopicComment(topicComment, getCurrentUser()));
			return new ResponseMsg(0,"评论成功",paramsMap);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"评论失败",null);
		}
	}
	
	//修改点赞数
	@RequiresPermissions(value="topic_list")
	@RequestMapping(value="/updateSupportNum",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateSupportNum(long topicId,long supportNum){
		try {
			CTopic topic=new CTopic();
			topic.setTopicId(topicId);
			topic.setSupportNum(supportNum);
			cTopicService.updateByPrimaryKeySelective(topic);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//获得点赞用户
	@RequiresPermissions(value="topic_list")
	@RequestMapping(value="/selectSupportUsers",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg selectSupportUsers(long topicId){
		try {
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			List<CSupport> list=cSupportService.selectSupportUsers(topicId);
			paramsMap.put("list", list);
			paramsMap.put("sumCounts",list.size() );
			return new ResponseMsg(0,"获取成功",paramsMap);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
	}
	
	//获得举报用户
	@RequiresPermissions(value="topic_list")
	@RequestMapping(value="/selectReportUsers",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg selectReportUsers(long topicId){
		try {
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			List<CTopicReport> list=topicReportService.selectReportUsers(topicId);
			paramsMap.put("list", list);
			paramsMap.put("sumCounts",list.size() );
			return new ResponseMsg(0,"获取成功",paramsMap);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
	}

	//禁用用户
	@RequestMapping(value = "/userStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg userStatus( Long uid, int status,String reason) {
		if(uid!=null){
			Users user = new Users();
			user.setId(uid);
			user.setStatus(UserStatusType.values()[status]);
			user.setReason(reason);
			userInfoService.modifyUserStatus(user);
			return new ResponseMsg(0, "操作成功", null);
		}
		return new ResponseMsg(1, "操作失败", null);
	}
}
