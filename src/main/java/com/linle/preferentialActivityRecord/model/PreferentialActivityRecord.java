package com.linle.preferentialActivityRecord.model;

import java.math.BigDecimal;

import com.linle.entity.sys.BaseDomain;
import com.linle.entity.sys.Users;
import com.linle.preferentialActivity.model.PreferentialActivity;

/**
 * 
 * @author pangd
 * @Description 优惠活动记录
 * @date 2016年9月20日下午5:52:30
 */
public class PreferentialActivityRecord  extends BaseDomain{

    private static final long serialVersionUID = -2492690777683339081L;

	private Long activityId;

    private String orderNo;

    private BigDecimal preferentialAmount;

    private Integer payFlag;
    
    private Long uid;

    private Users user;
    
    private PreferentialActivity preferentialActivity;
    
    public PreferentialActivity getPreferentialActivity() {
		return preferentialActivity;
	}

	public void setPreferentialActivity(PreferentialActivity preferentialActivity) {
		this.preferentialActivity = preferentialActivity;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public Integer getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}