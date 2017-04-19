package com.linle.system.mapper;

import java.util.List;

import com.linle.entity.sys.SysFile;

import component.BaseMapper;

public interface SysFileMapper extends BaseMapper<SysFile> {

	Integer delFileById(Long id);

	List<SysFile> findFilesByFolderId(Long folderId);

	int delFileByPath(String path);
	
}