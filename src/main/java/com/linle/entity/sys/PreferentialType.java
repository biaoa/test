package com.linle.entity.sys;

/**
 * 
 * @author pangd
 * @Description 优惠类型
 */
public class PreferentialType extends BaseDomain{

	private static final long serialVersionUID = -4531572778539017156L;

	private String description;

    private String type;

    private Integer status;
    
    private String name;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}