package com.linle.entity.sys;

import java.math.BigDecimal;

public class Shop extends BaseDomain{

	private static final long serialVersionUID = 7661330772581657912L;

	private String shopName;

    private String shopAddress;

    private String shopPhone;

    private String shopLogo;

    private String deliveryName;

    private Integer operateStatus;

    private String enterCommunity;

    private String operateBegin;

    private String operateEnd;

    private Integer shopStatus;
    
    private ShopMainType mainType;
    
    private ShopChildType childType;

    private Integer shopCredentials;

    private BigDecimal deliveryFee; //配送费

    private Users createUser;

    private Users user;
    
    private String introduction;//商家介绍
    
    private BigDecimal sendPrice; //起送价
    
    private String notice;//商家公告
    
    private int refundTime; //商家允许多少小时内可以退款
    
    private SysFolder folder;
    
    private Float cut; //抽成比例
    
    private BigDecimal preferentialCutAmount; //优惠抽成金额

    private Integer activityFlag;//是否参与活动标识
    
	private float grade;//评价等级 先不计算
	
	private BigDecimal withdrawalFee;//提现手续费  默认是0.012
	 
    public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public Integer getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(Integer activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public Integer getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Integer operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getEnterCommunity() {
        return enterCommunity;
    }

    public void setEnterCommunity(String enterCommunity) {
        this.enterCommunity = enterCommunity;
    }

    public String getOperateBegin() {
        return operateBegin;
    }

    public void setOperateBegin(String operateBegin) {
        this.operateBegin = operateBegin;
    }

    public String getOperateEnd() {
        return operateEnd;
    }

    public void setOperateEnd(String operateEnd) {
        this.operateEnd = operateEnd;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public Integer getShopCredentials() {
        return shopCredentials;
    }

    public void setShopCredentials(Integer shopCredentials) {
        this.shopCredentials = shopCredentials;
    }

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public ShopMainType getMainType() {
		return mainType;
	}

	public void setMainType(ShopMainType mainType) {
		this.mainType = mainType;
	}

	public ShopChildType getChildType() {
		return childType;
	}

	public void setChildType(ShopChildType childType) {
		this.childType = childType;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(int refundTime) {
		this.refundTime = refundTime;
	}

	public SysFolder getFolder() {
		return folder;
	}

	public void setFolder(SysFolder folder) {
		this.folder = folder;
	}

	public Float getCut() {
		return cut;
	}

	public void setCut(Float cut) {
		this.cut = cut;
	}

	public BigDecimal getPreferentialCutAmount() {
		return preferentialCutAmount;
	}

	public void setPreferentialCutAmount(BigDecimal preferentialCutAmount) {
		this.preferentialCutAmount = preferentialCutAmount;
	}

	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}
}