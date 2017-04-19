package com.linle.topicReport.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.cTopic.service.CTopicService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.CSupport;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.request.ReportOperateRequest;
import com.linle.mobileapi.v1.request.SupportOperateRequest;
import com.linle.topicReport.mapper.CTopicReportMapper;
import com.linle.topicReport.model.CTopicReport;
import com.linle.topicReport.service.TopicReportService;

@Service
@Transactional
public class TopicReportServiceImpl extends CommonServiceAdpter<CTopicReport> implements TopicReportService {
	@Autowired
	private CTopicReportMapper mapper;
	
	@Autowired
	private CTopicService cTopicService;
	
	/**
	 * 举报
	 */
	@Override
	public boolean addTopicReport(ReportOperateRequest req,Users user){
		try {
			long topicId=req.getTopicId();
			//1.增加举报记录
			CTopicReport record=new CTopicReport();
			record.setUserId(user.getId());
			record.setTopicId(topicId);
			record.setIsReport(true);
			record.setCreateTime(new Date());
			record.setIsDel(0);
			mapper.insertSelective(record);
			int reportCount=this.selectReportCount(topicId);
			if(reportCount>5){//举报次数超过5个，将删除话题
				cTopicService.deleteTopicById(topicId);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			throw new RuntimeException("举报出错了");
		}
	}
	
	/**
	 * 判断是否举报过
	 */
	@Override
	public int selectByTopicIdAndUserid(ReportOperateRequest req,Users user){
		Map<String, Object> map=new HashMap<>();
		map.put("topicId", req.getTopicId());
		map.put("userId", user.getId());
		int count=mapper.selectByTopicIdAndUserid(map);
		return count;
	}
	/**
	 * 获得某话题，所有举报用户
	 * @param topicId
	 * @return
	 */
	@Override
	public List<CTopicReport> selectReportUsers(long topicId){
		return mapper.selectReportUsers(topicId);
	}
	
	/**
	 * 获得某话题被举报总数
	 * @param topicId
	 * @return
	 */
	@Override
	public int selectReportCount(long topicId){
		return mapper.selectReportCount(topicId);
	}
	
	
}
