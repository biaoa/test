package com.linle.sysBaseType.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.sysBaseType.model.SysBaseType;

public interface SysBaseTypeService extends BaseService<SysBaseType>{

	String getTypeNameByModule(String module, List list);

}
