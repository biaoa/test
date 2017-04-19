package com.linle.propertyCompany.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.PropertyCompany;

import component.BaseMapper;

public interface PropertyCompanyMapper  extends BaseMapper<PropertyCompany>{

	PropertyCompany getPopertyCompanyByuserID(Long id);

	List<PropertyCompany> getAllPropertyCompany();

	int getPropertyCompanyCountByDate(Map<String, Object> map);

	List<UserStatistics> getPropertyListByDate(Map<String, Object> map);
}