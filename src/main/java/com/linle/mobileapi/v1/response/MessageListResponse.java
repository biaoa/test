package com.linle.mobileapi.v1.response;

import java.util.ArrayList;
import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.Messages;

/** 
 * @描述
 * @作者  杨立忠 
 * @版本 V1.0 
 * @创建时间：2014-5-28 下午06:05:22 
 */
public class MessageListResponse extends BaseResponse{
	private int pageNumber = 1;
	private int pageSize = 20;
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	private List<Messages> messages =  new ArrayList<Messages>();
	public List<Messages> getMessages() {
		return messages;
	}
	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}
	
}
