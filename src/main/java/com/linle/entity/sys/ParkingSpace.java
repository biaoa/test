package com.linle.entity.sys;

public class ParkingSpace extends BaseDomain{

	private static final long serialVersionUID = 9003799616420353290L;

	private String space;

    private String price;

    private String solded;
    
    private Long garageId;
    
    private int delFlag;
    
    private int spaceCount; //车位总数

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSolded() {
        return solded;
    }

    public void setSolded(String solded) {
        this.solded = solded;
    }

	public Long getGarageId() {
		return garageId;
	}

	public void setGarageId(Long garageId) {
		this.garageId = garageId;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getSpaceCount() {
		return spaceCount;
	}

	public void setSpaceCount(int spaceCount) {
		this.spaceCount = spaceCount;
	}

}