package com.linle.mobileapi.v1.request;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

public class RepairRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片(支持多张)
	 */
	@NotNull
	private CommonsMultipartFile[] imgs;
	
	/**
	 * 报修内容
	 */
	private String content;
	
	/**
	 * 类型ID
	 */
	private Long typeID;
	
	private String beginDate;
	
	private String endDate;
	
	private	List<CommonsMultipartFile> list;
	private  Long folderId;
	
	@NotNull(message="报修类型不能为空")
	private int parentTypeId;//大类型 0:私人家居 1:公共区域
	
	public int getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(int parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public CommonsMultipartFile[] getImgs() {
		return imgs;
	}

	public void setImgs(CommonsMultipartFile[] imgs) {
		this.imgs = imgs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public List<CommonsMultipartFile> getList() {
		return list;
	}

	public void setList(List<CommonsMultipartFile> list) {
		this.list = list;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getTypeID() {
		return typeID;
	}

	public void setTypeID(Long typeID) {
		this.typeID = typeID;
	}

}
