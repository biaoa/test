package com.linle.event.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.event.RongMessageEvent;
import com.linle.io.rong.models.TxtMessage;
import com.linle.io.rong.service.RongService;
/**
 * @描述:
 * @作者:
 * @创建时间：2016年08月02日
 **/
@Component
public class RongMessageListener implements ApplicationListener<RongMessageEvent> {
	protected final Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RongService rongService;

	@Async
	@Override
	public void onApplicationEvent(RongMessageEvent event) {
		try {
			Long sendId = event.getSendId();//发送人
			List<String> toUserIds = event.getToUserIds();//接收人
			TxtMessage message = (TxtMessage) event.getMessage();//信息内容
			rongService.sendMessage(sendId,toUserIds, message);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("融云发送出错了!");
		}
	}

}
