package com.linle.entity.sys;

import com.linle.community.model.Community;

/**
 * 
 * @author pangd
 * @Description 订单退款
 */
public class OrderRefund extends BaseDomain{

	private static final long serialVersionUID = 4661414701981090361L;

	private String orderNo;

    private Long userId;

    private Long shopId;

    private Long communityId;

    private String description;

    private Long folderId;

    private Integer status;
    
    private String refundType; //退款原因
    
    private SysFolder folder; //用户反馈的图片
    
    private Shop shop; //商家信息
    
    private Community community; //小区信息
    
    private Users user;
    
    private String failReason; //退款失败原因
    
    private String chargeId;
    

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public SysFolder getFolder() {
		return folder;
	}

	public void setFolder(SysFolder folder) {
		this.folder = folder;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

}