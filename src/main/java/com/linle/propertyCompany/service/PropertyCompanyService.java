package com.linle.propertyCompany.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.PropertyCompany;

public interface PropertyCompanyService  extends BaseService<PropertyCompany>{
	
	public Page<PropertyCompany> findAllPropertyCompanys(Page<PropertyCompany> page);

	public boolean addPropertyCompany(PropertyCompany company);

	public PropertyCompany getPopertyCompanyByuserID(Long id);
	
	//获得所有的物业公司
	public List<PropertyCompany> getAllPropertyCompany();

	int getPropertyCompanyCountByDate(Map<String, Object> map);

	List<UserStatistics> getPropertyListByDate(Map<String, Object> map);
	
	
}
