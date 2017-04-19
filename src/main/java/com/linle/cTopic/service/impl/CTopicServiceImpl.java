package com.linle.cTopic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.cTopic.mapper.CTopicMapper;
import com.linle.cTopic.service.CTopicService;
import com.linle.cTopicComment.service.CTopicCommentService;
import com.linle.cTopicReadRecord.service.CTopicReadRecordService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CTopicVO;
import com.linle.mobileapi.v1.model.TopicUnreadVO;

@Service
@Transactional
public class CTopicServiceImpl extends CommonServiceAdpter<CTopic> implements CTopicService {

	@Autowired
	private CTopicMapper mapper;

	@Autowired
	private CTopicCommentService cTopicCommentService;
	
	@Autowired
	private CTopicReadRecordService readRecordService;

	@Override
	public List<CTopicVO> getTopicListForApi(Map<String, Object> map) {
		return mapper.getTopicListForApi(map);
	}

	@Override
	public List<CTopicVO> getTopicListByUserIdForApi(Map<String, Object> map) {
		return mapper.getTopicListByUserIdForApi(map);
	}

	@Override
	public CTopicVO getTopicByTopicIdForApi(Map<String, Object> map) {
		return mapper.getTopicByTopicIdForApi(map);
	}

	@Override
	public boolean deleteTopicById(long topicId) {
		try {
			CTopic t = mapper.selectByPrimaryKey(topicId);
			if (t.getIsDel() != 1) {
				// 删除话题
				CTopic topic = new CTopic();
				topic.setTopicId(topicId);
				topic.setIsDel(1);
				int count = mapper.updateByPrimaryKeySelective(topic);
				// 删除该话题所有评论回复
				cTopicCommentService.updateCommentDelByTopicId(topicId);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			throw new RuntimeException("删除话题出错了");
		}
	}

	@Override
	public Page<CTopic> getAllTopicForPage(Page<CTopic> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	public BaseResponse add(CTopic topic, Users user) {
		try {
			topic.setUserId(user.getId());
			topic.setCreateTime(new Date());
			topic.setIsDel(0);
			boolean isok = mapper.insertSelective(topic) > 0;
			return isok ? BaseResponse.AddSuccess : BaseResponse.AddFail;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			_logger.error("出错了", e);
			_logger.info("创建话题出错了");
			return BaseResponse.ServerException;
		}
	}

	@Override
	public int hasNewTopic(Map<String, Object> map) {
		//获得分类列表和最后阅读时间
		List<TopicUnreadVO> list = readRecordService.getTopicUnreadList(map);
		int i = 1;
		for (TopicUnreadVO topicUnreadVO : list) {
			if(topicUnreadVO.getUnreadCount()>0 && i==1){
				return 0;
			}
		}
		return i;
	}

}
