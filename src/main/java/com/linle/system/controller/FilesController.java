package com.linle.system.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.base.BaseController;
import com.linle.common.base.CommonResponseMsg;
import com.linle.common.service.FileService;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.SysFile;

import javafx.util.Pair;

/**
 * 资讯内容
 * <p>
 * 2015年9月24日上午10:52:30
 */
@Controller
@RequestMapping("sys/file")
public class FilesController extends BaseController{
	
	
    @Autowired
    protected FileService sysFileService; //文件
    /**
     * 异步上传 返回路径
     *
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Pair<Long, String> uploadLogoFile(@RequestParam CommonsMultipartFile file,String type, HttpServletRequest request) {
        Pair<Long, String> info = null;
        try {
//        	String realPath = request.getSession().getServletContext().getRealPath("/");
        	String realPath = SysConfig.DISK_FOLDER;
            info = sysFileService.addFile(file, realPath,type);
        } catch (Exception e) {
            e.printStackTrace(); _logger.error("出错了", e);
        }
        return info;
    }
    
    @ResponseBody 
    @RequestMapping(value = "/delimg", method = RequestMethod.POST)
    public CommonResponseMsg delimg(String imgpath, HttpServletRequest request) {
//        String path = request.getSession().getServletContext().getRealPath("upload");// 存放位置
    	String path = SysConfig.DISK_FOLDER;
        File file = new File(path + imgpath.substring(6));
        try {
        	if(file.exists()) {
        		if (file.delete()) {
                 	sysFileService.delFileByPath(imgpath);//删除存在数据库
                 } else {
                     return CommonResponseMsg.RemoveFailInstance;
                 }
        	}else{
        		 sysFileService.delFileByPath(imgpath);//本地文件不存在，数据库存在
        		 return new CommonResponseMsg(0, "文件不存在", null);
        	}
		} catch (Exception e) {
			 e.printStackTrace(); _logger.error("出错了", e);
		}
        return CommonResponseMsg.RemoveSucInstance;
    }
    
    @ResponseBody
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public Pair<Long, List<SysFile>> uploadLogoFiles(@RequestParam CommonsMultipartFile[] file2,Long foldeID,String type, HttpServletRequest request) {
        Pair<Long, List<SysFile>> info = null;
        try {
        	String folderPath = "";
    		switch (type) {
    		case "shop":
    			folderPath=SysConfig.SHOP_FOLDER;
    			break;
    		case "topic":
    			folderPath=SysConfig.TOPIC_FOLDER;
    			break;
    		default:
    			break;
    		}
//        	String realPath = request.getSession().getServletContext().getRealPath("/");
    		String realPath = SysConfig.DISK_FOLDER;
            info = sysFileService.addFiles(file2,realPath ,foldeID,folderPath);
        } catch (Exception e) {
            e.printStackTrace(); _logger.error("出错了", e);
        }
        return info;
    }
}
