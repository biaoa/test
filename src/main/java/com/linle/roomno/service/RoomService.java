package com.linle.roomno.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.RoomNo;
import com.linle.mobileapi.v1.model.BaseEntity;
import com.linle.mobileapi.v1.response.BuildResponse;

public interface RoomService extends BaseService<RoomNo>{
	public Page<RoomNo> getAllData(Page<RoomNo> page);
	
	/**
	 * 
	* @Description: 根据小区ID 获得幢，单元，房号
	* @param @param community_id
	* @param @return
	* @return List<RoomNo>
	 */
	public List<BuildResponse> getRomeForAPI(Long community_id);
	
	/**
	 * 判断有没有幢单元房号都相同
	 * @param overall
	 * @return
	 */
	public int countRomeByOverall(String overall,Long id);
	
	//得到小区所有的幢
	public List<BaseEntity> getBuildForAPI(Long community_id);
	
	public List<BaseEntity> getUnitForAPI(Long community_id,String building);
	
	public List<BaseEntity> getRoomForAPI(Long community_id,String building,String unit);
	
	public ResponseMsg insertBatchRooms(String jsonStr,Long community_id) throws Exception;
	
}
