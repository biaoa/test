package com.linle.system.modle;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月31日
 **/
public class HelpInfo {
	private long id;
	@NotBlank (message="标题不能为空")
	private String title;
	@NotBlank(message="内容不能为空")
	private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
