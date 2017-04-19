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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.entity.sys.Users;
import com.linle.knowledgeThumbRecord.service.KnowledgeThumbRecordService;
import com.linle.littleKnowledge.service.LittleKnowledgeService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.KnowledgeThumbRequest;
import com.linle.mobileapi.v1.request.LittleKnowledgeRequest;
import com.linle.mobileapi.v1.response.LittleKnowledgeResponse;

@Controller
@RequestMapping("/api/1")
public class LittleKnowledgeAPIController extends BaseController {

	@Autowired
	private LittleKnowledgeService knowledgeService;
	
	@Autowired
	private KnowledgeThumbRecordService ThumbRecordService;

	@RequestMapping(value = "/getLittleKnowledge", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getLittleKnowledge(LittleKnowledgeRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			LittleKnowledgeResponse res = new LittleKnowledgeResponse();
			res.setCode(0);
			res.setMsg("获取成功");
			Map<String, Object> map = new HashMap<>();
			Subject subject = SecurityUtils.getSubject();
			map.put("uid", "");
			if(subject.isAuthenticated()){
				map.put("uid", getCurrentUser().getId());
			}
			LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
			res.setData(knowledgeService.getAllForAPI(map));
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}
	
	@RequestMapping(value = "/knowledgeThumb", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse LittleKnowledgeThumb(@Valid KnowledgeThumbRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				return ThumbRecordService.addThumb(user,req.getKnowledgeId(),req.getStatus());
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.error("点赞出错了:" + e);
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(BaseResponse.OperateSuccess));
	}
}
