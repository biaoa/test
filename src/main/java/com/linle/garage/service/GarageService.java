package com.linle.garage.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Garage;
import com.linle.entity.vo.SpaceOrder;

/**
 * 
* @ClassName: GarageService 
* @Description: 车库
* @author pangd
* @date 2016年4月5日 上午10:38:04 
*
 */
public interface GarageService extends BaseService<Garage> {
	
	Page<Garage> findAllGarage(Page<Garage> page);
	
	//根据小区ID 获得小区车库列表
	List<Garage> getGarageList(Long id);
	
	//小区车位订单
	Page<SpaceOrder> findAllGarageOrder(Page<SpaceOrder> page);
	//假删除
	boolean del(Garage garage);

}
