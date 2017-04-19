package com.linle.housekeeperAD.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Housekeeperad;
import com.linle.mobileapi.v1.model.BannerVO;

public interface HousekeeperadService extends BaseService<Housekeeperad> {

	Page<Housekeeperad> getAllHousekeeperAD(Page<Housekeeperad> page);

	List<BannerVO> getHousekeeperadForAPI(Map<String, Object> map);

}
