package com.linle.common.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.common.util.DateUtil;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.socket.SystemWebSocketHandler;
import com.linle.socket.msg.MsgService;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.socket.msg.service.WebSocketMsgService;
import com.linle.system.service.RedisManager;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;

/**
 * 
 * @author pangd
 * @Description 物业相关的定时任务
 * @date 2016年8月19日上午11:37:35
 */
@Component
public class CommunityJob implements Serializable {

	private static final long serialVersionUID = -4156158344955209535L;

	public ObjectMapper m = new ObjectMapper();

	private Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	private AttendanceTemplateService templateService;

	@Autowired
	private MsgService magService;

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private WebSocketMsgService webSocketMsgService;

	@Autowired
	public SystemWebSocketHandler systemWebSocketHandler() {
		return new SystemWebSocketHandler();
	}

	// 上班考勤考勤提醒
//	 @Scheduled(cron = "0 0/5 * * * ? ") // 测试，每分钟
	public void attendanceRemind() throws AuthenticationException, InvalidRequestException, APIConnectionException,
			APIException, ChannelException, JsonProcessingException {
		Date now = new Date();
		String msg = "";
		// 先查出所有需要提醒模板
		List<AttendanceTemplate> tempList = templateService.selectNeedRemindTemplate();
		for (AttendanceTemplate template : tempList) {
			// 判断今天是否需要打卡
			if (templateService.needAttendanceByTemplate(now, template) == 0) {
				continue;
			}
			Date templateDate = DateUtil.attendanceDateConvert(template.getOnDuty());
			if (now.getTime() > templateDate.getTime()) {
				continue;
			}
			// 现在的时间-考勤的时间<=模板的里的提醒时间
			int remainTime = DateUtil.DateDiffReturnMinute(templateDate, now);
			if (remainTime == template.getRemind()) {
				msg = "距您上班打卡时间还有:" + remainTime + "分钟";
				// 去查询模板里的所有用户
				List<String> userList = templateService.getAllTemplateUserByTemplateId(template.getId());
				// 发送通知
				PushBean pushBean = new PushBean();
				pushBean.setRefId("");
				pushBean.setType(PushType.ATTENDANCE_MSG);
				pushBean.setContent(msg);
				String[] userarray = new String[userList.size()];
				String arrays[] = userList.toArray(userarray);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", arrays, PushFrom.LINLE_COMMUNITY));
			}
		}
		_logger.info("发送考勤通知成功");
	}

	/**
	 * 
	 * @throws JsonProcessingException
	 * @Description 系统的通知模块 1:每次发送一条消息 2:消息存入数据库中(目前是存到redis中的,如果要做列表的话
	 *              是要存到数据库的) 3:用户登录后立即返回消息(返回最新的一条消息)
	 *              4:消息及时性问题(是实时发出还是放到任务里面统一调度,放到任务里统一调度)
	 */
//	@Scheduled(cron = "0 0/1 * * * ? ")
	public void communityMsgJob() throws JsonProcessingException {
		// userMsgList
		Set<String> set = (Set<String>) redisManager.sMembers("userMsgList");
		List<WebSocketMsg> list = new ArrayList<>();
		for (String uid : set) {
			// 读取出redis中每个用户的第一条消息
			WebSocketMsg msg = magService.getMsg(uid);
			if (msg != null) {
				list.add(msg);
			}
		}
		// 发送记录数组
		List<Long> toList = new ArrayList<>();
		if (!list.isEmpty()) {
			for (WebSocketMsg vo : list) {
				_logger.info("小区消息的大小是 :" + list.size());
				_logger.info("取出来的信息是:" + m.writeValueAsString(vo));
				_logger.info("结果是" + toList.indexOf(vo.getTo()));
				if (toList.indexOf(vo.getTo()) == -1) {
					// 没有发送过才发送
					WebSocketMsg msg = systemWebSocketHandler().sendMessageToUser(vo);
					if (msg != null) {
						magService.del(msg.getTo().toString(), msg);// redis 中删除
						msg.setSendStatus(1);
						webSocketMsgService.modifySendStatus(msg);
						toList.add(msg.getTo());
					}
				}
			}
		}
	}
}
