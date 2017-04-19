package com.linle.propertyFee.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.PropertyFee;
import com.linle.entity.vo.PropertyFeeVO;
import com.linle.mobileapi.v1.model.PropertyFeeRES;

import component.BaseMapper;

public interface PropertyFeeMapper extends BaseMapper<PropertyFee>{

	List<String> getNeedMessageList(Map<String, Object> map);

	PropertyFeeRES getPropertyFeeAPI(Map<String, Object> params);

	boolean paySuccessupdateStatus(String orderNo);

	PropertyFee selectByOrderNo(String orderNo);

	int updateStatusForOffline(Map<String, Object> map);

	List<PropertyFeeVO> getPropertyFeeExportUsers(Map<String, Object> params);

	int getPropertyFeeCount(Map<String, Object> params);

	void updatePropertyJson(PropertyFee propertyFee);

	List<PropertyFee> selectPropertyFeeByHousenumber(Map<String, Object> map);
	
	PropertyFee getRecentlyDate(Map<String, Object> params);

	PropertyFee getStatisticQuantity(Map<String, Object> map);
}