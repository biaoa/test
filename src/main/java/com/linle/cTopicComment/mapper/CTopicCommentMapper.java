package com.linle.cTopicComment.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CTopicComment;
import com.linle.mobileapi.v1.model.CTopicCommentVO;
import com.linle.mobileapi.v1.model.TopicReplyCommentVO;

import component.BaseMapper;

public interface CTopicCommentMapper extends BaseMapper<CTopicComment>{
	public List<CTopicCommentVO> getTopicDetailForApi(Map<String, Object> map);
	
	public List<TopicReplyCommentVO> getTopicReplyListForApi(Map<String, Object> map) ;
	
	public int getUnReadCount(Map<String, Object> map);
	
	public void updateReadCommentByUserid(CTopicComment comment);
	
	public int updateCommentDelByTopicId(CTopicComment comment);

	public CTopicCommentVO getTopicComment(long commentId);
	
}