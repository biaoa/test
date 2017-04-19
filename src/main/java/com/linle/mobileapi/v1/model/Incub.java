package com.linle.mobileapi.v1.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @描述:孵化器
 * @作者:杨立忠
 * @创建时间：2015年9月7日
 **/
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Incub implements Serializable{

	private Long id;
	/** 孵化器名称**/
	private String name;
	/** 孵化器logo**/
	private String logo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	

}
