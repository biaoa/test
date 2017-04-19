package com.linle.mobileapi.v1.model;

import java.util.Date;

/**
 * 
 * @author pangd
 * @Description 评价内容
 */ 
public class EvaluateInfo {
	private String name;//评价人
	
	private String logo;//头像
	
	private String content;//评价内容
	
	private Date evaluateDate;//评价时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}
}
