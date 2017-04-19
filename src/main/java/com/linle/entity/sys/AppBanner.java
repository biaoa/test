package com.linle.entity.sys;

import java.util.List;

public class AppBanner extends BaseDomain{

	private static final long serialVersionUID = 3346427386019722511L;

	private String imgUrl;

    private Integer isTop;

    private Long userId;

    private Integer delFlag;

    private String content;
    
    private String description;
    
    private Integer type; // 0 全部小区 1 部分小区
    
    private String communityList; //小区列表
    
    private Integer hasH5; //是否有H5

    private Integer orderNo;//排序
    
    private String bannerUrl;//banner链接(有可能是内部的,有可能是外部的)
    
    public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public class UserListForm {
	    private List<AppBanner> banner;

		public List<AppBanner> getBanner() {
			return banner;
		}

		public void setBanner(List<AppBanner> banner) {
			this.banner = banner;
		}
	    

	}


	public String getCommunityList() {
		return communityList;
	}

	public void setCommunityList(String communityList) {
		this.communityList = communityList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getHasH5() {
		return hasH5;
	}

	public void setHasH5(Integer hasH5) {
		this.hasH5 = hasH5;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

}