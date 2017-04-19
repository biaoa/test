package com.linle.mobileapi.v1.model;

/**
 * 
* @ClassName: UserInfoVO 
* @Description: 融云 根据用户ID 获得头像和昵称
* @author pangd
* @date 2016年3月29日 下午5:14:54 
*
 */
public class UserInfoVO {
	
	private Long id;
	
	private String name;
	
	private String logo;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
