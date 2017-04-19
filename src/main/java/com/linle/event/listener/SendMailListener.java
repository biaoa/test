package com.linle.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.event.SendMailEvent;
import com.linle.system.service.SendMailService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月8日
 **/
@Component
public class SendMailListener implements ApplicationListener<SendMailEvent>{
	
	@Autowired
	private SendMailService sendMailService;
	
	@Async
	@Override
	public void onApplicationEvent(SendMailEvent event) {
		List<String> to=(List<String>) event.getSource();
		String from =event.getFrom();
		String subject = event.getSubject();
		String text = event.getText();
		 try {
				sendMailService.sendMail(to, from, subject, text);
			} catch (Exception e) {
			}
	
	}
	
	

}
