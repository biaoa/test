package com.linle.entity.sys;

import java.math.BigDecimal;

/**
 * 
 * @author pangd 
 * @Description 商品信息
 */
public class Commodity extends BaseDomain{

	private static final long serialVersionUID = -713245486909929901L;

	private String name;

    private String logo;

    private BigDecimal price;

    private Integer quantity;

    private Long shopId;

//    private Long typeId;
    private CommodityType type;

    private String introduction;

    private Integer status;
    
    private Integer activityFlag;//是否参与活动标识
    
    private int genre; //商品类型 0 商品 1 服务
    
    private String serverScope; //服务范围
    
    private String serverDetail; //服务明细
    
    private String description; //商品简介


    public Integer getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(Integer activityFlag) {
		this.activityFlag = activityFlag;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public CommodityType getType() {
		return type;
	}

	public void setType(CommodityType type) {
		this.type = type;
	}

	public String getServerScope() {
		return serverScope;
	}

	public void setServerScope(String serverScope) {
		this.serverScope = serverScope;
	}

	public String getServerDetail() {
		return serverDetail;
	}

	public void setServerDetail(String serverDetail) {
		this.serverDetail = serverDetail;
	}

	public int getGenre() {
		return genre;
	}

	public void setGenre(int genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}