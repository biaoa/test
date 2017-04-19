package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 意见反馈列表请求
 * @date 2016年8月4日下午3:26:04
 */
public class GetAdviceRequest extends BaseRequest {

	private static final long serialVersionUID = 4394418780973924903L;

	private Integer pageSize;

	private Integer pageNumber;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
