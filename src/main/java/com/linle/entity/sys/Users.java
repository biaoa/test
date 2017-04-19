package com.linle.entity.sys;

import com.alibaba.fastjson.JSON;
import com.linle.community.model.Community;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;

import java.util.Date;

public class Users extends BaseDomain {

    /**
     * 用户基本表
     */
    private static final long serialVersionUID = 1L;
    private String userName;
    private String name;
    private String mobilePhone;
    private String password;
    private String salt;
    private UserStatusType status;
    private UserType identity;
    private UserStatusType delFlag;
    
    private Community community; //小区
    private String communityName;
    private RoomNo addressDetails; //地址信息补充

    private String email;
    
    private String logo; //用户头像
    
    private String sex;
    
    private String token; // 融云token
    
    private Date lastLoginDate;
    
    private Date lastChageAddressDate;

    private String reason;
    
    
    public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserStatusType getStatus() {
        return status;
    }

    public void setStatus(UserStatusType status) {
        this.status = status;
    }

    public UserType getIdentity() {
        return identity;
    }

    public void setIdentity(UserType identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public UserStatusType getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(UserStatusType delFlag) {
        this.delFlag = delFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public RoomNo getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(RoomNo addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastChageAddressDate() {
		return lastChageAddressDate;
	}

	public void setLastChageAddressDate(Date lastChageAddressDate) {
		this.lastChageAddressDate = lastChageAddressDate;
	}

}
