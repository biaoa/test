package com.linle.capitalManage.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author pangd
 * @Description 交易流水
 * @date 2016年10月13日下午3:55:49
 */
public class TransactionflowVo implements Serializable{
	
	private static final long serialVersionUID = 4493632685956966297L;

	private String userName;
	
	private String orderNo;
	
	private BigDecimal totalMoney; //用户实际支付金额
	
	private BigDecimal preferentialAmount; //优惠金额
	
	private String preferentialType; //优惠类型  商家优惠   系统优惠
	
	private Date payDate; //支付时间
	
	private String orderRemark; //订单备注
	
	private String channel;//支付方式

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getPreferentialAmount() {
		return preferentialAmount;
	}

	public void setPreferentialAmount(BigDecimal preferentialAmount) {
		this.preferentialAmount = preferentialAmount;
	}

	public String getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}
