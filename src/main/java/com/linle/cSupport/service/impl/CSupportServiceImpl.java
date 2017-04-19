package com.linle.cSupport.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.cSupport.mapper.CSupportMapper;
import com.linle.cSupport.service.CSupportService;
import com.linle.cTopic.service.CTopicService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.CSupport;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.request.SupportOperateRequest;
@Service
@Transactional
public class CSupportServiceImpl extends CommonServiceAdpter<CSupport> implements CSupportService {

	@Autowired
	private CSupportMapper mapper;
	
	@Autowired
	private CTopicService cTopicService;
	
	/**
	 * 点赞
	 */
	public boolean supportOperate(SupportOperateRequest req,Users user){
		try {
			long topicId=req.getTopicId();
			//1.增加点赞记录
			CSupport cSupport=new CSupport();
			cSupport.setUserId(user.getId());
			cSupport.setTopicId(topicId);
			cSupport.setIsSupport(true);
			cSupport.setCreateTime(new Date());
			cSupport.setIsDel(0);
			boolean flag = mapper.insertSelective(cSupport)>0;
			//2.修改话题表点赞字段
			CTopic topic=cTopicService.selectByPrimaryKey(topicId);
			long supportNum=topic.getSupportNum()==null?0:topic.getSupportNum();
			CTopic record=new CTopic();
			record.setTopicId(req.getTopicId());
			record.setSupportNum(supportNum+1);
			cTopicService.updateByPrimaryKeySelective(record);
			return flag;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("点赞出错了");
		}
	}
	
	public int selectByTopicIdAndUserid(SupportOperateRequest req,Users user){
		Map<String, Object> map=new HashMap<>();
		map.put("topicId", req.getTopicId());
		map.put("userId", user.getId());
		int count=mapper.selectByTopicIdAndUserid(map);
		return count;
	}
	
	public List<CSupport> selectSupportUsers(long topicId){
		return mapper.selectSupportUsers(topicId);
	}
	
}
