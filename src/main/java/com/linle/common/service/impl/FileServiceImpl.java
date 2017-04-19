package com.linle.common.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.service.FileService;
import com.linle.common.util.FileUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.SysFile;
import com.linle.entity.sys.SysFolder;
import com.linle.system.mapper.SysFileMapper;
import com.linle.system.mapper.SysFolderMapper;
import com.linle.system.service.SysFileService;

import javafx.util.Pair;
@Service
public class FileServiceImpl extends CommonServiceAdpter<SysFile> implements FileService {

	@Autowired
	private SysFileMapper sysFileMapper;
	
	@Autowired
	private SysFolderMapper sysFolderMapper;
	
	@Autowired
	private SysFileService sysFileService;
	
    @Override
    public Pair<Long, String> addFile(MultipartFile file, String prePath,String type) {
        Pair<Long, String> pair = null;
        File uploadedFile = null;
        try {
            SysFile sysFile = new SysFile();
            byte[] bytes = file.getBytes();
            String contentType = getDateName(file.getOriginalFilename());///获取文件后缀名
            if (isImage(contentType)) {//验证文件后缀名是否正确
                String path = "upload/" +type+"/"+ dateString();
                String uploadDir = prePath + path;
                File dirPath = new File(uploadDir);
                if (!dirPath.exists()) {
                    dirPath.mkdirs();
                }
                String sep = System.getProperty("file.separator");
                String pathimg = dateStringWithRandomNum() + contentType;
                String fullPahtName = path + "/" + pathimg;
                uploadedFile = new File(uploadDir + sep + pathimg);
                FileCopyUtils.copy(bytes, uploadedFile);

//                CommonsMultipartFile cf= (CommonsMultipartFile)file; 
//                ImageHepler.thumbnail(cf,uploadDir, prePath);
                
                sysFile.setOrgpath(fullPahtName);
                sysFile.setPath(fullPahtName);
                sysFile.setOriginalName(file.getOriginalFilename());
                sysFile.setFileName(pathimg);
                sysFile.setCreateDate(new Date());
                sysFileMapper.insertSelective(sysFile);
                pair = new Pair<Long, String>(sysFile.getId(), fullPahtName);
            }
        } catch (Exception e) {
            uploadedFile.deleteOnExit();
            pair = new Pair<Long, String>(null, "上传文件格式错误");
        }
        return pair;
    }

    @Override
    public SysFile addFiles(SysFile file, Long folderId) {
        SysFolder folder = new SysFolder();
        if (null != file && null != folderId) {
            folder = sysFolderMapper.getByFolderId(folderId);
            file.setFolderId(folder.getId());
        } else {
            if (null != file && sysFolderMapper.insert(folder) > 0) {
                file.setFolderId(folder.getId());
            }
        }
        sysFileMapper.insert(file);
        return file;
    }


    @Override
    public boolean delFileById(Long id) {
        return sysFileMapper.delFileById(id) > 0;
    }
    public boolean delFileByPath(String path) {
        return sysFileMapper.delFileByPath(path) > 0;
    }
    
    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    private final static String getDateName(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(dot);
            }
        }
        return fileName;
    }

    /**
     * 生成日期文件夹
     *
     * @return
     */
    private final static String dateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 生成日期文件名
     *
     * @return
     */
    private final static String dateStringWithRandomNum() {
        StringBuffer number = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        number.append(sdf.format(new Date()));
        number.append(new Random().nextInt(1000000));
        return number.toString();
    }

    /**
     * 验证文件头是否正确
     *
     * @return
     */
    private final static boolean isImage(String contentType) {
        List<String> allowType = Arrays.asList(".bmp", ".png", ".gif", ".jpg", ".jpeg", ".pjpeg");
        return allowType.contains(contentType);

    }


	@Override
	public List<SysFile> getFilesByFolder(Long id) {
		return sysFileMapper.findFilesByFolderId(id);
	}


	@Override
	public Pair<Long, List<SysFile>> addFiles(CommonsMultipartFile[] file, String realPath,Long foldeID,String folderPath) {
		Pair<Long, List<SysFile>> pair = null;
		List<SysFile> sysFiles = new ArrayList<SysFile>();
		if (null != file && file.length > 0) {
			for (CommonsMultipartFile multipartFile : file) {
				String originalName = multipartFile.getOriginalFilename();
				if (!multipartFile.isEmpty()) {
					String path = FileUtil.createFilePath(realPath+folderPath, originalName);
					File localFile = new File(path + FileUtil.createNewFileName(originalName));
					try {
						if (!localFile.exists()) {
							localFile.createNewFile();
						}
						multipartFile.transferTo(localFile);
					} catch (Exception e) {
						e.printStackTrace(); _logger.error("出错了", e);
					}
					SysFile sysFile = new SysFile();
					sysFile.setOriginalName(originalName);
					sysFile.setFileName(localFile.getName());
					sysFile.setPath(FileUtil.getDbPath(path) + localFile.getName());
					sysFiles.add(sysFile);
				}
			}

		}
		SysFolder folder = new SysFolder();
		if (foldeID!=null && foldeID!=0) {
			for (SysFile sysFile : sysFiles) {
				sysFileService.addFiles(sysFile,foldeID).getFolderId();
			}
			folder.setId(foldeID);
		}else{
			 folder = sysFileService.addFiles(sysFiles);
		}
		
		pair = new Pair<Long, List<SysFile>>(folder.getId(),sysFiles);
		return pair;
	}
}
