package com.linle.topicUserManager.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.mobileapi.v1.model.TopicUserManagerVo;
import com.linle.topicUserManager.model.TopicUserManager;

public interface TopicUserManagerService  extends BaseService<TopicUserManager>  {
	
	public Page<TopicUserManager> getAllForPage(Page<TopicUserManager> page) ;
	
	public TopicUserManager selectById(long userId);
	
	public TopicUserManagerVo selectByIdForApi(long userId);
	
	public TopicUserManager selectByUserIdAndTopicTypeId(Long userId,Long typeId,String type);
}
