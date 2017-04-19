package com.linle.entity.sys;
/**
 * 
* @ClassName: RegionalAgency 
* @Description: 区域代理
* @author pangd
* @date 2016年3月20日 下午5:32:16 
*
 */
public class RegionalAgency extends BaseDomain{

	private static final long serialVersionUID = 6661300281609292328L;

	private String name;

    private SysRegion province;
    
    private SysRegion city;
    private Byte state;
    
    private String phone;
    
    private Users user;
    
    private String type;
    
    private Long createUserId;//创建人ID
    
    private String address;

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

	public SysRegion getProvince() {
		return province;
	}

	public void setProvince(SysRegion province) {
		this.province = province;
	}

	public SysRegion getCity() {
		return city;
	}

	public void setCity(SysRegion city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}