package com.linle.system.service;

import java.util.List;

import com.linle.entity.sys.SysFile;
import com.linle.entity.sys.SysFolder;

/**
 * @描述:文件处理
 * @作者:杨立忠
 * @创建时间：2015年9月1日
 **/
public interface SysFileService {
	
	public SysFolder addFiles(List<SysFile> files);
	
	public SysFolder loadFolderById(Long id);
	
	public List<SysFile> findFilesByFolderId(Long folderId);
	
	public boolean deleteFolder(Long id);
	
	public SysFile addFile(SysFile files);
	
	public SysFile addFiles(SysFile files,Long folderId);
	
	public boolean delFileById(Long id);
	
	public boolean addFileNoFolder(SysFile files);
	
	public boolean addFileHasFolder(List<SysFile> files,Long folderId);
	
}
