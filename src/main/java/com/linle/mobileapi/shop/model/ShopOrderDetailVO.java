package com.linle.mobileapi.shop.model;

import java.io.Serializable;

public class ShopOrderDetailVO implements Serializable {

	private static final long serialVersionUID = -1977312517460000217L;
	
	private String productName;
	
	private int productQuantity;
	
	private String productPrice;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

}
