package com.linle.littleKnowledge.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.LittleKnowledge;
import com.linle.entity.sys.Users;
import com.linle.event.KnowledgeAccessRecordEvent;
import com.linle.littleKnowledge.service.LittleKnowledgeService;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description 邻乐速报
 */
@Controller
@RequestMapping("/littleKnowledge")
public class LittleKnowledgeController extends BaseController {
	
	@Autowired
	private LittleKnowledgeService knowledgeService;
	
	@RequiresPermissions("knowledge_list")
	@RequestMapping("/list")
	public String list(Integer pageNo,Model model,String title,Integer flag){
		try {
			try {
				if(StringUtil.isNotNull(title)){
					title=new String(title.getBytes("iso8859-1"),"utf-8");
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			Page<LittleKnowledge> page = new Page<LittleKnowledge>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("title", title);
			params.put("flag", flag);
			if (pageNo != null) {
				page.setPageNo(pageNo);
			}
			page.setParams(params);
			knowledgeService.getALLknowledgeService(page);
			model.addAttribute("title", title);
			model.addAttribute("flag", flag);
			model.addAttribute("pagelist", page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return "littleKnowledge/littleKnowledge_list";
	}
	
	@RequiresPermissions("knowledge_list")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String toadd(Long id,Model model){
		LittleKnowledge knowledge = new LittleKnowledge();
		if(id!=null){
			knowledge = knowledgeService.selectByPrimaryKey(id);
		}
		
		model.addAttribute("knowledge", knowledge);
		return "littleKnowledge/littleKnowledge_add";
	}
	
	@RequiresPermissions("knowledge_list")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(LittleKnowledge knowledge){
		try {
			if(knowledge.getId()!=null){
				knowledge.setUpdateDate(new Date());
				knowledgeService.updateByPrimaryKeySelective(knowledge);
				return BaseResponse.OperateSuccess;
			}
			knowledge.setDelFlag(0);
			knowledge.setCreateDate(new Date());
			knowledge.setUserId(getCurrentUser().getId());
			knowledgeService.insertSelective(knowledge);
			return BaseResponse.AddSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//预览
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id,Model model,String sid,HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			LittleKnowledge knowledge  = knowledgeService.selectByPrimaryKey(id);
			model.addAttribute("knowledge", knowledge);
			
			try {
				Device device = getDevice(servletRequest);
				if(device!=null && !device.isNormal()){
					Long userid = null,communityId =null;
					if (StringUtil.isNotNull(sid)) {
						processSidCookie(servletRequest, servletResponse, sid);
						Subject subject = SecurityUtils.getSubject();
						if (subject.isAuthenticated()) {
							Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
							userid = userInfo.getId();
							communityId = userInfo.getCommunity().getId();
						}
					}
					// 只要不是PC端访问的appBanner都插入访问量表中;
					applicationContext.publishEvent(new KnowledgeAccessRecordEvent(id, userid,communityId));
				}
			} catch (Throwable e) {
				e.printStackTrace(); _logger.error("出错了", e);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return "error/500";
		}
		return "littleKnowledge/littleKnowledge_detail";
	}
	
	@RequiresPermissions("knowledge_list")
	@RequestMapping(value="setShow",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse setShow(LittleKnowledge knowledge){
		try {
			knowledgeService.updateByPrimaryKeySelective(knowledge);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("knowledge_list")
	@RequestMapping(value="del")
	@ResponseBody
	public BaseResponse del(Long id){
		try {
			knowledgeService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
	}
	
}
