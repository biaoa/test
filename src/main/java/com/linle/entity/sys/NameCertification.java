package com.linle.entity.sys;

public class NameCertification  extends BaseDomain{

	private static final long serialVersionUID = 4027001470520599956L;

	private String name;

    private String cradNo;

    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCradNo() {
        return cradNo;
    }

    public void setCradNo(String cradNo) {
        this.cradNo = cradNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}