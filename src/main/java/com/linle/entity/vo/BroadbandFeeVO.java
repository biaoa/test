package com.linle.entity.vo;

import com.linle.common.util.ExcelVOAttribute;

/**
 * 
* @ClassName: GasFee 
* @Description: 宽带费 有线电视
* @author pangd
* @date 2016年3月25日 下午2:57:45 
*
 */
public class BroadbandFeeVO {
	
	@ExcelVOAttribute(column = "A", name = "业主名称")
	private String name; //业主名称
	
	@ExcelVOAttribute(column = "B", name = "门牌号")
	private String houseNumber; //门牌号
	
	@ExcelVOAttribute(column = "C", name = "支付金额")
	private String payable; //支付金额
	
	@ExcelVOAttribute(column = "D", name = "费用时间")
	private String feeTime;//费用时间
	
	@ExcelVOAttribute(column = "F", name = "已缴/未缴")
    private String payStatus; //上传数据时的缴费状态

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getPayable() {
		return payable;
	}

	public void setPayable(String payable) {
		this.payable = payable;
	}

	public String getFeeTime() {
		return feeTime;
	}

	public void setFeeTime(String feeTime) {
		this.feeTime = feeTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	

}
