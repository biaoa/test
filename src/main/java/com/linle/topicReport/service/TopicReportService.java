package com.linle.topicReport.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.request.ReportOperateRequest;
import com.linle.topicReport.model.CTopicReport;

public interface TopicReportService extends BaseService<CTopicReport>{

	boolean addTopicReport(ReportOperateRequest req, Users user);

	int selectByTopicIdAndUserid(ReportOperateRequest req, Users user);

	List<CTopicReport> selectReportUsers(long topicId);

	int selectReportCount(long topicId);

}
