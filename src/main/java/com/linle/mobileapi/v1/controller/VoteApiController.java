package com.linle.mobileapi.v1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.AppLoadVoteListRequest;
import com.linle.mobileapi.v1.response.AppLoadVoteListResponse;
import com.linle.vote.model.Vote;
import com.linle.vote.service.VoteService;
import com.linle.voteOptions.model.VoteOptions;
import com.linle.voteOptions.service.VoteOptionsService;
import com.linle.voteUser.model.VoteUser;
import com.linle.voteUser.service.VoteUserService;
/**
 * 
 * @author chenkai
 * @Description 活动投票APP接口
 */
@Controller
@RequestMapping("/api/1")
public class VoteApiController extends BaseController {	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private VoteUserService voteUserService;
	
	
	@Autowired
	private VoteOptionsService voteOptionsService;
	
	
	//投票列表界面
	@RequestMapping(value = "/getAppVoteList")
	public String getAppVoteList(String sid, HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Model model) throws Throwable {
		try {
			 processSidCookie(servletRequest, servletResponse, sid);
			 validatorSid(sid);
//			 Subject subject = SecurityUtils.getSubject();
//			 if (subject.isAuthenticated()) {
////				Users user = getCurrentUser();
//				HashMap<Object, Object> map = new HashMap<Object, Object>();
//				map.put("communityId", getCommunity().getId());//小区
//				List<Vote> list=voteService.getAllDataForApi(map);
//				model.addAttribute("voteList", list);
//			 }	
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return "error/500";
		}
		
		return "/vote/app/app_vote_list";
	}
	
	//加载列表数据
	@RequestMapping(value="/LoadVoteList",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse appLoadVoteList(AppLoadVoteListRequest req){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				AppLoadVoteListResponse res = null;
				Users user  =  userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> map = new HashMap<>();
				LimitUtil.limit(map, req.getPageSize(), req.getPageNuber());
				map.put("userId", user.getId());
				map.put("communityId", user.getCommunity().getId());
				map.put("addressDetails", user.getAddressDetails());
				res = voteService.LoadVoteList(map);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//手机端通知栏点击--》新增投票界面
	@RequestMapping(value = "/toAddVoteUser",method = RequestMethod.GET)
	public String toAddVoteUser(long themeId,String sid, HttpServletRequest servletRequest, HttpServletResponse servletResponse,Model model) {
		 try {
			processSidCookie(servletRequest, servletResponse, sid);
			validatorSid(sid);
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = getCurrentUser();
				Vote vote=voteService.getVoteById(themeId,user.getId());
				model.addAttribute("vote", vote);
				if(vote.getStatus()==2){//已结束，跳转到查看详情界面
					return "/vote/app/view_vote";
				}
			}
		} catch (Throwable e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return "error/500";
		}
		return "/vote/app/addVoteUser";
	}
	
	//新增投票界面
	@RequestMapping(value = "/addVoteUser")
	public String addVoteUser(long themeId,Model model) {
		try {
			Users user = getCurrentUser();
			Vote vote=voteService.getVoteById(themeId,user.getId());
			model.addAttribute("vote", vote);
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return "error/500";
		}
		
		return "/vote/app/addVoteUser";
	}
	
	// 查看投票详情   1.已结束，2.投过票的
	@RequestMapping(value="/getAppVoteById")
	public String getAppVoteById(long themeId,Model model){
		try {
			Users user = getCurrentUser();
			Vote vote=voteService.getVoteById(themeId,user.getId());
			model.addAttribute("vote", vote);
			} catch (Exception e) {
				e.printStackTrace(); 
				_logger.error("出错了", e);
				return "error/500";
			}
		return "/vote/app/view_vote";
	}
	
	//h5 查看投票参与成员
	@RequestMapping(value="/getAppVoteUsersById")
	public String getAppVoteUsersById(long themeId,Model model){
	try {
		List<VoteUser> list=voteUserService.getVoteUsersById(themeId);
		model.addAttribute("voteUserList", list);
		model.addAttribute("optionCounts", list.size());
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return "error/500";
		}
		return "/vote/app/view_voteUsers";
	}
	
	//h5 投票选项详情
	@RequestMapping(value="/getAppVoteOptionsById")
	public String getAppVoteOptionsById(long themeId,long optionsId,Model model){
		try {
			List<VoteOptions> list=voteOptionsService.getVoteOptionsById(themeId,optionsId);
			model.addAttribute("voteContent", list.get(0).getContent());
			model.addAttribute("voteOptionsCounts", list.size());
			model.addAttribute("voteOptionsList", list);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return "error/500";
		}
		
		return "/vote/app/view_voteOptions";
		
	}
	
	//h5   插入投票
	@RequestMapping(value="/saveVoteUser")
	@ResponseBody
	public BaseResponse saveVoteUser(long themeId,String optionsIds){
		try {
			Subject subject = SecurityUtils.getSubject();
			Users user  = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
			Map<String, Object> map=new HashMap<>();
			map.put("themeId", themeId);
			map.put("userId", user.getId());
			long voteUserCount=voteUserService.selectCountByThemeIdAndUserId(map);
		
			if(voteUserCount>0){
				return new BaseResponse(1, "已经投过票，不能重复投票");
			}else{
				//当投票设置为投票权限为区别居住地址投票
				Vote vote=voteService.getVoteById(themeId,user.getId());
				if(vote.getVotePrvlg()==0){
					if(null!=user.getAddressDetails()){
						map.put("addressDetails", user.getAddressDetails());
						long voteUserAddressCount=voteUserService.selectCountByThemeIdAndAddressDetails(map);
						if(voteUserAddressCount>0){
							return new BaseResponse(1, "有相同房号地址投过票");
						}
					}
				}else if(vote.getStatus()==2){
					return new BaseResponse(1, "投票已结束");
				}
				
				String[] optionsIds_array=optionsIds.split(",");
				for (int j = 0; j < optionsIds_array.length; j++) {
						VoteUser record=new VoteUser();
						record.setThemeId(themeId);
						record.setOptionsId(Long.parseLong(optionsIds_array[j]));
						record.setUserId(user.getId());
						record.setCreateDate(new Date());
						voteUserService.insert(record);
				}
			}
			return new BaseResponse(0, "投票成功");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}

}
