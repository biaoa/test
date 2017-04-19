package com.linle.cTopicReadRecord.service;



import java.util.List;
import java.util.Map;

import com.linle.cTopicReadRecord.model.CTopicReadRecord;
import com.linle.common.base.BaseService;
import com.linle.mobileapi.v1.model.TopicUnreadVO;

public interface CTopicReadRecordService extends BaseService<CTopicReadRecord> {
	
	//某个类型的阅读记录是否存在
	boolean recordExist(Long userId,Long typeId);
	
	//修改阅读记录的最后阅读时间
	boolean updateReadRecord(Long id, Long typeId);
	
	List<TopicUnreadVO> getTopicUnreadList(Map<String, Object> map);
}
