package com.linle.qrCodeRoom.mapper;

import java.util.List;

import com.linle.qrCodeRoom.model.QrCodeRoom;

import component.BaseMapper;

public interface QrCodeRoomMapper  extends BaseMapper<QrCodeRoom>{
	 int insertQRcodeRoomFromRoom_noTable(long communityId);
	 
	 List<QrCodeRoom> getAllNoCreateQRcodeRoom(long communityId);

	 List<QrCodeRoom> getAllCreateQRcodeRoom(long communityId);
}