package com.linle.problemType.mapper;

import java.util.HashMap;
import java.util.List;

import com.linle.problemType.model.ProblemType;

import component.BaseMapper;

public interface ProblemTypeMapper extends BaseMapper<ProblemType>{
	public List<ProblemType> getAllTypeAndData(HashMap<String, Object> map);
	
	public List<ProblemType> getAllType();
}