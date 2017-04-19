package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.Privilege;
import com.linle.mobileapi.v1.model.Sort;

public class ShopTypeResponse extends BaseResponse {

	private static final long serialVersionUID = -8163997479052132957L;
	
	private List<Sort> sortList; //商铺分类集合
	
	private List<Privilege> privilegesList; //优惠活动集合

	public List<Sort> getSortList() {
		return sortList;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}

	public List<Privilege> getPrivilegesList() {
		return privilegesList;
	}

	public void setPrivilegesList(List<Privilege> privilegesList) {
		this.privilegesList = privilegesList;
	}
	

}
