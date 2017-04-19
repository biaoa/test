package com.linle.entity;

import java.io.Serializable;

import com.linle.entity.sys.BaseDomain;

public class SmsInterface extends BaseDomain implements Serializable{

    private String name;

    private Integer type;

    private String address;

    private String param;

    private String content;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}