package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description app端请求投票列表
 * @date 2016年7月29日上午10:51:24
 */
public class AppLoadVoteListRequest extends BaseRequest {

	private static final long serialVersionUID = 8685182453358651678L;
	
	private int pageSize;
	
	private int pageNuber;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNuber() {
		return pageNuber;
	}

	public void setPageNuber(int pageNuber) {
		this.pageNuber = pageNuber;
	}

}
