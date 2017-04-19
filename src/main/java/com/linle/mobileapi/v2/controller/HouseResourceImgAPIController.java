package com.linle.mobileapi.v2.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.base.BaseController;
import com.linle.common.util.FileUtil;
import com.linle.common.util.OrderCode;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.HouseResource;
import com.linle.entity.sys.SysFile;
import com.linle.entity.sys.SysFolder;
import com.linle.entity.sys.Users;
import com.linle.houseResource.service.HouseResourceService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.DelHouseResourceRequest;
import com.linle.mobileapi.v1.request.HouseResouceListRequest;
import com.linle.mobileapi.v1.request.HouseResourceRequest;
import com.linle.mobileapi.v1.response.HouseResouceResponse;
import com.linle.system.service.SysFileService;
/**
 */
@Controller
@RequestMapping("/api/2")
public class HouseResourceImgAPIController extends BaseController {
	@Autowired
	private HouseResourceService houseService;
	
	/**
	 * 
	 * @Description: 提交房屋信息
	 * @param @param
	 *            req
	 * @param @return
	 * @return BaseResponse
	 */
	@ResponseBody
	@RequestMapping(value = "/submitHouseResource", method = RequestMethod.POST)
	public BaseResponse AddHouseResouce(HouseResourceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				HouseResource record = new HouseResource();
				if(null!=req.getFolderId()){
					SysFolder folder = new SysFolder();
					folder.setId(req.getFolderId());
					record.setFolder(folder);
				}
				record.setSingle(OrderCode.GenerationOrderCode());
				record.setCommunityId(user.getCommunity().getId());
				record.setUser(user);
				record.setContent(req.getContent());
				record.setType(req.getType());
				record.setPrice(req.getPrice());
				record.setStatus(0);
				record.setDelFlag(0);
				boolean isok = houseService.insertSelective(record)>0;
				return isok?BaseResponse.AddSuccess:BaseResponse.AddFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("添加房源出错了");
			return BaseResponse.ServerException;
		}

	}

	
	//修改房源信息
	@ResponseBody
	@RequestMapping(value = "/modifyHouseResource", method = RequestMethod.POST)
	public BaseResponse modifyHouseResouce(HouseResourceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
//					Users user = userService.findUserInfoByUserName(subject.getPrincipal().toString());
				HouseResource record = houseService.selectByPrimaryKey(req.getId());
				if(null!=req.getFolderId()){
					SysFolder folder = new SysFolder();
					folder.setId(req.getFolderId());
					record.setFolder(folder);
				}
				if (record!=null) {
					record.setContent(req.getContent());
					record.setType(req.getType());
					record.setPrice(req.getPrice());
					record.setStatus(0);
					boolean isok = houseService.updateByPrimaryKeySelective(record)>0;
					return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
				}else{
					return new BaseResponse(1, "房源不存在");
				}
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("修改房源信息出错了");
			return BaseResponse.ServerException;
		}

	}
}