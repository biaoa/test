package com.linle.entity.sys;

/**
 * 
* @ClassName: Garage 
* @Description: 车库
* @author pangd
* @date 2016年4月5日 上午10:35:35 
*
 */
public class Garage extends BaseDomain{

	private static final long serialVersionUID = 7316752734921450171L;

	private String name;

    private Integer garageLength;

    private Integer garageWidth;

    private Integer sort;

    private Long communityId;
    
    private int useCount; // 使用中的车库数量
    
    private int delFlag; //0 正常 1 已删除
    
    private int spaceCount;//车位总数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGarageLength() {
        return garageLength;
    }

    public void setGarageLength(Integer garageLength) {
        this.garageLength = garageLength;
    }

    public Integer getGarageWidth() {
        return garageWidth;
    }

    public void setGarageWidth(Integer garageWidth) {
        this.garageWidth = garageWidth;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
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