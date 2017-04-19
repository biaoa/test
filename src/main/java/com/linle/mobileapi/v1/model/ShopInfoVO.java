package com.linle.mobileapi.v1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author pangd
 * @Description 商家信息API
 */
public class ShopInfoVO {
	
	@JsonIgnore
	private Long id;
	
	private String dispatchTime;//配送时间
	
	private String shopPhone;//商家电话
	
	private String shopAddress;//商家地址
	
	private String dispatchCompany;//配送公司
	
	private List<ShopPrivilege> privilegesList; //优惠活动
	
	private String introduction;//商家介绍
	
	private List<String> certificates;//商家证件照

	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getDispatchCompany() {
		return dispatchCompany;
	}

	public void setDispatchCompany(String dispatchCompany) {
		this.dispatchCompany = dispatchCompany;
	}

	public List<ShopPrivilege> getPrivilegesList() {
		return privilegesList;
	}

	public void setPrivilegesList(List<ShopPrivilege> privilegesList) {
		this.privilegesList = privilegesList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<String> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<String> certificates) {
		this.certificates = certificates;
	}

}
