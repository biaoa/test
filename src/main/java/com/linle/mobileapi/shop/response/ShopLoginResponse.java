package com.linle.mobileapi.shop.response;

import java.math.BigDecimal;

import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description 商家登陆返回信息
 * @date 2016年7月25日下午3:25:54
 */
public class ShopLoginResponse extends BaseResponse {

	private static final long serialVersionUID = 5881146290123495996L;
	
	private Long userId;
	
	private String sid;
	
	private String shopName;
	
	private int shopStatus;
	
	private String shopLogo;
	
	private String phone;
	
	private BigDecimal balance; //余额

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(int shopStatus) {
		this.shopStatus = shopStatus;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
