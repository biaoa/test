package com.linle.system.mapper;

import java.util.List;

import com.linle.entity.sys.SysFile;
import com.linle.entity.sys.SysFolder;

import component.BaseMapper;

public interface SysFolderMapper extends BaseMapper<SysFolder> {
	
	public SysFolder getByFolderId(Long id);

	public List<SysFile> deleteByFoldId(Long id);
	
}