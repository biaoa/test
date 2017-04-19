package com.linle.qrCodeRoom.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.qrCodeRoom.model.QrCodeRoom;

public interface QrCodeRoomService  extends BaseService<QrCodeRoom> {
	public Page<QrCodeRoom> getAllDataForPage(Page<QrCodeRoom> page);
	public BaseResponse createQRCode(long id,String qrCodeName,String logoPath,String destPath);
	public BaseResponse batchCreateQRCode(long communityId, String logoPath,String destPath);
	public BaseResponse insertQRcodeRoomFromRoom_noTable(long communityId);
	
	public List<QrCodeRoom> getAllNoCreateQRcodeRoom(long communityId);
	
	public List<QrCodeRoom> getAllCreateQRcodeRoom(long communityId);
}
