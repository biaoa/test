package com.linle.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linle.entity.enumType.StatusType;

public class CommunityEmployee extends BaseDomain{

	private static final long serialVersionUID = 1216188962514232055L;

	private String name;

    private String logo;

    private String level;

    private String phone;

    private Long communityId;

    private Long departmentId;
    
    private CommunityDepartment department;
    @JsonIgnore
    private Users user;
    @JsonIgnore
    private StatusType status;
    
    public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

	public CommunityDepartment getDepartment() {
		return department;
	}

	public void setDepartment(CommunityDepartment department) {
		this.department = department;
	}

}