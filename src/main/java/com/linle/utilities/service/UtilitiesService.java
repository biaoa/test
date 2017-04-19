package com.linle.utilities.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.Users;
import com.linle.entity.sys.Utilities;
import com.linle.entity.vo.WaterVO;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.UtilitiesRequest;
import com.linle.mobileapi.v1.response.UtilitiesResponse;

public interface UtilitiesService extends BaseService<Utilities> {
		
	public Page<Utilities> findAllUtilities(Page<Utilities> page);
	
	public ResponseMsg utilitiesInfo(String path,String type,Long communityId);
	/**
	 * 
	* @Description: 得到需要发送通知的列表
	* @param @param id
	* @param @param date
	* @param @param type
	* @param @return
	* @return List<String>
	 */
	public List<String> getNeedMessageList(Long id, String date, String type);
	/**
	 * 
	* @Description: 获得本月水电费账单
	* @param @param params
	* @param @return
	* @return UtilitiesResponse
	 */
	public UtilitiesResponse getUtilitiesForAPI(UtilitiesRequest req);
	
	public List<WaterVO> getUtilitiesExportUsers(long commpanyId,int typeId);
	
	/**
	 * 
	 * @Description 根据缴费记录生成订单 水，电，燃气
	 * @param utilitiesId
	 * @return int
	 * $
	 */
	public int createOrder(Long utilitiesId);

	public boolean createFeeOrderAndDetail(Utilities utilities,Users users);
	
	//修改 水费，电费，燃气费缴费状态
	public boolean updateStatus(String orderNo);
	
	//水费，电费，燃气费缴费线下缴费的逻辑
	public long offline(String orderNo);

	BaseResponse updateFee(Utilities utilities);

	List<Utilities> selectUtilitiesByHousenumber(long roomNoId, long communityId);

	Map<String, Object> getDateCondition(Map<String, Object> params);

	Utilities getStatisticQuantity(Map<String, Object> map);

	boolean updateUtilitiesStatusForNext(Map<String, Object> map);

	BigDecimal selectBeforeUnPayableSum(Map<String, Object> map);

	Page<WaterVO> findAllUtilitiesVo(Page<WaterVO> page);

	ResponseMsg utilitiesCreateOrder(String jsonStr, Long community_id, String type,String recordTime,String yearMonth) throws Exception;

	Page<Utilities> findAllOwnerUtilities(Page<Utilities> page);
	
	BigDecimal getSettingPrice(long community_id,String type);
	/**
	 * 
	 * @Description 支付成功修改缴费记录状态和支付时间
	 * @param orderNo
	 * @return boolean
	 */
	boolean paySuccessupdateStatus(String orderNo);
}
