package com.linle.utilities.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.linle.common.util.Page;
import com.linle.entity.sys.Utilities;
import com.linle.entity.vo.WaterVO;
import com.linle.mobileapi.v1.model.UtilitiesVO;

import component.BaseMapper;

public interface UtilitiesMapper extends BaseMapper<Utilities>{

	List<String> needMessage(Map<String, Object> map);

	UtilitiesVO getUtilitiesForAPI(Map<String, Object> params);

	int updateStatus(String orderNo);

	Utilities selectByOrderNo(String orderNo);

	int updateStatusForOffline(Map<String, Object> map);

	List<WaterVO> getUtilitiesExportUsers(Map<String, Object> params);

	int getUtilitiesCount(Map<String, Object> params);

	void updateUtilitiesJson(Utilities utilities);

	List<Utilities> selectUtilitiesByHousenumber(Map<String, Object> map);

	Utilities getRecentlyDate(Map<String, Object> params);

	Utilities getStatisticQuantity(Map<String, Object> map);

	int updateUtilitiesStatusForNext(Map<String, Object> map);

	BigDecimal selectBeforeUnPayableSum(Map<String, Object> map);

	List<WaterVO> getAllDataVo(Page<WaterVO> page);

	List<Utilities> findAllOwnerUtilities(Page<Utilities> page);

	Utilities getOldDateByStatus(Map<String, Object> params);

	int paySuccessupdateStatus(String orderNo);

}