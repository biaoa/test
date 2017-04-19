package com.linle.entity.sys;
/**
 * 
* @ClassName: PropertyCompany 
* @Description: 物业公司
* @author pangd
* @date 2016年3月20日 下午5:31:54 
*
 */
public class PropertyCompany extends BaseDomain{

	private static final long serialVersionUID = 7621287248704422534L;

	private Long id;

    private String name;

    private Byte state;

    private SysRegion sysRegion;
    
    private String logo;
    
    private String addressDetails;
    
    private String phone;
    
    private String workTime;
    
    private String synopsis;//简介
    
    private Long createUserId;//创建人ID
    
    //添加物业信息的时候，工作时间和电话
    private String work1;
    private String work2;
    private String phone1;
    private String phone2;
    
    private Users user;
    
    private String principalName;//负责人名称
    
    private String principalPhone;//电话
    
    private String principalAddress;//地址
    
    private String ownerNotice; //业主须知
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

	public SysRegion getSysRegion() {
		return sysRegion;
	}

	public void setSysRegion(SysRegion sysRegion) {
		this.sysRegion = sysRegion;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getWork1() {
		return work1;
	}

	public void setWork1(String work1) {
		this.work1 = work1;
	}

	public String getWork2() {
		return work2;
	}

	public void setWork2(String work2) {
		this.work2 = work2;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
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

	public String getOwnerNotice() {
		return ownerNotice;
	}

	public void setOwnerNotice(String ownerNotice) {
		this.ownerNotice = ownerNotice;
	}

}