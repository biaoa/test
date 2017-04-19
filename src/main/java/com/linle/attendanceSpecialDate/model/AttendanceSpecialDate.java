package com.linle.attendanceSpecialDate.model;

import java.util.Date;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description 考勤设置中的特殊时间表
 * @date 2016年8月12日上午10:34:26
 */
public class AttendanceSpecialDate extends BaseDomain{

	private static final long serialVersionUID = 287960961205105837L;

	private Long templateId;

	private Integer type;

	private Date specialDate;

	private String description;

	private Long createUser;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getSpecialDate() {
		return specialDate;
	}

	public void setSpecialDate(Date specialDate) {
		this.specialDate = specialDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
}