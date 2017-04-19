package com.linle.nameCertification.service;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.NameCertification;

public interface NameCertificationService extends BaseService<NameCertification> {

	NameCertification selectByUserId(Long uid);

}
