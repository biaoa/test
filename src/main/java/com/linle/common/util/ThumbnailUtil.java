package com.linle.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
/**
 * thumbnailator
 * 支持：图片缩放，区域裁剪，水印，旋转，保持比例。
 */
public class ThumbnailUtil {
	public static final Logger _logger = LoggerFactory.getLogger(ThumbnailUtil.class);
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	

	public static String thumbnail(CommonsMultipartFile file, String uploadDir, String realUploadPath,int width,int height){
		try {
			String des = uploadDir+"/thum_"+file.getOriginalFilename();
//			Thumbnails.of(file.getInputStream()).size(WIDTH, HEIGHT).toFile(des);
			Builder<? extends InputStream> thumbnail = Thumbnails.of(file.getInputStream());
			thumbnail.size(width, height);
			thumbnail.keepAspectRatio(false);
			thumbnail.toFile(des);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return uploadDir+"/thum_"+file.getOriginalFilename();
	}
	/** 
     * MultipartFile 转换成File 
     *  
     * @param multfile 原文件类型 
     * @return File 
     * @throws IOException 
     */  
    private static File multipartToFile(CommonsMultipartFile multfile) throws IOException {  
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
        //这个myfile是MultipartFile的  
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
        File file = fi.getStoreLocation();  
        //手动创建临时文件  
        if(file.length() < 1024){  
            File tmpFile = new File("D:\\tomcat\\tomcat-7.1\\work\\Catalina\\localhost\\linle\\" +   
                    file.getName());  
            multfile.transferTo(tmpFile);  
            return tmpFile;  
        }  
        return file;  
    } 
	public static void thumbnail(CommonsMultipartFile file,String realUploadPath) throws Exception{
//	        DiskFileItem fi = (DiskFileItem)file.getFileItem(); 
//	        File file2 = fi.getStoreLocation();
			int targetPicWidth = 0;
	        int targetPicHeight = 0;
			// 通过缓冲读入源图片文件
	        BufferedImage sourceImage = null;
	        try {
	            // 读取文件生成BufferedImage
	            sourceImage =ImageIO.read(file.getInputStream()); //获取上传图片的宽高
	        } catch (IOException ex) {
	            throw new RuntimeException("读取源图像文件出错!  ");
	        }
	        // 源图片的宽度和高度
	        int sourceWidth = sourceImage.getWidth();
	        int sourceHeight = sourceImage.getHeight();
	        
	        // 设置目标图片的实际宽度和高度
	        int targetWidth = 0;
	        int targetHeight = 0;
	        
	//	        缩放策略：
	//	        如果宽度缩放比>高度缩放比就以宽度来缩放，反之以高度缩放
	        if(sourceWidth<267&&sourceHeight<267){
	        	 _logger.info("图片太小无需压缩!");
		         // 设置目标图片的实际宽度和高度
		         targetWidth = sourceWidth;
		         targetHeight = sourceHeight;
	        }else{
	            if(sourceWidth>sourceHeight){
		            targetPicWidth = 267;//宽比高大
		        }else{
		        	targetPicHeight = 267;
		        }
		      
		        if (targetPicWidth != 0) {
		            // 根据设定的宽度求出长度
		            targetWidth = targetPicWidth;
		            targetHeight = (targetWidth * sourceHeight) / sourceWidth;
		        } else if (targetPicHeight != 0) {
		            // 根据设定的长度求出宽度
		            targetHeight = targetPicHeight;
		            targetWidth = (targetHeight * sourceWidth) / sourceHeight;
		        } else {
		            throw new RuntimeException(" 对象参数初始化不正确!  ");
		        }
	        }
	        
			Builder<? extends InputStream> thumbnail = Thumbnails.of(file.getInputStream());
			thumbnail.size(targetWidth, targetHeight);
			thumbnail.keepAspectRatio(false);
			thumbnail.toFile(new File(realUploadPath));
	}
}
