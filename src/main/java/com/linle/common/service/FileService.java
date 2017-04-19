package com.linle.common.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.SysFile;

import javafx.util.Pair;

@Service
public interface FileService extends BaseService<SysFile> {

    Pair<Long, String> addFile(MultipartFile file, String filename,String type) throws Exception;

    public SysFile addFiles(SysFile files,Long folderId);
    
    public boolean delFileById(Long id);
    public boolean delFileByPath(String path);
    
    public List<SysFile> getFilesByFolder(Long id);

	Pair<Long, List<SysFile>> addFiles(CommonsMultipartFile[] file, String realPath,Long foldeID,String folderPath);

}
