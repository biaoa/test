package com.linle.feedback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.Feedback;
import com.linle.feedback.mapper.FeedbackMapper;
import com.linle.feedback.service.FeedbackService;

@Service
@Transactional
public class FeedbackServiceImpl extends CommonServiceAdpter<Feedback> implements FeedbackService {

	@Autowired
	private FeedbackMapper mapper;

	@Override
	public Page<Feedback> getAllDate(Page<Feedback> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public boolean del(Feedback back) {
		return mapper.del(back);
	}

}
