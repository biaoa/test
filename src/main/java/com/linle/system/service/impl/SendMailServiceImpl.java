package com.linle.system.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.system.service.SendMailService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月8日
 **/

@Service("sendMailService")
@Transactional
public class SendMailServiceImpl implements SendMailService{
	
	@Resource
	private JavaMailSender mailSender;

	@Override
	public void sendMail(List<String> to, String from, String subject,
			String text) throws Exception {
		MimeMessage mailMessage=mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(
				mailMessage, true, "utf-8");
		String[] address=new String[to.size()];
		to.toArray(address);
		messageHelper.setTo(address);
		messageHelper.setSubject(subject);
		messageHelper.setText(text, true);
		messageHelper.setFrom(from);
		mailSender.send(mailMessage);
		
		
	}
	
	private String getMailList(List<String> to) {
		StringBuffer toList = new StringBuffer();
		int length = to.size();
		if (to != null && length < 2) {
			toList.append(to.get(0));
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(to.get(i));
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}

}
