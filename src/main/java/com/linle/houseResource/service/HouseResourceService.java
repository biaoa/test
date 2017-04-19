package com.linle.houseResource.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.HouseResource;
import com.linle.mobileapi.v1.model.HouseResouceVO;

public interface HouseResourceService extends BaseService<HouseResource> {
	
	/**
	 * 
	* @Description: 获得房源列表 API
	* @param @param map
	* @param @return
	* @return List<HouseResouceVO>
	 */
	List<HouseResouceVO> getHouseResouceList(Map<String, Object> map);
	/**
	 * 
	* @Description: 获得房源列表 后台
	* @param @param page
	* @param @return
	* @return Page<HouseResource>
	 */
	Page<HouseResource> findAllHouseResource(Page<HouseResource> page);
	//用户删除房源信息
	boolean delHouseResource(Long uid, Long hid);

}
