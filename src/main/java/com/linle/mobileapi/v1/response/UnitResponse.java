package com.linle.mobileapi.v1.response;

import java.util.List;

public class UnitResponse {
	private String unit;
	
	private List<RoomResponse> roomList;
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<RoomResponse> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomResponse> roomList) {
		this.roomList = roomList;
	}

}
