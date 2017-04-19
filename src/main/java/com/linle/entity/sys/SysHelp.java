package com.linle.entity.sys;

import com.linle.entity.enumType.UserStatusType;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月31日
 **/
public class SysHelp extends BaseDomain {
	private String title;
	private String content;
	private String remark;
	private UserStatusType delFlag;
	/**
	 * 所属科创中心
	 */
	private Long centerId;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public UserStatusType getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(UserStatusType delFlag) {
		this.delFlag = delFlag;
	}
	public Long getCenterId() {
		return centerId;
	}
	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}
	
}
