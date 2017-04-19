package com.linle.communitySuggestions.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.communitySuggestions.service.CommunitySuggestionsService;
import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;
import com.linle.communitySuggestionsComment.service.CommunitySuggestionsCommentService;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.entity.sys.Users;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;

/**
 * 
 * @author pangd
 * @Description 小区的意见反馈
 */
@Controller
@RequestMapping("/communitySuggestions")
@RequiresPermissions("commodity_feedback_manage")
public class CommunitySuggestionsController extends BaseController {
	
	@Autowired
	private CommunitySuggestionsService communitySuggestionsService;
	@Autowired
	private CommunitySuggestionsCommentService communitySuggestionsCommentService;
	
	@RequestMapping(value="/list")
	public String list(Model model,Integer pageNo, String owner,String roomno,String beginDate,String endDate){
		Page<CommunitySuggestions> page = new Page<CommunitySuggestions>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		if(StringUtil.isNotNull(beginDate)){
			params.put("beginDate", beginDate);
		}
		if (StringUtil.isNotNull(endDate)) {
			params.put("endDate", endDate);
		}
		params.put("owner", owner);
		params.put("roomno", roomno);
		params.put("communityId", getCommunity().getId());
		page.setParams(params);
		try {
			communitySuggestionsService.findAllCommunitySuggestions(page);
			model.addAttribute("pagelist", page);
			model.addAttribute("owner", owner);
			model.addAttribute("roomno", roomno);
			model.addAttribute("beginDate", beginDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/communitySuggestions/feedback";
	}
	
	/**
	 * 留言
	 * @return
	 */
	@RequestMapping(value="/commentSuggestions")
	@ResponseBody
	public BaseResponse commentSuggestions(long suggestionsId,String content){
		try {		
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				Users user=getCurrentUser();
				//1.留言
				CommunitySuggestionsComment record=new CommunitySuggestionsComment();
				record.setSuggestionsId(suggestionsId);
				record.setContent(content);
				record.setCreateTime(new Date());
				record.setIsDel(0);
				record.setUserId(user.getId());
				boolean flag=communitySuggestionsCommentService.insertSelective(record)>0?true:false;
				CommunitySuggestions communitySuggestions=communitySuggestionsService.selectByPrimaryKey(suggestionsId);
				
				//2.物业留言用户，则该条记录改为处理中
				Map<String,Object> map = new HashMap<>();
				map.put("id", suggestionsId);
				map.put("status", 1);
				communitySuggestionsService.updateStatus(map);
				
				//3.推送
				String msg="小区物业留言了你："+content;
				PushBean pushBean = new PushBean();
				pushBean.setRefId(suggestionsId+"");
				pushBean.setType(PushType.COMMUNITYSUGGESTIONS_MSG);//物业留言住户
				pushBean.setContent(msg);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", communitySuggestions.getUserId()+"",PushFrom.LINLE_USER));
				return flag==true?BaseResponse.AddSuccess:BaseResponse.OperateFail;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 获取某记录的留言列表
	 */
	
	@RequestMapping(value="/selectCommentListById",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg selectCommentListById(long suggestionsId){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("suggestionsId", suggestionsId);
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			paramsMap.put("commentlist", communitySuggestionsCommentService.selectCommentListById(map));
			return new ResponseMsg(0,"获取成功",paramsMap);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateStatus")
	public BaseResponse updateStatus(Long rid,int status)  {
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("id", rid);
			map.put("status", status);
			return communitySuggestionsService.updateStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	//删除
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(Long id){
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				CommunitySuggestions communitySuggestions = communitySuggestionsService.selectByPrimaryKey(id);
				if(communitySuggestions!=null &&communitySuggestions.getCommunityDelFalg()==0){
					communitySuggestionsService.communityDel(communitySuggestions);
					return BaseResponse.OperateSuccess;
				}
				return new BaseResponse(1, "该记录不存在,请刷新页面重试");
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("小区删除意见反馈出错:"+e);
			return BaseResponse.ServerException;
		}
	}
}
