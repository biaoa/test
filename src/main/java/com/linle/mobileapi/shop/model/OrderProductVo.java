package com.linle.mobileapi.shop.model;

import java.io.Serializable;
/**
 * 
 * @author pangd
 * @Description 商家版商品对象
 * @date 2016年7月26日下午6:12:02
 */
public class OrderProductVo implements Serializable {

	private static final long serialVersionUID = 479285322733128574L;
	
	private String productName;
	
	private String productPrice;
	
	private int productQuantity;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

}
