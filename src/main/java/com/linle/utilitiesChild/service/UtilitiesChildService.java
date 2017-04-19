package com.linle.utilitiesChild.service;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.Utilities;
import com.linle.utilitiesChild.model.UtilitiesChild;

public interface UtilitiesChildService extends BaseService<UtilitiesChild>{
	public void insertUtilitiesChild(Utilities oldUtilities,Utilities utilities);
}
