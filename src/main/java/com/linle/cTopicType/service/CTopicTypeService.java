package com.linle.cTopicType.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CTopicType;

public interface CTopicTypeService  extends BaseService<CTopicType> {
	
	public List<CTopicType> getAllType();
	
	public Page<CTopicType> getAllTypeForPage(Page<CTopicType> page) ;
	
	int getTypeUnreadCount(Map<String, Object> map);
}
