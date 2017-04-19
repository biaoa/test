package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.vote.model.Vote;

/**
 * 
 * @author pangd
 * @Description app请求投票响应结果
 * @date 2016年7月29日上午10:55:20
 */
public class AppLoadVoteListResponse extends BaseResponse {

	private static final long serialVersionUID = 1697998928353587909L;

	private int resultCount;

	private List<Vote> list;

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public List<Vote> getList() {
		return list;
	}

	public void setList(List<Vote> list) {
		this.list = list;
	}
}
