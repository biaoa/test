package com.linle.feedback.mapper;

import com.linle.entity.sys.Feedback;

import component.BaseMapper;

public interface FeedbackMapper extends BaseMapper<Feedback>{

	boolean del(Feedback back);
	
}