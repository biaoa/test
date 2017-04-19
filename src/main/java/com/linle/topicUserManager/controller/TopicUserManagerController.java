package com.linle.topicUserManager.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.cTopicType.service.CTopicTypeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.topicUserManager.model.TopicUserManager;
import com.linle.topicUserManager.service.TopicUserManagerService;

/**
 * 
 * @Description: 圈子USER管理
 * @author chenkai
 *
 */
@Controller
@RequestMapping("topicUserManager")
public class TopicUserManagerController extends BaseController{

	@Autowired
	private TopicUserManagerService topicUserManagerService;
	@Autowired
	private CTopicTypeService cTopicTypeService;
	// 列表
	@RequiresPermissions("topicUserManager_list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model,String topicTypeId,String userName) {
		Page<TopicUserManager> page = new Page<TopicUserManager>();
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
		params.put("topicTypeId", topicTypeId);
		params.put("userName", userName);
		page.setParams(params);
		try {
			topicUserManagerService.getAllForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		model.addAttribute("userName", userName);
		model.addAttribute("topicTypeId", topicTypeId);
		model.addAttribute("tipicTypeList", cTopicTypeService.getAllType());
		return "/topicUserManager/topicUserManager_list";
	}

	@RequiresPermissions("topicUserManager_list")
	@RequestMapping(value = "/del/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse delTopicType(@PathVariable Long id) {
		try {
			TopicUserManager record=new TopicUserManager();
			record.setId(id);
			record.setIsDel(1);
			topicUserManagerService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("topicUserManager_list")
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(TopicUserManager topicUserManager) {
		try {
//			topicUserManager.setIsPublish(0);
//			topicUserManager.setIsReply(0);
			topicUserManagerService.updateByPrimaryKeySelective(topicUserManager);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//selectById
	@RequestMapping(value = "/selectById",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg selectById(TopicUserManager topicUserManager) {
		try {
			TopicUserManager obj=topicUserManagerService.selectById(topicUserManager.getUserId());
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("topicUserManager", obj);
			return new ResponseMsg(0, "获取成功", paramsMap);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1, "获取失败", null);
		}
	}
	//禁用发布
	@RequestMapping(value = "/updateIsPublish",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateIsPublish(TopicUserManager topicUserManager) {
		try {
			TopicUserManager obj=topicUserManagerService.selectByUserIdAndTopicTypeId(topicUserManager.getUserId(),
					topicUserManager.getTopicTypeId(), "");
			if(obj!=null){
				if(obj.getIsPublish()==1){
//					if(topicUserManager.getTopicTypeId()==obj.getTopicTypeId()){
//						return new BaseResponse(1, "该用户已禁用话题发布");
//					}
//					else{//更改禁用话题类型
//						obj.setTopicTypeId(topicUserManager.getTopicTypeId());
//						topicUserManagerService.updateByPrimaryKeySelective(obj);
//					}
					return new BaseResponse(1, "该用户已禁用话题发布");
				}else{
					obj.setIsPublish(1);
					topicUserManagerService.updateByPrimaryKeySelective(obj);
				}
			}else{
				topicUserManager.setCreateTime(new Date());
				topicUserManager.setIsDel(0);
//				topicUserManager.setCommTopicTypeId(0l);
//				topicUserManager.setIsReply(0);
				topicUserManagerService.insertSelective(topicUserManager);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//禁用回复
	@RequestMapping(value = "/updateIsReply",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateIsReply(TopicUserManager topicUserManager) {
		try {
//			TopicUserManager obj=topicUserManagerService.selectById(topicUserManager.getUserId());
			TopicUserManager obj=topicUserManagerService.selectByUserIdAndTopicTypeId(topicUserManager.getUserId(),
					topicUserManager.getCommTopicTypeId(), "");
			if(obj!=null){
				if(obj.getIsReply()!=null&&obj.getIsReply()==1){
//					if(topicUserManager.getCommTopicTypeId()==obj.getCommTopicTypeId()){
//						return new BaseResponse(1, "该用户已禁用话题回复");
//					}else{//更改禁用话题类型
//						obj.setCommTopicTypeId(topicUserManager.getCommTopicTypeId());
//						topicUserManagerService.updateByPrimaryKeySelective(obj);
//					}
					return new BaseResponse(1, "该用户已禁用话题回复");
				}else{
					obj.setIsReply(1);
					topicUserManagerService.updateByPrimaryKeySelective(obj);
				}
			}else{
				topicUserManager.setCreateTime(new Date());
				topicUserManager.setIsDel(0);
				topicUserManagerService.insertSelective(topicUserManager);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
