package com.linle.universalTelephone.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.UniversalTelephone;
import com.linle.mobileapi.v1.model.UniversalTelephoneVO;

public interface UniversalTelephoneService extends BaseService<UniversalTelephone> {

	List<UniversalTelephoneVO> getAllUniversalTelephoneForAPI();

}
