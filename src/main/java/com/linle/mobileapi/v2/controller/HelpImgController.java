package com.linle.mobileapi.v2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.communitySuggestions.service.CommunitySuggestionsService;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.entity.sys.Users;
import com.linle.event.SystemMsgEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.CommunitySuggestionsRequest;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;



@Controller
@RequestMapping("/api/2")

public class HelpImgController extends BaseController {

	@Autowired
	private CommunitySuggestionsService suggestionsService;

	/**
	 * 
	 * @Description 小区居民提交反馈信息2
	 * 
	 */
	@RequestMapping(value = "postAdviceToManagement", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addSuggestions2(CommunitySuggestionsRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user  = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				CommunitySuggestions Suggestions = new CommunitySuggestions();
				Suggestions.setUserId(user.getId());
				Suggestions.setCommunityId(getUserCommunity().getId());
				Suggestions.setCreateDate(new Date());
				Suggestions.setAdvice(req.getAdvice());
				if(null!=req.getFolderId()){
					Suggestions.setFolderId(req.getFolderId());
				}
				suggestionsService.insertSelective(Suggestions);
				BaseWebSocketMsgDataVo msgDate = new BaseWebSocketMsgDataVo();
				msgDate.setTilte("意见反馈提醒");
				msgDate.setBody("用户:"+user.getAddressDetails().getOverall()+"提交了新的意见反馈，请及时查看");
				msgDate.setUrl("/communitySuggestions/list");
				WebSocketMsg msg = new WebSocketMsg();
				msg.setFrom((long)1);
				msg.setTo(user.getCommunity().getUser().getId());
				msg.setObj(m.writeValueAsString(msgDate));
				msg.setSendDate(new Date());
				if(user.getCommunity().getPresident()!=null){
					//如果社长信息存在
					msgDate.setBody(user.getCommunity().getName()+"用户:"+user.getAddressDetails().getOverall()+"提交了报修提醒，请及时查看");
					msg.setObj(m.writeValueAsString(msgDate));
					List<Long> tos = new ArrayList<Long>();
					tos.add(msg.getTo());
					tos.add(user.getCommunity().getPresident().getUserId()); //获得小区的社长id
					msg.setTos(tos);
				}
				applicationContext.publishEvent(new SystemMsgEvent(msg.getTo().toString(),msg));
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
}
