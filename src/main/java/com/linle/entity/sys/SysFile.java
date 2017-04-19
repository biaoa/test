package com.linle.entity.sys;

import java.util.Date;

public class SysFile {


    private Long id;

    private Long folderId;

    private String path;

    private String fileName;

    private String originalName;

    private Date createDate;

    private Date updateDate;

    private String orgpath;
    
    private String style;
    
    private String thumPath;

    private String thumName;
    
    
    public String getThumPath() {
		return thumPath;
	}

	public void setThumPath(String thumPath) {
		this.thumPath = thumPath;
	}

	public String getThumName() {
		return thumName;
	}

	public void setThumName(String thumName) {
		this.thumName = thumName;
	}

	public String getOrgpath() {
        return orgpath;
    }

    public void setOrgpath(String orgpath) {
        this.orgpath = orgpath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPath_() {
        return path;
    }

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}