package com.linle.mobileapi.v2.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.base.BaseController;
import com.linle.common.util.FileUtil;
import com.linle.common.util.SysConfig;
import com.linle.common.util.ThumbnailUtil;
import com.linle.entity.sys.SysFile;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v2.request.FileUploadRequest;
import com.linle.mobileapi.v2.response.FileResponse;
import com.linle.system.service.SysFileService;

@Controller
@RequestMapping("/api/2")
public class FileApiController extends BaseController {

	@Autowired
	private SysFileService fileService;

	/**
	 * 单个文件上传
	 * 多个文件上传，就多次请求该方法
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public BaseResponse fileUpload(@Valid FileUploadRequest request, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			FileResponse res = new FileResponse();
			CommonsMultipartFile multipartFile=request.getFile();
			_logger.error("参数:"+m.writeValueAsString(request.getFolderId()));
			String originalName = request.getFile().getOriginalFilename();//文件原始名
			try {
				FileUtil.getFileSuffix(originalName);
			} catch (Exception e) {
				res.setCode(1);
				res.setMsg("上传失败，文件名格式不对！");
				return res;
			}
			
			String path = FileUtil.createFilePath(SysConfig.DISK_FOLDER + SysConfig.UPLOAD_FOLDER + request.getFolderName() + "/", originalName);
			File localFile = new File(path + FileUtil.createNewFileName(originalName));
			
			if (!localFile.exists()) {
				localFile.createNewFile();//创建新文件
			}
			//处理缩略图
			String thum_UploadPath=path+ ("thum_"+localFile.getName());
			ThumbnailUtil.thumbnail(multipartFile, thum_UploadPath);
			
			multipartFile.transferTo(localFile);//转存原文件
			
			SysFile sysFile = new SysFile();
			sysFile.setOriginalName(originalName);
			sysFile.setFileName(localFile.getName());
			sysFile.setPath(FileUtil.getDbPath(path) + localFile.getName());
			//缩略图
			sysFile.setThumPath(FileUtil.getDbPath(path) + ("thum_"+localFile.getName()));
			sysFile.setThumName("thum_"+localFile.getName());
			sysFile.setCreateDate(new Date());
			// 单张
			if (request.getType() == 0) {
				SysFile file = fileService.addFile(sysFile);
				res.setFileParams(file.getPath());//返回图片路径
			} else if (request.getType() == 1) {//多张图片分开上传，第一个上传的图片将folderId返回，下张图片保存需用到
				SysFile file = fileService.addFiles(sysFile, request.getFolderId());
				res.setFileParams(file.getFolderId().toString());//返回folderId
			}
			res.setCode(0);
			res.setMsg("上传成功");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("fileUpload上传文件出错:" + e);
			return BaseResponse.ServerException;
		}
	}

}
