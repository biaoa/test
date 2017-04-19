package com.linle.init;

import java.util.Date;

public class Script {
	private String fileName;
	private String virsion;
	private long fileLastModifiedAt;
	private String checksum;
	private Date executedAt;
	private int succeeded;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVirsion() {
		return virsion;
	}

	public void setVirsion(String virsion) {
		this.virsion = virsion;
	}

	public long getFileLastModifiedAt() {
		return fileLastModifiedAt;
	}

	public void setFileLastModifiedAt(long fileLastModifiedAt) {
		this.fileLastModifiedAt = fileLastModifiedAt;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public Date getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(Date executedAt) {
		this.executedAt = executedAt;
	}

	public int getSucceeded() {
		return succeeded;
	}

	public void setSucceeded(int succeeded) {
		this.succeeded = succeeded;
	}
}
