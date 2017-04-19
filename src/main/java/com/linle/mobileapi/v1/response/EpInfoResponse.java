package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月7日
 **/
public class EpInfoResponse extends BaseResponse {
	private Long id;
	private String epName;
	private String logo;
	private int maturity ;
	private int gold;
	public String getEpName() {
		return epName;
	}
	public void setEpName(String epName) {
		this.epName = epName;
	}
	public String getLogo() {
		return logo ==null?"":logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getMaturity() {
		return maturity;
	}
	public void setMaturity(int maturity) {
		this.maturity = maturity;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	
}
