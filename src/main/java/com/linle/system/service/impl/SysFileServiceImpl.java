package com.linle.system.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.util.SysConfig;
import com.linle.entity.sys.SysFile;
import com.linle.entity.sys.SysFolder;
import com.linle.system.mapper.SysFileMapper;
import com.linle.system.mapper.SysFolderMapper;
import com.linle.system.service.SysFileService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月1日
 **/
@Service("sysFileService")
@Transactional
public class SysFileServiceImpl implements SysFileService{
	
	@Autowired
	private SysFolderMapper folderMapper;
	@Autowired
	private SysFileMapper fileMapper;

	@Override
	public SysFolder addFiles(List<SysFile> files) {
		SysFolder folder = new SysFolder();
		if (null != files && files.size() > 0
				&& folderMapper.insert(folder) > 0) {
			for (SysFile file : files) {
				file.setFolderId(folder.getId());
				fileMapper.insertSelective(file);
			}
			return folder;
		}
		return null;
	}
	
	@Override
	public SysFile addFile(SysFile files) {
		SysFolder folder = new SysFolder();
		if (null != files 
				&& folderMapper.insert(folder) > 0) {
			files.setFolderId(folder.getId());
				fileMapper.insertSelective(files);
			return files;
		}
		return null;
	}
	
	@Override
	public SysFile addFiles(SysFile files,Long folderId){
		SysFolder folder = new SysFolder();
		if (null!=files&&null!=folderId) {
			folder=folderMapper.getByFolderId(folderId);
			files.setFolderId(folder.getId());
		}else {
			if (null!=files&&folderMapper.insert(folder) > 0) {
				files.setFolderId(folder.getId());
			}
		}
		fileMapper.insertSelective(files);
		return files;
	}

	@Override
	public SysFolder loadFolderById(Long id) {
		return folderMapper.getByFolderId(id);

	}

	@Override
	public List<SysFile> findFilesByFolderId(Long folderId) {
		return fileMapper.findFilesByFolderId(folderId);

	}

	@Override
	public boolean deleteFolder(Long id) {
		List<SysFile> sysFiles=folderMapper.deleteByFoldId(id);
		if (null!=sysFiles&&sysFiles.size()>0) {
			for (SysFile sysfile:sysFiles) {
				File file = new File(SysConfig.UPLOAD_FOLDER+"/"+sysfile.getPath());   
		        if(file.exists()){     
		            file.delete(); 
		        }
			}
		}
		return true;
	}
	@Override
	public boolean delFileById(Long id){
		return fileMapper.delFileById(id)>0;
	}

	@Override
	public boolean addFileNoFolder(SysFile files) {
		return fileMapper.insertSelective(files)>0;
	}

	@Override
	public boolean addFileHasFolder(List<SysFile> files, Long folderId) {
		for(SysFile file : files){
			file.setFolderId(folderId);
			fileMapper.insertSelective(file);
		}
		return true;
	}

}
