package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.DepartmentVO;

public class CommunityDepartmentResponse extends BaseResponse {

	private static final long serialVersionUID = 2804896849108200607L;
	
	private List<DepartmentVO> departmentList;
	
	private String propertyCompanyLogo;
	
	private String propertyCompanyName;
	
	private String synopsis;
	
	private String ownerNotice;

	public List<DepartmentVO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentVO> departmentList) {
		this.departmentList = departmentList;
	}

	public String getPropertyCompanyLogo() {
		return propertyCompanyLogo;
	}

	public void setPropertyCompanyLogo(String propertyCompanyLogo) {
		this.propertyCompanyLogo = propertyCompanyLogo;
	}

	public String getPropertyCompanyName() {
		return propertyCompanyName;
	}

	public void setPropertyCompanyName(String propertyCompanyName) {
		this.propertyCompanyName = propertyCompanyName;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getOwnerNotice() {
		return ownerNotice;
	}

	public void setOwnerNotice(String ownerNotice) {
		this.ownerNotice = ownerNotice;
	}
	
}
