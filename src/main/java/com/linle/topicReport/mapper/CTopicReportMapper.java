package com.linle.topicReport.mapper;

import java.util.List;
import java.util.Map;

import com.linle.topicReport.model.CTopicReport;

import component.BaseMapper;

public interface CTopicReportMapper extends BaseMapper<CTopicReport>{

	int selectByTopicIdAndUserid(Map<String, Object> map);

	List<CTopicReport> selectReportUsers(long topicId);

	int selectReportCount(long topicId);
	
	
}