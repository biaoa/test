package com.linle.entity.statistics;

public class OrderStatistics extends BaseStatistics{
	private int	sumQuantity;
	private int  onlineQuantity;
	private int  offlineQuantity;
	
	private int	sumAmount;
	private int  onlineAmount;
	private int  offlineAmount;
	
	public int getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}
	public int getOnlineAmount() {
		return onlineAmount;
	}
	public void setOnlineAmount(int onlineAmount) {
		this.onlineAmount = onlineAmount;
	}
	public int getOfflineAmount() {
		return offlineAmount;
	}
	public void setOfflineAmount(int offlineAmount) {
		this.offlineAmount = offlineAmount;
	}
	public int getSumQuantity() {
		return sumQuantity;
	}
	public void setSumQuantity(int sumQuantity) {
		this.sumQuantity = sumQuantity;
	}
	public int getOnlineQuantity() {
		return onlineQuantity;
	}
	public void setOnlineQuantity(int onlineQuantity) {
		this.onlineQuantity = onlineQuantity;
	}
	public int getOfflineQuantity() {
		return offlineQuantity;
	}
	public void setOfflineQuantity(int offlineQuantity) {
		this.offlineQuantity = offlineQuantity;
	}
	
}
