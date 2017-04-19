package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 订单评价请求信息
 */
public class OrderEvaluateRequest extends BaseRequest {

	private static final long serialVersionUID = 1144505876151463367L;
	
	@NotBlank(message="订单号不能为空")
	private String orderNo;
	
	@NotNull(message="商品评分不能为空")
	private Integer commodityStar;
	
	@NotNull(message="服务评分不能为空")
	private Integer serviceStar;
	
	@NotNull(message="配送评分不能为空")
	private Integer sendStar;
	
	private String content;//评价内容
	
	private int deliverytime;//确认送达时间  单位分钟

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getCommodityStar() {
		return commodityStar;
	}

	public void setCommodityStar(Integer commodityStar) {
		this.commodityStar = commodityStar;
	}

	public Integer getServiceStar() {
		return serviceStar;
	}

	public void setServiceStar(Integer serviceStar) {
		this.serviceStar = serviceStar;
	}

	public Integer getSendStar() {
		return sendStar;
	}

	public void setSendStar(Integer sendStar) {
		this.sendStar = sendStar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDeliverytime() {
		return deliverytime;
	}

	public void setDeliverytime(int deliverytime) {
		this.deliverytime = deliverytime;
	}
}
