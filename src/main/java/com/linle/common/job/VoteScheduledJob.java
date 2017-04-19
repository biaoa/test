package com.linle.common.job;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.util.DateUtil;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.user.service.UserInfoService;
import com.linle.vote.model.Vote;
import com.linle.vote.service.VoteService;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;

@Component
public class VoteScheduledJob implements Serializable {

	public ObjectMapper m = new ObjectMapper();

	private static final long serialVersionUID = -4786565172934239371L;

	private Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VoteService voteService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	protected ApplicationContext applicationContext;
	
	
	/**
	 * 查询除了已结束所有投票
	 * 若当前时间超过了开始时间，则将状态修改为进行中
	 * 若当前时间超过了结束时间，则将状态修改为已结束
	 * 每5分钟执行一次
	 */
//	@Scheduled(cron = "0 0/5 * * * ? ")
	public void updateVoteStatus() throws AuthenticationException, InvalidRequestException, APIConnectionException,
			APIException, ChannelException, JsonProcessingException {
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("status", 2);
		List<Vote> list=voteService.getAllDataByStatus(p);
		if (!list.isEmpty()) {
			for (Vote vote : list) {
				Map<String, Object> map=DateUtil.compareDateForVote(vote.getStartDate(),vote.getEndDate());
				int i=(int) map.get("i");
				int m=(int) map.get("m");
				int oldStatus=vote.getStatus();
				int status=0;
				if(i==-1){//当前时间小于开始时间
					status=0;;//未开始
				}else if(i==1&&m==-1){//当前时间大于开始时间 并且 当前时间小于结束时间
					status=1;//进行中
				}else{// 当前时间大于结束时间
					status=2;//已结束
				}
				//修改状态
				if(status!=0&&oldStatus!=status){
					Map<String, Object> param=new HashMap<String, Object>();
					param.put("id", vote.getThemeId());
					param.put("status", status);
					voteService.updateStatusById(param);
				}
			}
		}
		
	}

	
	/**
	 * 投票结束时间之前xx时间会提醒推送用户投票
	 * 
	 * 条件：状态=进行中 ，设置了提醒时间，当前时间小于结束时间 ,未推送过的，本小区活动投票推送本小区用户
	 * 只推送一次，根据vote表push_flg字段判断
	 * 每20分钟执行一次
	 */
//	@Scheduled(cron = "0 0/20 * * * ? ")
	public void voteRemindByEndDate()  {
		try {
			Map<String, Object> p=new HashMap<String, Object>();
			List<Vote> list=voteService.getRemindVoteList(p);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			List<String> toUserIds = new ArrayList<>();
			if (!list.isEmpty()) {
				for (Vote vote : list) {
					float remindTime=vote.getRemindTime();
					String endDateStr=vote.getEndDate();
					String currentDateStr=sdf.format(new Date());
					Date currentDate=sdf.parse(currentDateStr);//当前时间
					Date endDate=sdf.parse(endDateStr);//结束时间
					
					int	remindTimeMinutes=(int) (remindTime*60);//提醒时间转换为分钟格式
					//计算他们之间时间差
					long l=endDate.getTime()-currentDate.getTime();
					//计算相差多少分钟
					int diffMinute=(int) (l/1000.0/60);
					if(remindTimeMinutes>=diffMinute){//
						//获得该投票主题该小区没有投过票所有用户
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("themeId", vote.getThemeId());
						map.put("communityId", vote.getCommunityId());
						toUserIds = userInfoService.getUserIdByCommunityIdAndThemeId(map);
						if(toUserIds.size()>0){
							//推送
							PushBean pushBean = new PushBean();
							pushBean.setType(PushType.VOTE_MSG);
							pushBean.setRefId(vote.getThemeId()+"");//投票id
							pushBean.setContent("投票即将截止："+vote.getContent());
							String[] array = new String[toUserIds.size()];
							String[] toUserIdsArr=toUserIds.toArray(array);
							applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUserIdsArr,PushFrom.LINLE_USER));
							//标记该投票已推送
							voteService.updatePushFlgById(vote.getThemeId());
						}
					}
				
				}
				System.out.println("提醒推送用户投票执行成功,推送用户="+toUserIds);
			}
			
		
		} catch (Exception e) {
			_logger.debug("提醒推送用户投票执行失败");
			e.printStackTrace(); _logger.error("出错了", e);
		}
	}
	
	/**
	 * 测试推送方法
	 */
//	@Scheduled(cron = "0 0/1 * * * ? ")
/*	@Scheduled(cron="0 0/1 * * * ? ")   //每5秒执行一次  
	public void testVote()  {
		//推送
		PushBean pushBean = new PushBean();
		pushBean.setType(PushType.VOTE_MSG);
		pushBean.setRefId(49+"");//投票id
		pushBean.setContent("投票即将截止：测试推送aaaaa");
		List<String> toUserIds = new ArrayList<>();
		toUserIds.add("4307");
		String[] array = new String[toUserIds.size()];
		String[] toUserIdsArr=toUserIds.toArray(array);
		applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUserIdsArr,PushFrom.LINLE_USER));
	}*/
}
