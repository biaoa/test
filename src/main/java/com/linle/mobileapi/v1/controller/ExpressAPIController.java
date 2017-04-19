package com.linle.mobileapi.v1.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.base.BaseController;
import com.linle.common.util.PostRequestUtil;
import com.linle.communityExpress.service.CommunityExpressService;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ExpressQueryResult;
import com.linle.mobileapi.v1.request.ExpressListRequest;
import com.linle.mobileapi.v1.request.ExpressQueryRequest;
import com.linle.mobileapi.v1.request.SentRequest;
import com.linle.mobileapi.v1.response.ExpressListResponse;
import com.linle.mobileapi.v1.response.ExpressQueryResponse;
import com.linle.sentInfo.service.SentInfoService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/api/1")
public class ExpressAPIController extends BaseController {
	

	@Value("${express_url}")
	private  String url;
	
	@Value("${express_appkey}")
	private  String appkey;

	@Autowired
	private CommunityExpressService communityExpressService;
	
	@Autowired
	private SentInfoService sentInfoService;
	
	// 快递查询 ,method=RequestMethod.POST
	@ResponseBody
	@RequestMapping(value = "/expressQuery", method = RequestMethod.POST)
	public BaseResponse expressQuery(@Valid ExpressQueryRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws JsonProcessingException {
		Map<String, String> map = new HashMap<>();
		map.put("appkey", appkey);
		map.put("type", "auto");
		map.put("number", req.getNumber());
		String result = "";
		try {
			result = PostRequestUtil.doPostRequest(url, map, "utf-8", "utf-8");
			System.out.println(result);
		} catch (Exception e) {
			_logger.info("快递查询出错了");
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		ExpressQueryResponse res = new ExpressQueryResponse();
		if(!JSONObject.fromObject(result).get("status").equals("0")){
			res.setMsg("没有信息");
			res.setCode(0);
			return res;
		}
		res.setCode(0);
		res.setMsg("获得成功");
		try {
			m.setSerializationInclusion(Include.NON_NULL);
			res.setData(m.readValue(result, ExpressQueryResult.class));
		} catch (IOException e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return res;
	}
	//快递列表
	@ResponseBody
	@RequestMapping(value = "/expressList", method = RequestMethod.POST)
	public BaseResponse expressList(@Valid ExpressListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		ExpressListResponse res = new ExpressListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
			Users	userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
			res.setCode(0);
			res.setMsg("获取成功");
			res.setData(communityExpressService.getExpressList(userInfo.getCommunity().getId()));
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.info("获取快递列表出错了");
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/sent", method = RequestMethod.POST)
	public BaseResponse sent(@Valid SentRequest req, Errors errors, HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
		try {
			_logger.info("快递参数："+m.writeValueAsString(req));
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				return sentInfoService.createSentOrder(req);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.info("寄件出错了");
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper= new ObjectMapper();
		System.out.println(mapper.writeValueAsString(new BaseResponse(0,"操作成功")));
	}

}
