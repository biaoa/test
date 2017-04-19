package com.linle.appBanner.model;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description appBanner访问记录
 * @date 2016年8月30日下午2:21:44
 */
public class AppbannerAccessRecord extends BaseDomain{

    private static final long serialVersionUID = 7555680759622689555L;

	private Long uid;

    private Long communityId;

    private Long bannerId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }
}