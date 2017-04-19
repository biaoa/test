package com.linle.propertyFee.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.PropertyFee;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.PropertyFeeVO;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.PropertyFeeRequest;
import com.linle.mobileapi.v1.response.PropertyFeeResponse;

public interface PropertyFeeService extends BaseService<PropertyFee>{

	ResponseMsg propertyFeeInfo(String path, Long id,String type);

	Page<PropertyFee> findAllPropertyFee(Page<PropertyFee> page);

	List<String> getNeedMessageList(Long id, String date);

	PropertyFeeResponse getPropertyFeeForAPI(PropertyFeeRequest req);
	
	public List<PropertyFeeVO> getPropertyFeeExportUsers(long commpanyId);
	
	//创建物业费订单
	public boolean createOrder(Long id);
	
	public boolean createFeeOrderAndDetail(PropertyFee propertyFee,Users users);
	
	//物业费 缴费成功
	boolean paySuccessupdateStatus(String orderNo);
	//物业费线下缴费
	boolean offline(String orderNo);

	BaseResponse updateFee(PropertyFee propertyFee);

	List<PropertyFee> selectPropertyFeeByHousenumber(long roomNoId, long communityId);

	Map<String, Object> getDateCondition(Map<String, Object> params);

	PropertyFee getStatisticQuantity(Map<String, Object> map);

}
