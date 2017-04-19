package com.linle.applyCooperae.model;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description 申请合作
 */
public class ApplyCooperae extends BaseDomain{

	private static final long serialVersionUID = 4964742699502953201L;

	private String city;

    private String name;

    private String phone;

    private String qq;

    private String remark;

    private String ip;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}