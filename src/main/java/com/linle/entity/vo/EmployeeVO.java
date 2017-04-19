package com.linle.entity.vo;

import java.io.Serializable;

/**
 * 
 * @author pangd
 * @Description 员工VO 添加规则时用到
 * @date 2016年8月11日下午2:39:09
 */
public class EmployeeVO implements Serializable {

	private static final long serialVersionUID = 8043225256058533046L;

	private Long uid;

	private String name;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
