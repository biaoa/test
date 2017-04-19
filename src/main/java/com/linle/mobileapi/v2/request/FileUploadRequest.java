package com.linle.mobileapi.v2.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

public class FileUploadRequest extends BaseRequest {

	private static final long serialVersionUID = -3122694801041359373L;

	@NotNull(message = "文件不能为空")
	private CommonsMultipartFile file;

	@NotBlank(message = "文件夹名称不能为空")
	private String folderName;
	
	private Long folderId;
	
	@NotNull(message="类型不能为空")
	private Integer type; // 0 单张 1 多张

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
