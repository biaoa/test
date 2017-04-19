package com.linle.serviceAgreement.mapper;

import java.util.HashMap;

import com.linle.serviceAgreement.model.ServiceAgreement;

import component.BaseMapper;

public interface ServiceAgreementMapper extends BaseMapper<ServiceAgreement> {

	ServiceAgreement selectByTypeId(HashMap<String, Object> map);
}