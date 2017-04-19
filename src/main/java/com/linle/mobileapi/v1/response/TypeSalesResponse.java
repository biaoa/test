package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.SortSales;


public class TypeSalesResponse extends BaseResponse {

	private static final long serialVersionUID = 6347497194765111929L;
	
	private List<SortSales> sortSalesList;

	public List<SortSales> getSortSalesList() {
		return sortSalesList;
	}

	public void setSortSalesList(List<SortSales> sortSalesList) {
		this.sortSalesList = sortSalesList;
	}

}
