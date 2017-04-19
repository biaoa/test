package com.linle.entity.sys;

public class CommunityDepartment extends BaseDomain{

	private static final long serialVersionUID = 1824471141896238327L;

	private String name;

    private String workBegin;

    private String workEnd;

    private String phone;

    private String introduce;

    private Long communityId;

    
    private int count;//部门总人数
    
    private String logo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getWorkBegin() {
		return workBegin;
	}

	public void setWorkBegin(String workBegin) {
		this.workBegin = workBegin;
	}

	public String getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(String workEnd) {
		this.workEnd = workEnd;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}