package com.linle.preferentialActivity.mapper;

import java.util.Map;

import com.linle.preferentialActivity.model.PreferentialActivity;

import component.BaseMapper;

public interface PreferentialActivityMapper extends BaseMapper<PreferentialActivity>{

	PreferentialActivity selectHasPreferential(Map<String, Object> map);
   
}