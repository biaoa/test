package com.linle.communityConvention.model;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description 社区公约
 * @date 2016年10月28日下午4:45:04
 */
public class CommunityConvention extends BaseDomain {

	private static final long serialVersionUID = 1720727820780087443L;

	private Long createUser;

	private String content;

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}