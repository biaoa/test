package com.linle.qrCodeRoom.model;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;

public class QrCodeRoomResponse extends BaseResponse{
	
	private List<QrCodeRoom> qrCodeRoomList;

	public List<QrCodeRoom> getQrCodeRoomList() {
		return qrCodeRoomList;
	}

	public void setQrCodeRoomList(List<QrCodeRoom> qrCodeRoomList) {
		this.qrCodeRoomList = qrCodeRoomList;
	}


	
}