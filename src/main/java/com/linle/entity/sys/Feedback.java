package com.linle.entity.sys;

public class Feedback extends BaseDomain{

	private static final long serialVersionUID = -659965003408524086L;

	private String content;

    private String mobileInfo;

    private String sysVersion;

    private Long userId;
    
    private String type;
    
    private String mobileVersion;

    private Users user;
    
    private Integer delFlag;//删除状态  0 正常 1 删除
    
    public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobileInfo() {
        return mobileInfo;
    }

    public void setMobileInfo(String mobileInfo) {
        this.mobileInfo = mobileInfo;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobileVersion() {
		return mobileVersion;
	}

	public void setMobileVersion(String mobileVersion) {
		this.mobileVersion = mobileVersion;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}