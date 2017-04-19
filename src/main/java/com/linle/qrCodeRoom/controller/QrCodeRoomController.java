package com.linle.qrCodeRoom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.SysConfig;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.qrCodeRoom.model.QrCodeRoom;
import com.linle.qrCodeRoom.model.QrCodeRoomResponse;
import com.linle.qrCodeRoom.service.QrCodeRoomService;
import com.linle.roomno.service.RoomService;

/**
 * 
 * @Description: 巡更二维码管理
 * @author chenkai
 * @date 2016年8月10日
 *
 */
@Controller
@RequestMapping("qrCodeRoom")
public class QrCodeRoomController extends BaseController {

	@Autowired
	private QrCodeRoomService qrCodeRoomService;
	@Autowired
	private RoomService roomService;
	// 列表
	@RequiresPermissions("qrCodeRoom_manage")
	@RequestMapping(value = "/list")
	public String list(Integer pageNo, Model model,String buildingType,String unitType,String roomSearch) {
		Page<QrCodeRoom> page = new Page<QrCodeRoom>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		long communityId=getCommunity().getId();
		params.put("communityId", communityId);
		params.put("buildingType", buildingType);
		params.put("unitType", unitType);
		page.setParams(params);
		try {
			qrCodeRoomService.getAllDataForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		//搜索相关
		model.addAttribute("buildingList", roomService.getBuildForAPI(communityId));//该小区所有幢
		model.addAttribute("buildingType", buildingType);//幢
		model.addAttribute("unitType", unitType);//单元
		model.addAttribute("roomSearch", roomSearch);//房号
		return "/qrCodeRoom/qrCodeRoom_list";
	}
	
	//创建二维码
	@RequiresPermissions("qrCodeRoom_manage")
	@RequestMapping(value = "/createQRCode", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse createQRCode(long id,String qrCodeName, HttpServletRequest request){
		try {
			String logoPath = request.getSession().getServletContext().getRealPath("/resources/images/linle.png");
			String destPath= SysConfig.DISK_FOLDER +SysConfig.QRCODEROOM_FOLDER;
			BaseResponse res=qrCodeRoomService.createQRCode(id,qrCodeName,logoPath,destPath);
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//批量创建二维码
	@RequiresPermissions("qrCodeRoom_manage")
	@RequestMapping(value = "/batchCreateQRCode")
	@ResponseBody
	public BaseResponse batchCreateQRCode(HttpServletRequest request){
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				String logoPath = request.getSession().getServletContext().getRealPath("/resources/images/linle.png");
				String destPath= SysConfig.DISK_FOLDER +SysConfig.QRCODEROOM_FOLDER;
				BaseResponse res=qrCodeRoomService.batchCreateQRCode(getCommunity().getId(), logoPath, destPath);
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//将房号表的数据导入到生成二维码表里
	@RequiresPermissions("qrCodeRoom_manage")
	@RequestMapping(value = "/insertQRcodeRoomFromRoom_noTable")
	@ResponseBody
	public BaseResponse insertQRcodeRoomFromRoom_noTable(HttpServletRequest request){
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				BaseResponse res=qrCodeRoomService.insertQRcodeRoomFromRoom_noTable(getCommunity().getId());
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("qrCodeRoom_manage")
	@RequestMapping(value = "/getAllCreateQRcodeRoom")
	@ResponseBody
	public BaseResponse getAllCreateQRcodeRoom(HttpServletRequest request){
		try {
			QrCodeRoomResponse res=new QrCodeRoomResponse();
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				List<QrCodeRoom> list=qrCodeRoomService.getAllCreateQRcodeRoom(getCommunity().getId());
				res.setCode(0);
				res.setQrCodeRoomList(list);
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
 }
