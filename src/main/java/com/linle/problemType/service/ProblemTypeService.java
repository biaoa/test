package com.linle.problemType.service;

import java.util.HashMap;
import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.problemType.model.ProblemType;

public interface ProblemTypeService  extends BaseService<ProblemType> {
	public List<ProblemType> getAllTypeAndData(HashMap<String, Object> map);
	
	public List<ProblemType> getAllType();
	
	public Page<ProblemType> getAllDataForPage(Page<ProblemType> page);
}
