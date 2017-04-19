
package com.linle.BroadbandFee.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.BroadbandFee;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.BroadbandFeeVO;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.BroadbandFeeRequest;
import com.linle.mobileapi.v1.response.BroadbandFeeResponse;

public interface BroadbandFeeService  extends BaseService<BroadbandFee>{
	/**
	 * 
	 * @Description 生成宽带费，有线电视缴费信息
	 * @param path 上传文件的路径
	 * @param id 小区id 
	 * @param type 缴费类型 1 宽带 2 有限电视
	 * @return ResponseMsg
	 * $
	 */
	ResponseMsg BroadbandFeeInfo(String path, Long id,Integer type);

	List<String> getNeedMessageList(Long id, String date,String type);

	Page<BroadbandFee> findAllBroadbandFee(Page<BroadbandFee> page);

	BroadbandFeeResponse getBroadbandFeeForAPI(BroadbandFeeRequest req);
	
	public List<BroadbandFeeVO> getBroadbandFeeExportUsers(long commpanyId,int typeId);
	
	/**
	 * 
	 * @Description 生成宽带/有限电视订单
	 * @param paymentId
	 * @return boolean
	 * $
	 */
	boolean createOrder(Long paymentId);
	
	public boolean createFeeOrderAndDetail(BroadbandFee fee,Users users);
	
	//支付成功后 修改缴费记录状态
	boolean paySuccessupdateStatus(String orderNo);
	//线下缴费逻辑
	boolean offline(String orderNo);

	BaseResponse updateFee(BroadbandFee broadbandFee);

	List<BroadbandFee> selectBroadbandFeeByHousenumber(long roomNoId, long communityId);

	Map<String, Object> getDateCondition(Map<String, Object> params);

	BroadbandFee getStatisticQuantity(Map<String, Object> map);
	
}
