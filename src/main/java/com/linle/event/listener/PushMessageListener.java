package com.linle.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.push.PushMessageService;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月10日
 **/
@Component
public class PushMessageListener implements
		ApplicationListener<PushMessageEvent> {
	protected final Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PushMessageService pushMessageService;

	@Async
	@Override
	public void onApplicationEvent(PushMessageEvent event) {
		try {
			PushBean pushBean = (PushBean) event.getPushBean();
			String msg = pushBean.getContent().toString();
			PushFrom from = event.getFrom();
			String alias = (String) event.getAlias();
			String[] aliasArr=event.getAliasArray();
			if(alias!=null&&!alias.equals("") ){
				pushMessageService.pushAliasMessage(pushBean, msg, from, alias);//单个推送目标
			}else if(aliasArr!=null&&aliasArr.length>0){
				pushMessageService.pushAliasMessage(pushBean, msg, from, aliasArr);//多个推送目标
			}
			_logger.info("消息发送成功");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);_logger.error("出错了", e);
			_logger.error("推送出错了!");
		}
	}

}
