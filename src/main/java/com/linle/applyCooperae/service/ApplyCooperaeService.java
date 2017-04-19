package com.linle.applyCooperae.service;

import com.linle.applyCooperae.model.ApplyCooperae;
import com.linle.common.base.BaseService;
import com.linle.common.util.Page;

public interface ApplyCooperaeService extends BaseService<ApplyCooperae> {

	Page<ApplyCooperae> getAllApplyCooperaeService(Page<ApplyCooperae> page);

}
