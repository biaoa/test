package com.linle.vote.controller;

import java.util.HashMap;
import java.util.List;
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
import com.linle.common.util.LimitUtil;
import com.linle.common.util.Page;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.AppLoadVoteListRequest;
import com.linle.mobileapi.v1.response.AppLoadVoteListResponse;
import com.linle.vote.model.Vote;
import com.linle.vote.model.VoteOptionsResponse;
import com.linle.vote.model.VoteResponse;
import com.linle.vote.model.VoteUserResponse;
import com.linle.vote.service.VoteService;
import com.linle.voteOptions.model.VoteOptions;
import com.linle.voteOptions.service.VoteOptionsService;
import com.linle.voteUser.model.VoteUser;
import com.linle.voteUser.service.VoteUserService;
/**
 * 
 * @author chenkai
 * @Description 活动投票
 */
@Controller
@RequestMapping("/vote")
public class VoteController extends BaseController {	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private VoteUserService voteUserService;
	
	
	@Autowired
	private VoteOptionsService voteOptionsService;
	
	
	@RequiresPermissions("vote_manage")
	@RequestMapping(value = "/list")
	public String list(Integer pageNo, Model model) {
		Page<Vote> page = new Page<Vote>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",getCommunity().getId());
		page.setParams(params);
		try {
			voteService.getAllData(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/vote/vote_list";
	}
	
	@RequiresPermissions(value="vote_manage")
	@RequestMapping(value="/doOpreate",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOpreate(Vote vote){
		BaseResponse res=new BaseResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				Users user=getCurrentUser();
				vote.setUserId(user.getId());
				vote.setCommunityId(getCommunity().getId());
				if(vote.getThemeId()!=null){
					res=voteService.operateVoteOptions(vote,user);
				}else{
					res=voteService.add(vote,user);
				}
			}else{
				return BaseResponse.PleaseSignIn;
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	//获得对象
	@RequiresPermissions(value="vote_manage")
	@RequestMapping(value="/getVoteById",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getVoteById(long id){
		try {
			VoteResponse res=new VoteResponse();
			Vote vote=voteService.getVoteById(id,getCurrentUser().getId());
			res.setVote(vote);
			res.setCode(0);
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	//参与投票成员
	@RequiresPermissions(value="vote_manage")
	@RequestMapping(value="/getVoteUsersById",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getVoteUsersById(long id){
		try {
			VoteUserResponse res=new VoteUserResponse();
			List<VoteUser> list=voteUserService.getVoteUsersById(id);
			res.setOptionCounts(list.size());
			res.setVoteUserList(list);
			res.setCode(0);
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	//投票选项详情
	@RequiresPermissions(value="vote_manage")
	@RequestMapping(value="/getVoteOptionsById",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getVoteOptionsById(long themeId,long optionsId){
		try {
			VoteOptionsResponse res=new VoteOptionsResponse();
			List<VoteOptions> list=voteOptionsService.getVoteOptionsById(themeId,optionsId);
			res.setVoteOptionsCounts(list.size());
			res.setVoteOptionsList(list);
			res.setCode(0);
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	@RequiresPermissions(value="vote_manage")
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(long id){
		try {
			voteService.updateIsDelById(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	
	@RequestMapping(value="/LoadVoteList",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse appLoadVoteList(AppLoadVoteListRequest req){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				AppLoadVoteListResponse res = null;
				Users user  = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				LimitUtil.limit(map, req.getPageSize(), req.getPageNuber());
				map.put("userId", user.getId());
				map.put("communityId", user.getCommunity().getId());
				res = voteService.LoadVoteList(map);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
}
