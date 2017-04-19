package com.linle.commonProblem.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.commonProblem.model.CommonProblem;

public interface CommonProblemService  extends BaseService<CommonProblem> {
	
	public List<CommonProblem> getAllDataForApi();
	
	public Page<CommonProblem> getAllDataForPage(Page<CommonProblem> page);
}
