package com.linle.parkingspace.service;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.ParkingSpace;
import com.linle.mobileapi.base.BaseResponse;

public interface ParkingSpaceService extends BaseService<ParkingSpace> {
	
	/**
	 * 
	* @Description: 通过车库ID 查询车位信息 
	* @param @param id
	* @param @return
	* @return ParkingSpace
	 */
	ParkingSpace selectByGarageId(Long id);

	BaseResponse saveOrder(Long gid,String spaceid, String select_space, String price_list, String total,String type,String dateType,String orderType);
	/**
	 * 
	* @Description: 车位申停订单
	* @param  gid 车库ID
	* @param  spaceid  车库详情ID
	* @param  select_space 选中的车位
	* @param  spaceRecordId 车位购买记录ID
	* @param  price 车位价格
	* @param  dateType 日期类型 0 月 1季 2年
	* @return boolean
	 */
	boolean saveParkingStopOrder(Long gid, String spaceid, String select_space,String spaceRecordId,String price,String dateType);
	/**
	 * 
	 * @Description 车库删除 同时删除车位信息 (伪删)
	 * @param id
	 * @return boolean
	 * $
	 */
	boolean del(Long id);
	
	/**
	 * 
	 * @Description 根据订单号 释放车位
	 * @param orderNo
	 * @return boolean
	 */
	boolean freedSpace(String orderNo);


}
