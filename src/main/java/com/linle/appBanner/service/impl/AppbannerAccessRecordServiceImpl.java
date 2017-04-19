package com.linle.appBanner.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.appBanner.model.AppbannerAccessRecord;
import com.linle.appBanner.service.AppbannerAccessRecordService;
import com.linle.common.base.CommonServiceAdpter;

@Service
@Transactional
public class AppbannerAccessRecordServiceImpl extends CommonServiceAdpter<AppbannerAccessRecord>
		implements AppbannerAccessRecordService {

}
