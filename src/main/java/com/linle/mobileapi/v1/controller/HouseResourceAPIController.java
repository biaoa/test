package com.linle.mobileapi.v1.controller;

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
 * 
* @ClassName: HouseResourceAPIController 
* @Description: 房屋租售API
* @author pangd
* @date 2016年3月29日 下午7:32:19 
*
 */
@Controller
@RequestMapping("/api/1")
public class HouseResourceAPIController extends BaseController {

	@Autowired
	private SysFileService fileService;

	
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
				Long folderID = doFile(req.getList(),servletRequest);
				SysFolder folder = new SysFolder();
				folder.setId(folderID);
				HouseResource record = new HouseResource();
				record.setSingle(OrderCode.GenerationOrderCode());
				record.setCommunityId(user.getCommunity().getId());
				record.setUser(user);
				record.setContent(req.getContent());
				record.setType(req.getType());
				record.setPrice(req.getPrice());
				record.setStatus(0);
				record.setFolder(folder);
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

	public Long doFile(List<CommonsMultipartFile> imgs,HttpServletRequest servletRequest) {
		List<SysFile> sysFiles = new ArrayList<SysFile>();
		if (null != imgs && imgs.size() > 0) {
			for (CommonsMultipartFile multipartFile : imgs) {
				String originalName = multipartFile.getOriginalFilename();
				if (!multipartFile.isEmpty()) {
					String path = FileUtil.createFilePath(SysConfig.DISK_FOLDER+SysConfig.HOUSE_RESOURCE_FOLDER, originalName);
					File localFile = new File(path + FileUtil.createNewFileName(originalName));
					try {
						if (!localFile.exists()) {
							localFile.createNewFile();
						}
						multipartFile.transferTo(localFile);
					} catch (Exception e) {
						_logger.error("文件上传失败了：" + e);
					}
					SysFile sysFile = new SysFile();
					sysFile.setOriginalName(originalName);
					sysFile.setFileName(localFile.getName());
					sysFile.setPath(FileUtil.getDbPath(path) + localFile.getName());
					sysFiles.add(sysFile);
				}
			}

		}
		SysFolder folder = fileService.addFiles(sysFiles);
		if (folder==null) {
			return null;
		}
		return folder.getId();
	}
	
	
	// 获得房源列表
	@ResponseBody
	@RequestMapping(value = "/getHouseResourceList")
	public BaseResponse getHouseResouceList(HouseResouceListRequest req,Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
		HouseResouceResponse res = new HouseResouceResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Map<String, Object> map = new HashMap<>();
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("uid", user.getId());
				res.setData(houseService.getHouseResouceList(map));
				res.setCode(0);
				res.setMsg("获取成功");
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			res.setCode(1);
			res.setMsg("获取异常");
		}
		return res;
	}
	
	//修改房源信息
	@ResponseBody
	@RequestMapping(value = "/modifyHouseResource", method = RequestMethod.POST)
	public BaseResponse modifyHouseResouce(HouseResourceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
//				Users user = userService.findUserInfoByUserName(subject.getPrincipal().toString());
				Long folderID = doFile(req.getList(),servletRequest);
				SysFolder folder = new SysFolder();
				folder.setId(folderID);
				HouseResource record = houseService.selectByPrimaryKey(req.getId());
				if (record!=null) {
					record.setContent(req.getContent());
					record.setType(req.getType());
					record.setPrice(req.getPrice());
					record.setStatus(0);
					record.setFolder(folder);
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
	
	
	//删除房源信息
	@ResponseBody
	@RequestMapping(value = "/delHouseResource", method = RequestMethod.POST)
	public BaseResponse delHouseResource(DelHouseResourceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				boolean isok =houseService.delHouseResource(user.getId(),req.getId());
				return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
			} else {
				return new BaseResponse(9, "请重新登陆");
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("删除房源信息出错了");
			return BaseResponse.ServerException;
		}

	}
	
}