package com.linle.entity.sys;

/**
 * 
 * @author pangd
 * @Description 商品类型 商家可以自定义
 */
public class CommodityType extends BaseDomain{

	private static final long serialVersionUID = 3522407408817350538L;

	private String typeName;

    private Long shopId;

    private Integer status;

    private Integer sort;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}