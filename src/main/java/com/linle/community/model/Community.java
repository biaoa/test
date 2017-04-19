package com.linle.community.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linle.entity.enumType.StatusType;
import com.linle.entity.sys.BaseDomain;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.PropertyCompany;
import com.linle.entity.sys.SysRegion;
import com.linle.entity.sys.Users;
/**
 * 
* @ClassName: Community 
* @Description: 小区信息
* @author pangd
* @date 2016年3月21日 下午1:25:02 
*
 */
public class Community  extends BaseDomain{

	private static final long serialVersionUID = 1354213800991306374L;
	private String name;
    @JsonIgnore
    private PropertyCompany propertyCompany;
    
    private String address;//小区地址
    
    @JsonIgnore
    private String logo;
    
    @JsonIgnore
    private SysRegion sysRegion;
    
    @JsonIgnore
    private Users user;
    
    @JsonIgnore
    private StatusType status;
    
    private String phone;
    
    private CommunityPresident president; //社区社长信息
    
    private String principalName;//负责人名称
    
    private String principalPhone;//电话
    
    private String principalAddress;//地址
    
    private Long level2Proxy; //二级代理
    
    private Long level1Proxy; //一级代理
    
    private Long sysId; //总公司
    
    private Long franchiseesId;//加盟商
    
    private String ownerNotice; //业主须知
    
    private int collaborateFlag; //合作状态 0 未合作 1 已合作
    
    private BigDecimal withdrawalFee; //提现手续费(默认是0.012)
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PropertyCompany getPropertyCompany() {
		return propertyCompany;
	}

	public void setPropertyCompany(PropertyCompany propertyCompany) {
		this.propertyCompany = propertyCompany;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public SysRegion getSysRegion() {
		return sysRegion;
	}

	public void setSysRegion(SysRegion sysRegion) {
		this.sysRegion = sysRegion;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public CommunityPresident getPresident() {
		return president;
	}

	public void setPresident(CommunityPresident president) {
		this.president = president;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPrincipalPhone() {
		return principalPhone;
	}

	public void setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
	}

	public String getPrincipalAddress() {
		return principalAddress;
	}

	public void setPrincipalAddress(String principalAddress) {
		this.principalAddress = principalAddress;
	}

	public Long getLevel2Proxy() {
		return level2Proxy;
	}

	public void setLevel2Proxy(Long level2Proxy) {
		this.level2Proxy = level2Proxy;
	}

	public Long getLevel1Proxy() {
		return level1Proxy;
	}

	public void setLevel1Proxy(Long level1Proxy) {
		this.level1Proxy = level1Proxy;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Long getFranchiseesId() {
		return franchiseesId;
	}

	public void setFranchiseesId(Long franchiseesId) {
		this.franchiseesId = franchiseesId;
	}

	public String getOwnerNotice() {
		return ownerNotice;
	}

	public void setOwnerNotice(String ownerNotice) {
		this.ownerNotice = ownerNotice;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCollaborateFlag() {
		return collaborateFlag;
	}

	public void setCollaborateFlag(int collaborateFlag) {
		this.collaborateFlag = collaborateFlag;
	}

	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}
}