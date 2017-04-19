package com.linle.mobileapi.v1.request;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

public class TopicRequest extends BaseRequest {

	/**
	 * 图片(支持多张)
	 */
	private CommonsMultipartFile[] imgs;
	
	/**
	 * 内容
	 */
	@NotEmpty(message="消息内容不能为空")
	private String content;
	
	/**
	 * 类型ID
	 */
	@NotNull(message="话题类型不能为空")
	private Long topicTypeId;
	
	private	List<CommonsMultipartFile> list;
	
	private  Long folderId;
	

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getTopicTypeId() {
		return topicTypeId;
	}
	public void setTopicTypeId(Long topicTypeId) {
		this.topicTypeId = topicTypeId;
	}
	public List<CommonsMultipartFile> getList() {
		return list;
	}
	public void setList(List<CommonsMultipartFile> list) {
		this.list = list;
	}

	public CommonsMultipartFile[] getImgs() {
		return imgs;
	}
	public void setImgs(CommonsMultipartFile[] imgs) {
		this.imgs = imgs;
	}

	

}
