package com.linle.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	public static final Logger _logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 保存文件
	 */
	public static void saveFile( String folderPath, MultipartFile file) {
		String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名.xls
		String fileName = System.currentTimeMillis() + type;// 取当前时间戳作为文件名

		String fulPath = folderPath + "/" + fileName;// 存放位置

		File destFile = new File(fulPath);
		try {
			// FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);// 复制临时文件到指定目录下
		} catch (IOException e) {
			destFile.delete();
			e.printStackTrace(); _logger.error("出错了", e);
		}
	}
	
	public static String createFilePath(String rootPath,String originalName) {
		String fileSuffix = getFileSuffix(originalName);		
		String filePath = rootPath + fileSuffix.substring(1) + "/"
				+ Calendar.getInstance().get(Calendar.YEAR) + "/"
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
				+ (Calendar.getInstance().get(Calendar.DAY_OF_MONTH))+"/";
		File file = new File(filePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		return filePath;
	}
	
	public static String createNewFileName(String originalName) {
		String fileSuffix = getFileSuffix(originalName);
		String newFileName = UUID.randomUUID().toString() + fileSuffix;		
		return newFileName;
	}

	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
	}
	
	public static String getDbPath(String filePath){
		return filePath.substring(filePath.indexOf(SysConfig.UPLOAD_FOLDER));
	}

	/**
	 * 源文件名保存文件
	 */
	public static long saveFileByOriginalFileName(String folderPath, MultipartFile file) {
//		String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名.xls
//		String timeStamp = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
		Long timeStamp = System.currentTimeMillis();// 取当前时间戳作为文件名
//		String fulPath = folderPath + fileName;// 存放位置

		File destFile = new File(folderPath + "/" + timeStamp + "_" + file.getOriginalFilename());
		try {
			// FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);// 复制临时文件到指定目录下
		} catch (IOException e) {
			destFile.delete();
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return timeStamp;
	}
	
	public static String getDateName(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length() - 1))) {
				return fileName.substring(dot);
			}
		}
		return fileName;
	}
	
	/**
	 * 验证文件头是否正确
	 * 
	 * @param formFile
	 * @return
	 */
	public static boolean isExcel(String contentType) {
		List<String> allowType = Arrays.asList(".xls", ".xlsx");
		return allowType.contains(contentType);

	}
	
	/**
	 * 生成日期文件夹
	 * 
	 * @return
	 */
	public static String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	/**
	 * 生成日期文件名
	 * 
	 * @return
	 */
	public static String getFileName() {
		StringBuffer number = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		number.append(sdf.format(new Date()));
		number.append(new Random().nextInt(1000000));
		return number.toString();
	}
	
	public static String getFileEncode(String path) {
	    String charset ="asci";
	    byte[] first3Bytes = new byte[3];
	    BufferedInputStream bis = null;
	    try {
	      boolean checked = false;
	      bis = new BufferedInputStream(new FileInputStream(path));
	      bis.mark(0);
	      int read = bis.read(first3Bytes, 0, 3);
	      if (read == -1)
	        return charset;
	      if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
	        charset = "Unicode";//UTF-16LE
	        checked = true;
	      } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
	        charset = "Unicode";//UTF-16BE
	        checked = true;
	      } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
	        charset = "UTF8";
	        checked = true;
	      }
	      bis.reset();
	      if (!checked) {
	        int len = 0;
	        int loc = 0;
	        while ((read = bis.read()) != -1) {
	          loc++;
	          if (read >= 0xF0)
	            break;
	          if (0x80 <= read && read <= 0xBF) //单独出现BF以下的，也算是GBK
	            break;
	          if (0xC0 <= read && read <= 0xDF) {
	            read = bis.read();
	            if (0x80 <= read && read <= 0xBF) 
	            //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
	              continue;
	            else
	              break;
	          } else if (0xE0 <= read && read <= 0xEF) { //也有可能出错，但是几率较小
	            read = bis.read();
	            if (0x80 <= read && read <= 0xBF) {
	              read = bis.read();
	              if (0x80 <= read && read <= 0xBF) {
	                charset = "UTF-8";
	                break;
	              } else
	                break;
	            } else
	              break;
	          }
	        }
	        //TextLogger.getLogger().info(loc + " " + Integer.toHexString(read));
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (bis != null) {
	        try {
	          bis.close();
	        } catch (IOException ex) {
	        }
	      }
	    }
	    System.out.println(charset);
	    return charset;
	  }
	
	  private static String getEncode(int flag1, int flag2, int flag3) {
		    String encode="";
		    // txt文件的开头会多出几个字节，分别是FF、FE（Unicode）,
		    // FE、FF（Unicode big endian）,EF、BB、BF（UTF-8）
		    if (flag1 == 255 && flag2 == 254) {
		      encode="Unicode";
		    }
		    else if (flag1 == 254 && flag2 == 255) {
		      encode="UTF-16";
		    }
		    else if (flag1 == 239 && flag2 == 187 && flag3 == 191) {
		      encode="UTF8";
		    }
		    else {
		      encode="asci";// ASCII码
		    }
		    return encode;
		  }
	  
	  public static String readFile(String path){
		    String data = null;
		    // 判断文件是否存在
		    File file = new File(path);
		    if(!file.exists()){
		      return data;
		    }
		    // 获取文件编码格式
		    String code = getFileEncode(path);
		    InputStreamReader isr = null;
		    try{
		      // 根据编码格式解析文件
		      if("asci".equals(code)){
		        // 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码 
		        // code = System.getProperty("file.encoding");
		        code = "GBK";
		      }
		      isr = new InputStreamReader(new FileInputStream(file),code);
		      // 读取文件内容
		      int length = -1 ;
		      char[] buffer = new char[1024];
		      StringBuffer sb = new StringBuffer();
		      while((length = isr.read(buffer, 0, 1024) ) != -1){
		        sb.append(buffer,0,length);
		      }
		      data = new String(sb);
		    }catch(Exception e){
		      e.printStackTrace();
		    }finally{
		      try {
		        if(isr != null){
		          isr.close();
		        }
		      } catch (IOException e) {
		        e.printStackTrace();
		      }
		    }
		    return data;
		  }
}
