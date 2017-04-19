package com.linle.entity;

import java.io.Serializable;
import java.util.Date;

import com.linle.entity.sys.BaseDomain;

public class SmsRecord extends BaseDomain implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer type;

    private String phone;

    private String code;

    private String content;
    
    private String result;

    private Integer status;

    private Date senddate;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }
    
}