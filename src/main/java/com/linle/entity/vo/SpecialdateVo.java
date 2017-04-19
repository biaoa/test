package com.linle.entity.vo;

import java.util.Date;

/**
 * 
 * @author pangd
 * @Description 特殊时间VO
 * @date 2016年8月12日上午11:15:23
 */
public class SpecialdateVo {
	
	private Date specialDate;
	
	private String description;
	
	private int type;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
