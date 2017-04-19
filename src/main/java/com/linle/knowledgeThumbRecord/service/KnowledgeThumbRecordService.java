package com.linle.knowledgeThumbRecord.service;

import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.Users;
import com.linle.knowledgeThumbRecord.model.KnowledgeThumbRecord;
import com.linle.mobileapi.base.BaseResponse;

public interface KnowledgeThumbRecordService extends BaseService<KnowledgeThumbRecord> {
	
	/**
	 * 
	 * @Description 点赞
	 * @param id
	 * @param knowledgeId
	 * @param status
	 * @return BaseResponse
	 * $
	 */
	BaseResponse addThumb(Users user, Long knowledgeId, Integer status);
	
	/**
	 * 
	 * @Description 根据用户ID 速报ID 查询点赞记录
	 * @param map
	 * @return KnowledgeThumbRecord
	 * $
	 */
	KnowledgeThumbRecord selectByUserIdAndKnowledgeId(Map<String, Object> map);
}
