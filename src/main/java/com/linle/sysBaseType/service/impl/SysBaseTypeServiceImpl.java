package com.linle.sysBaseType.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.BaseServiceAdpter;
import com.linle.sysBaseType.mapper.SysBaseTypeMapper;
import com.linle.sysBaseType.model.SysBaseType;
import com.linle.sysBaseType.service.SysBaseTypeService;
@Service
@Transactional
public class SysBaseTypeServiceImpl extends BaseServiceAdpter<SysBaseType> implements SysBaseTypeService{
    @Autowired
    private SysBaseTypeMapper mapper;
    
    @Override
    public String getTypeNameByModule(String module,List list){
    	Map<String, Object> map=new HashMap<String, Object>();
    	map.put("module", module);
    	map.put("list", list);
		return mapper.getTypeNameByModule(map);
    }
}
