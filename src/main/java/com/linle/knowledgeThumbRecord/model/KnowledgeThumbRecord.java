package com.linle.knowledgeThumbRecord.model;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * 
 * @author pangd
 * @Description 点赞记录
 * @date 2016年10月20日下午2:34:32
 */
public class KnowledgeThumbRecord extends BaseDomain{

    private static final long serialVersionUID = -6768568260383375142L;

	private Long uid;

    private Long communityId;

    private Long knowledgeId;

    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}