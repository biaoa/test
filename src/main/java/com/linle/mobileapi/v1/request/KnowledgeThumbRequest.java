package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 邻乐速报点赞请求
 * @author pangd
 * @Description
 * @date 2016年10月20日下午2:22:21
 */
public class KnowledgeThumbRequest extends BaseRequest {

	private static final long serialVersionUID = 6784057988753853181L;
	
	@NotNull(message="参数错误")
	private Long knowledgeId; //邻乐速报ID
	
	@NotNull(message="参数错误")
	private Integer status;

	public Long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
