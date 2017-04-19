package com.linle.repairType.mapper;

import java.util.List;

import com.linle.entity.sys.RepairType;

import component.BaseMapper;
/**
 * 
* @ClassName: RepairTypeMapper 
* @Description: 报修类型
* @author pangd
* @date 2016年3月28日 上午9:43:17 
*
 */
public interface RepairTypeMapper extends BaseMapper<RepairType>{

	List<RepairType> getAllType();
    
}