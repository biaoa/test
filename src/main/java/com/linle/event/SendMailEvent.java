package com.linle.event;


import java.util.List;

import org.springframework.context.ApplicationEvent;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/

public class SendMailEvent extends ApplicationEvent{
	private static final long serialVersionUID = 1L;
	
	private String from;
	private String subject;
	private String text;

	public SendMailEvent(List<String> to,String from,String subject,String text) {
		super(to);
		this.from=from;
		this.subject=subject;
		this.text=text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	

}
