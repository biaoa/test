package com.linle.entity.vo;

import com.linle.common.util.ExcelVOAttribute;
/**
 * 
* @ClassName: PropertyFeeVO 
* @Description: excel 数据导入VO
* @author pangd
* @date 2016年3月24日 下午3:52:45 
*
 */
public class PropertyFeeVO {
	@ExcelVOAttribute(column = "A", name = "业主名称")
	private String name; //业主名称
	
	@ExcelVOAttribute(column = "B", name = "房间号")
	private String roomNo; //房间号
	
	@ExcelVOAttribute(column = "C", name = "面积")
	private String acreage; //面积
	
	@ExcelVOAttribute(column = "D", name = "单价")
	private String price;//单价
	
	@ExcelVOAttribute(column = "E", name = "应缴")
	private String payable; //应缴
	
	@ExcelVOAttribute(column = "F", name = "费用时间")
	private String feeTime;//费用时间
	
	@ExcelVOAttribute(column = "G", name = "已缴/未缴")
    private String payStatus; //上传数据时的缴费状态

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getAcreage() {
		return acreage;
	}

	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPayable() {
		return payable;
	}

	public void setPayable(String payable) {
		this.payable = payable;
	}

	public String getFeeTime() {
		return feeTime;
	}

	public void setFeeTime(String feeTime) {
		this.feeTime = feeTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}
