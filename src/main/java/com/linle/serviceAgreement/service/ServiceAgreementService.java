package com.linle.serviceAgreement.service;

import java.util.HashMap;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.serviceAgreement.model.ServiceAgreement;

public interface ServiceAgreementService extends BaseService<ServiceAgreement>{
	public Page<ServiceAgreement> getAllData(Page<ServiceAgreement> page) ;
	
	public ServiceAgreement selectByTypeId(HashMap<String, Object> map);
}
