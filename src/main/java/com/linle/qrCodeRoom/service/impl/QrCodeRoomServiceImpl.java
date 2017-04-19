package com.linle.qrCodeRoom.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Des3Util;
import com.linle.common.util.Page;
import com.linle.common.util.QRCodeUtil;
import com.linle.common.util.SysConfig;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.qrCodeRoom.mapper.QrCodeRoomMapper;
import com.linle.qrCodeRoom.model.QrCodeRoom;
import com.linle.qrCodeRoom.service.QrCodeRoomService;
@Service
@Transactional
public class QrCodeRoomServiceImpl extends CommonServiceAdpter<QrCodeRoom>  implements QrCodeRoomService {
	@Autowired
	private QrCodeRoomMapper mapper;
	
	@Autowired
	private CommunityService communityService;
	
	@Override
	public Page<QrCodeRoom> getAllDataForPage(Page<QrCodeRoom> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	//生成二维码
	public BaseResponse createQRCode(long id, String qrCodeName, String logoPath,String destPath){
		try {
			//生成二维码
//			String fileName =DateUtil.getTimeStamp()+"_"+qrCodeName + ".jpg";
//			String fileName =qrCodeName + ".jpg";
			
			QrCodeRoom qrCodeRoom=mapper.selectByPrimaryKey(id);
			String fileName =qrCodeRoom.getBuilding()+"-"+qrCodeRoom.getUnit()+".jpg";
			String checkAddress=organizeAddress(qrCodeName);
			String enStr=Des3Util.encode(checkAddress);
			QRCodeUtil.encode2(enStr, logoPath, fileName, destPath);
			//修改
			QrCodeRoom obj=new QrCodeRoom();
			obj.setId(id);
			obj.setCreateDate(new Date());
			obj.setPath(SysConfig.QRCODEROOM_FOLDER + fileName);
			obj.setFileName(fileName);
			mapper.updateByPrimaryKeySelective(obj);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.OperateFail;
		}
		return new BaseResponse(0, "生成成功!");
	}
	
	public  String organizeAddress(String qrCodeName){
		String checkAddress="";
		try {
//			String[] addresss=Des3Util.decode(qrCodeName).split("-");
			String[] addresss=qrCodeName.split("-");
			String communityId=addresss[0];
			String building=addresss[1];
			String unit=addresss[2];
			Community community=communityService.selectByPrimaryKey(Long.valueOf(communityId));
			checkAddress=community.getName()+building+"幢"+unit+"单元";
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		
		return checkAddress;
	}
	
	//批量生成二维码
	public BaseResponse batchCreateQRCode(long communityId, String logoPath,String destPath){
		List<QrCodeRoom> list= mapper.getAllNoCreateQRcodeRoom(communityId);
		try {
			for (QrCodeRoom qrCodeRoom : list) {
				//生成二维码
//				String fileName =DateUtil.getTimeStamp()+"_"+qrCodeRoom.getQrCodeName() + ".jpg";
//				String fileName =qrCodeRoom.getBuilding()+"幢"+qrCodeRoom.getUnit()+"单元.jpg";
				
				String fileName =qrCodeRoom.getBuilding()+"-"+qrCodeRoom.getUnit()+".jpg";
				String checkAddress=organizeAddress(qrCodeRoom.getQrCodeName());
				String enStr=Des3Util.encode(checkAddress);
				QRCodeUtil.encode2(enStr, logoPath, fileName, destPath);
				//修改
				QrCodeRoom obj=new QrCodeRoom();
				obj.setId(qrCodeRoom.getId());
				obj.setCreateDate(new Date());
				obj.setPath(SysConfig.QRCODEROOM_FOLDER + fileName);
				obj.setFileName(fileName);
				mapper.updateByPrimaryKeySelective(obj);
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
		if(list.size()==0){
			return new BaseResponse(8, "当前二维码全部生成完，若需要更多，请先导入数据!");
		}
		return new BaseResponse(0, "批量生成二维码成功，总数："+list.size());
	}

	//将房号表的数据导入到生成二维码表里
	@Override
	public BaseResponse insertQRcodeRoomFromRoom_noTable(long communityId) {
		int count=mapper.insertQRcodeRoomFromRoom_noTable(communityId);
		if(count==0){
			return new BaseResponse(8, "暂时没有数据可导入！");
		}
		return new BaseResponse(0, "成功导入总数："+count);
	}

	//获取所有未生成二维码，并生成二维码
	@Override
	public List<QrCodeRoom> getAllNoCreateQRcodeRoom(long communityId) {
		return mapper.getAllNoCreateQRcodeRoom(communityId);
	}
	
	@Override
	public List<QrCodeRoom> getAllCreateQRcodeRoom(long communityId) {
		return mapper.getAllCreateQRcodeRoom(communityId);
	}
	
}
