package com.linle.cTopicComment.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.CTopicComment;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.model.CTopicCommentVO;
import com.linle.mobileapi.v1.model.TopicReplyCommentVO;
import com.linle.mobileapi.v1.request.CommentOperateRequest;

public interface CTopicCommentService extends BaseService<CTopicComment> {
	
	public List<CTopicCommentVO> getTopicDetailForApi(Map<String, Object> map);
	
	public long commentOperate(CommentOperateRequest req,Users user);
	
	public CTopicCommentVO addTopicComment(CTopicComment comment,Users user);
	
	public List<TopicReplyCommentVO> getTopicReplyListForApi(Map<String, Object> map);
	
	public int getUnReadCount(Map<String, Object> map) ;
	
	public boolean updateReadCommentByUserid(Users user);

	public boolean deleteTopicCommentById(long commentId);
	
	public boolean updateCommentDelByTopicId(long topicId);
	
	public boolean removeReplyCommentById(long commentId);
}
