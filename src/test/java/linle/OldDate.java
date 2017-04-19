package linle;

import java.io.Serializable;

import com.linle.common.util.ExcelVOAttribute;

public class OldDate implements Serializable{
	
	private static final long serialVersionUID = -4609311278810183996L;

	@ExcelVOAttribute(column = "C", name = "业主名称")
	private String name; //业主名称
	
	@ExcelVOAttribute(column = "B", name = "房间号")
	private String roomNo; //房间号
	
	@ExcelVOAttribute(column = "D", name = "面积")
	private String acreage; //面积
	
	@ExcelVOAttribute(column = "E", name = "单价")
	private String price;//单价
	
	@ExcelVOAttribute(column = "M", name = "费用时间")
	private String month;//费用时间 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getAcreage() {
		return acreage;
	}

	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
