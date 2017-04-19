package com.linle.mobileapi.v2.controller;

import java.util.Date;

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
import com.linle.common.base.BaseController;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.TopicRequest;
import com.linle.topicUserManager.model.TopicUserManager;
import com.linle.topicUserManager.service.TopicUserManagerService;

@Controller
@RequestMapping("/api/2")
public class TopicAPIController extends BaseController {

	@Autowired
	private CTopicService cTopicService;
	
	@Autowired
	private TopicUserManagerService topicUserManagerService;
	
	/**
	 * 创建话题
	 */
	@ResponseBody
	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	public BaseResponse addTopic2(@Valid TopicRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
//				Long folderID = doFile(req.getList(), servletRequest,SysConfig.TOPIC_FOLDER);
				//判断是否能创建话题
				TopicUserManager topicUserManager =topicUserManagerService.selectByUserIdAndTopicTypeId(user.getId(), req.getTopicTypeId(), "publish");
				if(topicUserManager!=null){
					return new BaseResponse(1,"不能发布话题");
				}
				CTopic record = new CTopic();
				record.setUserId(user.getId());
				record.setTopicContent(req.getContent());
				if(req.getFolderId()!=null){
					record.setFolderId(req.getFolderId());
				}
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

}