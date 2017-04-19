package com.linle.mobileapi.v1.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 小区居民的投诉与建议
 */
public class CommunitySuggestionsRequest extends BaseRequest {

	private static final long serialVersionUID = -7623701878449765694L;
	
	@NotEmpty(message="反馈内容不能为空")
	private String advice;
	
	private	List<CommonsMultipartFile> list;

	private  Long folderId;
	

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public List<CommonsMultipartFile> getList() {
		return list;
	}

	public void setList(List<CommonsMultipartFile> list) {
		this.list = list;
	}

}
