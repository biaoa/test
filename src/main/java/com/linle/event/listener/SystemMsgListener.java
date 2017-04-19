package com.linle.event.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.linle.event.SystemMsgEvent;
import com.linle.socket.msg.MsgService;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.socket.msg.service.WebSocketMsgService;

/**
 * 
 * @author pangd
 * @Description 发送系统消息
 * @date 2016年11月9日下午5:56:33
 */
@Component
public class SystemMsgListener implements ApplicationListener<SystemMsgEvent> {

	protected final Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MsgService msgService;

	@Autowired
	private WebSocketMsgService webSocketMsgService;

	@Override
	public void onApplicationEvent(SystemMsgEvent event) {
		String key = event.getSource().toString();
		WebSocketMsg webMsg = event.getWebMsg();
		webMsg.setSendStatus(0);
		webMsg.setReadStatus(0);
		webMsg.setCreateDate(new Date());
		if (!webMsg.getTos().isEmpty()) {
			for (Long uid : webMsg.getTos()) {
				webMsg.setId(null);
				webMsg.setTo(uid);
				webSocketMsgService.insertSelective(webMsg);// 消息存入数据库
				msgService.add(key, webMsg); // 加入redis中的消息队列
			}
		} else {
			webSocketMsgService.insertSelective(webMsg);// 消息存入数据库
			msgService.add(key, webMsg); // 加入redis中的消息队列
		}
	}
}
