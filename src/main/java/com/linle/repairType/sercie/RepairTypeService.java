package com.linle.repairType.sercie;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.RepairType;

/**
 * 
* @ClassName: RepairTypeService 
* @Description: 报修类型
* @author pangd
* @date 2016年3月28日 上午9:44:45 
*
 */
public interface RepairTypeService extends BaseService<RepairType>{

	Page<RepairType> findAllRepairType(Page<RepairType> page);
	
	List<RepairType> getAllType();

}
