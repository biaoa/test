package com.linle.vote.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateKit;
import com.linle.common.util.DateUtil;
import com.linle.common.util.Page;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.response.AppLoadVoteListResponse;
import com.linle.vote.mapper.VoteMapper;
import com.linle.vote.model.Vote;
import com.linle.vote.service.VoteService;
import com.linle.voteOptions.mapper.VoteOptionsMapper;
import com.linle.voteOptions.model.VoteOptions;
import com.linle.voteUser.mapper.VoteUserMapper;
import com.linle.voteUser.service.VoteUserService;

@Service
@Transactional
public class VoteServiceImpl extends CommonServiceAdpter<Vote> implements VoteService {
	@Autowired
	private VoteMapper mapper;
	
	@Autowired
	private VoteOptionsMapper voteOptionsMapper;
	
	@Autowired
	private VoteUserMapper voteUserMapper;
	
	@Autowired
	private VoteUserService voteUserService;
	
	@Override
	public Page<Vote> getAllData(Page<Vote> page) {
		page.setPageSize(5);
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	public List<Vote> getAllDataForApi(HashMap<Object, Object> map) {
		return mapper.getAllDataForApi(map);
	}
	
	public List<Vote> getAllDataByStatus(Map<String, Object> map) {
		return mapper.getAllDataByStatus(map);
	}
	
	public List<Vote> getRemindVoteList(Map<String, Object> map) {
		return mapper.getRemindVoteList(map);
	}
	
	
	@Override
	public BaseResponse operateVoteOptions(Vote vote,Users user){
		try {
			//1.
			Map<String, Object> map=DateUtil.compareDateForVote(vote.getStartDate(),vote.getEndDate());
			int i=(int) map.get("i");
			int m=(int) map.get("m");
			if(i==-1){//当前时间小于开始时间
				vote.setStatus(0);//未开始
			}else if(i==1&&m==-1){//当前时间大于开始时间 并且 当前时间小于结束时间
				vote.setStatus(1);//进行中
			}else{// 当前时间大于结束时间
				vote.setStatus(2);//已结束
			}
			mapper.updateByPrimaryKeySelective(vote);
			//2.插入投票选项表
			String optionsContents=vote.getOptionsContents();
			String[] content_array=optionsContents.split("@;");
			//先删除该活动所有投票选项
			if(content_array.length>0){
				voteOptionsMapper.deleteByThemeId(vote.getThemeId());
			}
			for (int j = 0; j < content_array.length; j++) {
				VoteOptions record=new VoteOptions();
				record.setContent(content_array[j]);
				record.setThemeId(vote.getThemeId());
				voteOptionsMapper.insert(record);
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
		return BaseResponse.OperateSuccess;
	}
	
	public BaseResponse add(Vote vote,Users user){
		try {
			//插入投票主题表
			vote.setCreateDate(new Date());
			Map<String, Object> map=DateUtil.compareDateForVote(vote.getStartDate(),vote.getEndDate());
			int i=(int) map.get("i");
			int m=(int) map.get("m");
			if(i==-1){//当前时间小于开始时间
				vote.setStatus(0);//未开始
			}else if(i==1&&m==-1){//当前时间大于开始时间 并且 当前时间小于结束时间
				vote.setStatus(1);//进行中
			}else{// 当前时间大于结束时间
				vote.setStatus(2);//已结束
			}
			int count=mapper.insert(vote);
			if(count>0){
				//2.插入投票选项表
				String optionsContents=vote.getOptionsContents();
				String[] content_array=optionsContents.split("@;");
				for (int j = 0; j < content_array.length; j++) {
					VoteOptions record=new VoteOptions();
					record.setContent(content_array[j]);
					record.setThemeId(vote.getThemeId());
					voteOptionsMapper.insert(record);
				}
			}else{
				return new BaseResponse(1,"创建投票记录出错");
			}
			
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
		
		return BaseResponse.OperateSuccess;
	}
	
	public Vote getVoteById(Long id,long userId) {
		HashMap<Object, Object> map=new HashMap<Object, Object>();
		map.put("themeId", id);
		Vote vote=mapper.getVoteById(map);
		//计算选项百分比
		List<VoteOptions> list=vote.getVoteOptionsList();
		long voteCount=vote.getVoteCount();
		for (VoteOptions voteOptions : list) {
			long optionsCount=voteOptions.getOptionsCount();
			double percent = optionsCount/(double)voteCount;
			voteOptions.setPercent(percent*100);
			if(voteOptions.getVoteUsers()!=null&&voteOptions.getVoteUsers().contains(","+userId+",")){
				voteOptions.setIsVoteOptions(1);//是否投票过该选项
			}
		}
		vote.setCreateDateStr(DateKit.friendlyFormat(vote.getCreateDate()));
	    return vote;
	}
	 
	//删除
	@Override
	public void updateIsDelById(long id) {
		mapper.updateIsDelById(id);
	}

	@Override
	public AppLoadVoteListResponse LoadVoteList(Map<String, Object> map) {
		AppLoadVoteListResponse res = new AppLoadVoteListResponse();
		res.setCode(0);
		res.setMsg("获取成功");
		res.setResultCount(mapper.getVoteCountByCommunityId(map.get("communityId")));
		List<Vote> list = mapper.LoadVoteList(map);
		Map<String, Object> params = new HashMap<>();
		for (Vote vote : list) {
			params.put("themeId", vote.getThemeId());
			params.put("userId",map.get("userId"));
			long userVoteCount=voteUserService.selectCountByThemeIdAndUserId(params);
			//当投票设置为投票权限为区别居住地址投票
			if(userVoteCount==0&&vote.getVotePrvlg()==0){
				//判断该活动是否有相同房号地址投过票
				if(map.get("addressDetails")!=null&&!"".equals(map.get("addressDetails"))){
					map.put("themeId", vote.getThemeId());
					map.put("addressDetails", map.get("addressDetails"));
					long voteUserAddressCount=voteUserService.selectCountByThemeIdAndAddressDetails(map);
					//有相同房号地址投过票，则当前用户也不能投票（场景：相同房号地址，有多个人注册）
					if(voteUserAddressCount>0){
						userVoteCount=1;
					}
				}
			}
			vote.setUserVoteCount(userVoteCount);
			vote.setCreateDateStr(DateKit.friendlyFormat(vote.getCreateDate()));
//			System.out.println(map.get("userId")+"是否投票过："+vote.getContent()+"--"+count);
		}
		res.setList(list);
		return res;
	}
	
	@Override
	public void updateStatusById(Map<String, Object> map) {
		try {
			mapper.updateStatusById(map);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
	}
	@Override
	public void updatePushFlgById(long id) {
		try {
			mapper.updatePushFlgById(id);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
	}
	
}
