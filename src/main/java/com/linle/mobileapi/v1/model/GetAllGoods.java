package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

// 获得商家所有的商品信息
public class GetAllGoods {
	
	@JsonIgnore
	private Long id;
	
	private  List<GoodsSort> sortList;//分类列表
	
	private String notice;//商家公告
	
	private String shopName;
	
	private BigDecimal minDisPatch;//最小起送价
	
	private BigDecimal disPatchPrice;//配送费
	
	private boolean operateStatus;
	
	private List<Privilege> prvilegeList;//优惠列表

	public List<GoodsSort> getSortList() {
		return sortList;
	}

	public void setSortList(List<GoodsSort> sortList) {
		this.sortList = sortList;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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

	public List<Privilege> getPrvilegeList() {
		return prvilegeList;
	}

	public void setPrvilegeList(List<Privilege> prvilegeList) {
		this.prvilegeList = prvilegeList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public boolean isOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(boolean operateStatus) {
		this.operateStatus = !operateStatus;
	}
	
}
