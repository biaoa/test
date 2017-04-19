package com.linle.cTopic.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CTopicVO;

public interface CTopicService extends BaseService<CTopic> {
	
	//获取某个类型所有话题
	public List<CTopicVO> getTopicListForApi(Map<String, Object> map);
	
	public CTopicVO getTopicByTopicIdForApi(Map<String, Object> map);
	
	//获取某个人发布所有话题
	public List<CTopicVO> getTopicListByUserIdForApi(Map<String, Object> map);
	
	public boolean deleteTopicById(long topicId);
	
	public Page<CTopic> getAllTopicForPage(Page<CTopic> page);
	
	public BaseResponse add(CTopic topic,Users user);
	/**
	 * 
	 * @Description  圈子是否有新内容
	 * @param map
	 * @return int
	 * $
	 */
	public int hasNewTopic(Map<String, Object> map);
}
