package com.linle.user.model;

import javax.validation.constraints.NotNull;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月31日
 **/
public class IncubatorInfo {
	@NotNull(message="参数错误")
	private Long id;
	/**
	 * 介绍
	 */
	private String intro;
	/**
	 * 政策
	 */
	private String policy;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	
}
