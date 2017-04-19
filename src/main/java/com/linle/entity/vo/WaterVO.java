package com.linle.entity.vo;

import java.math.BigDecimal;

import com.linle.common.util.ExcelVOAttribute;

//水电费的
public class WaterVO {
	
	@ExcelVOAttribute(column = "A", name = "业主名称")
	private String name; //业主名称
	
	@ExcelVOAttribute(column = "B", name = "房间号")
	private String roomNo; //房间号

	@ExcelVOAttribute(column = "C", name = "上次抄表")
	private String lastMeterReading;//上次抄表
	
	@ExcelVOAttribute(column = "D", name = "本次抄表")
	private String thisMeterReading;//本次抄表
	
	@ExcelVOAttribute(column = "E", name = "实用")
	private String actualConsumption;//实用
	
	@ExcelVOAttribute(column = "F", name = "费用时间")
	private String recordTime; //抄表时间
	
	@ExcelVOAttribute(column = "G", name = "单价")
	private String price;//单价
	
	@ExcelVOAttribute(column = "H", name = "公摊")
	private String pooledPrice;//公摊
	
	@ExcelVOAttribute(column = "I", name = "结余")
	private String balance;
	
	@ExcelVOAttribute(column = "J", name = "应缴")
	private String payable; //应缴

	@ExcelVOAttribute(column = "K", name = "已缴/未缴")
    private String payStatus; //上传数据时的缴费状态
	
	private int status; //状态 1未交 2已交
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

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

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getLastMeterReading() {
		return lastMeterReading;
	}

	public void setLastMeterReading(String lastMeterReading) {
		this.lastMeterReading = lastMeterReading;
	}

	public String getThisMeterReading() {
		return thisMeterReading;
	}

	public void setThisMeterReading(String thisMeterReading) {
		this.thisMeterReading = thisMeterReading;
	}

	public String getActualConsumption() {
		return actualConsumption;
	}

	public void setActualConsumption(String actualConsumption) {
		this.actualConsumption = actualConsumption;
	}

	public String getPooledPrice() {
		return pooledPrice;
	}

	public void setPooledPrice(String pooledPrice) {
		this.pooledPrice = pooledPrice;
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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	
}