package com.linle.mobileapi.v1.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 家园购买请求
 */
public class CommodityRequest extends BaseRequest {

	private static final long serialVersionUID = -1304830683235014334L;
	
	@NotNull(message="商铺ID不能为空")
	private Long shopId; //商铺ID
	
	@NotEmpty(message="商品不能为空")
	private String goodsList; //商品结合 json串
	
	private String privilegeId; //优惠ID 可能会有多个，用逗号隔开
	
	@NotNull(message="总价不能为空")
	private BigDecimal totalAmount; //总价格
	
	@NotNull(message="实际价格不能为空")
	private BigDecimal actualAmount;//实际支付价格
	
	private String buyerMessage;//买家备注
	
	private String beginDate; //预约开始时间
	
	private String endDate; //预约结束时间
	
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(String goodsList) {
		this.goodsList = goodsList;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
