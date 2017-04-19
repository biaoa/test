package com.linle.roomno.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.RoomNo;
import com.linle.mobileapi.v1.model.BaseEntity;
import com.linle.mobileapi.v1.response.BuildResponse;

import component.BaseMapper;

public interface RoomNoMapper extends BaseMapper<RoomNo>{

	List<BuildResponse> getRomeForAPI(Long community_id);
	
	public int countRomeByOverall(Map<String, Object> map);
	
	public List<BaseEntity> getBuildForAPI(Long community_id);
	
	public List<BaseEntity> getUnitForAPI(Map<String, Object> map);
	
	public List<BaseEntity> getRoomForAPI(Map<String, Object> map);
	
	
}