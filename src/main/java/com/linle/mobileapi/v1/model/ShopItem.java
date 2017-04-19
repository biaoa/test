package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ShopItem {
	 private String id;//商店id
	 
	 private String shopname;//商店名称
	 
	 private String imagUrl;//商店图标
	 private String logo;//商店图标
	 private int monthSales;//月销售多少单 先不计算
	 
	 private float grade;//评价等级 先不计算
	 
	 private int minTime;//最短时间
	 
	 private BigDecimal minDisPatch;//最小起送价
	 
	 private BigDecimal disPatchPrice;//配送费
	 
	 @JsonIgnore
	 private String beginDate; //营业开始时间
	 
	 @JsonIgnore
	 private String endDate; //营业结束时间
	 
	 private boolean operateStatus; //营业状态
	 
	 private List<ShopPrivilege> privilegesList;//优惠列表
	 
	 
	 private int status;//商家营业状态 0 营业  1 歇业

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getImagUrl() {
		return imagUrl;
	}

	public void setImagUrl(String imagUrl) {
		this.imagUrl = imagUrl;
	}

	public int getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(int monthSales) {
		this.monthSales = monthSales;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public int getMinTime() {
		return minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public BigDecimal getMinDisPatch() {
		return minDisPatch;
	}

	public void setMinDisPatch(BigDecimal minDisPatch) {
		this.minDisPatch = minDisPatch;
	}

	public BigDecimal getDisPatchPrice() {
		return disPatchPrice;
	}

	public void setDisPatchPrice(BigDecimal disPatchPrice) {
		this.disPatchPrice = disPatchPrice;
	}

	public List<ShopPrivilege> getPrivilegesList() {
		return privilegesList;
	}

	public void setPrivilegesList(List<ShopPrivilege> privilegesList) {
		this.privilegesList = privilegesList;
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

	public boolean isOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(boolean operateStatus) {
		this.operateStatus = operateStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}