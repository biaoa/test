package com.linle.mobileapi.v1.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

public class RefundOrderRequest extends BaseRequest {

	private static final long serialVersionUID = -1075468840530583527L;
	
	@NotNull(message="订单号不能为空")
	private String orderNo;
	
	@NotNull(message="退款描述不能为空")
	private String description;
	
	@NotNull(message="退单原因不能为空")
	private String refundType;//退单原因
	
	private	List<CommonsMultipartFile> list;
	
	private Long folderId; //文件ID
	
	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CommonsMultipartFile> getList() {
		return list;
	}

	public void setList(List<CommonsMultipartFile> list) {
		this.list = list;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

}
