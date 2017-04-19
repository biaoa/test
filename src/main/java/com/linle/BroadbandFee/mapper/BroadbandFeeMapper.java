package com.linle.BroadbandFee.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.BroadbandFee;
import com.linle.entity.vo.BroadbandFeeVO;
import com.linle.mobileapi.v1.model.BroadbandFeeRES;

import component.BaseMapper;

public interface BroadbandFeeMapper extends BaseMapper<BroadbandFee>{

	List<String> needMessage(Map<String, Object> map);

	BroadbandFeeRES getBroadbandFeeAPI(Map<String, Object> params);

	int paySuccessupdateStatus(String orderNo);

	BroadbandFee selectByOrderNo(String orderNo);

	int updateStatusForOffline(Map<String, Object> map);

	List<BroadbandFeeVO> getBroadbandFeeExportUsers(Map<String, Object> params);

	int getBroadbandFeeCount(Map<String, Object> params);

	void updateBroadbandJson(BroadbandFee broadbandFee);

	List<BroadbandFee> selectBroadbandFeeByHousenumber(Map<String, Object> map);

	BroadbandFee getRecentlyDate(Map<String, Object> params);

	BroadbandFee getStatisticQuantity(Map<String, Object> map);
}