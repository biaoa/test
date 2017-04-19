package com.linle.user.model;

import javax.validation.constraints.NotNull;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月29日
 **/
public class ResourceInfo {
	@NotNull(message="参数错误")
	private Long id;
	private String intro;
	private String trait;
	/**
	 * 服务范围
	 */
	private String servicRange;
	/**
	 *联系方式
	 */
	private String contactContent;

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTrait() {
		return trait;
	}

	public void setTrait(String trait) {
		this.trait = trait;
	}

	public String getServicRange() {
		return servicRange;
	}

	public void setServicRange(String servicRange) {
		this.servicRange = servicRange;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactContent() {
		return contactContent;
	}

	public void setContactContent(String contactContent) {
		this.contactContent = contactContent;
	}
	
	
}
