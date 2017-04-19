package com.linle.entity.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: SpaceOrder 
* @Description: 车位订单信息 
* @author pangd
* @date 2016年4月13日 下午6:51:15 
*
 */
public class SpaceOrder implements Serializable{
	
	private static final long serialVersionUID = 1981570876564870778L;

	private Long orderId;
	
	private String orderNo; //订单号
	
	private String parkingName;//车位名称
	
	private String grarageName;//车库名称
	
	private String orderType;//申请类型
	
	private String dateType;//申请套餐。1月 1季度 1年
	
	private Date endData; //车位到期日期
	
	private Date orderCreateDate; //订单创建日期
	
	private String userName; //用户名称
	
	private String addressDetail;//房号
	
	private int orderStatus;//订单状态
	
	private Long userId; //用户ID

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getGrarageName() {
		return grarageName;
	}

	public void setGrarageName(String grarageName) {
		this.grarageName = grarageName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Date getEndData() {
		return endData;
	}

	public void setEndData(Date endData) {
		this.endData = endData;
	}

	public Date getOrderCreateDate() {
		return orderCreateDate;
	}

	public void setOrderCreateDate(Date orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
