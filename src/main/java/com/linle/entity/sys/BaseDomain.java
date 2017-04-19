package com.linle.entity.sys;


import com.linle.common.util.JsonUtil;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseDomain implements Serializable {

	private static final long serialVersionUID = 7450845850686904625L;
	private Long id;
	
	private Date createDate;
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

/*	public String toString() {
		// return ToStringBuilder.reflectionToString(this);
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.id).toString();
	}*/
	@Override
	public String toString() {
		return JsonUtil.toJSon(this);
	}
	public boolean equals(BaseDomain obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaseDomain)) {
			return false;
		}

		if ((getClass().isAssignableFrom(obj.getClass()))
				|| (obj.getClass().isAssignableFrom(getClass()))) {

		} else {
			return false;
		}

		BaseDomain other = (BaseDomain) obj;
		if (other.getId() == null || getId() == null) {
			return false;
		} else {
			if (other.getId().equals(getId())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : super.hashCode());
	}
}
