package com.linle.feedback.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Feedback;

public interface FeedbackService extends BaseService<Feedback> {
	public Page<Feedback> getAllDate(Page<Feedback> page);

	public boolean del(Feedback back);
}
