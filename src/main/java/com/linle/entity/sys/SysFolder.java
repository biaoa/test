package com.linle.entity.sys;

import java.util.List;

import com.linle.entity.sys.BaseDomain;

public class SysFolder extends BaseDomain{
	private List<SysFile> files;
	

	public List<SysFile> getFiles() {
		return files;
	}


	public void setFiles(List<SysFile> files) {
		this.files = files;
	}


	private static final long serialVersionUID = -3340372934138930506L;

}