package com.linle.knowledgeAccessRecord.model;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description 邻乐速报阅读记录
 * @date 2016年10月20日上午11:09:44
 */
public class KnowledgeAccessRecord extends BaseDomain{

    private static final long serialVersionUID = 7461545621216177479L;

	private Long uid;

    private Long communityId;

    private Long knowledgeId;

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

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
}