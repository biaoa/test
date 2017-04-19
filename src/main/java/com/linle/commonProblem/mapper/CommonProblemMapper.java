package com.linle.commonProblem.mapper;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.commonProblem.model.CommonProblem;

import component.BaseMapper;

public interface CommonProblemMapper extends BaseMapper<CommonProblem>{
	public List<CommonProblem> getAllDataForApi() ;
	
	public Page<CommonProblem> getAllDataForPage(Page<CommonProblem> page);
}