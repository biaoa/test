package com.linle.entity.sys;

/**
 * 
 * @author pangd
 * @Description 订单评价
 */
public class OrderEvaluate extends BaseDomain{

	private static final long serialVersionUID = 5029875939270449015L;

	private Long userId;

    private Long shopId;

    private String orderNo;

    private Integer commodityStar;

    private Integer serviceStar;

    private Integer sendStar;

    private Float complexStar;

    private String content;



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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCommodityStar() {
        return commodityStar;
    }

    public void setCommodityStar(Integer commodityStar) {
        this.commodityStar = commodityStar;
    }

    public Integer getServiceStar() {
        return serviceStar;
    }

    public void setServiceStar(Integer serviceStar) {
        this.serviceStar = serviceStar;
    }

    public Integer getSendStar() {
        return sendStar;
    }

    public void setSendStar(Integer sendStar) {
        this.sendStar = sendStar;
    }

    public Float getComplexStar() {
        return complexStar;
    }

    public void setComplexStar(Float complexStar) {
        this.complexStar = complexStar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}