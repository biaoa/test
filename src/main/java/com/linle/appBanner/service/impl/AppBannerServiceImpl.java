package com.linle.appBanner.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.appBanner.mapper.AppBannerMapper;
import com.linle.appBanner.model.AppBannerStatisticalVo;
import com.linle.appBanner.service.AppBannerService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.AppBanner;
import com.linle.mobileapi.v1.model.BannerVO;

@Service
@Transactional
public class AppBannerServiceImpl extends CommonServiceAdpter<AppBanner> implements AppBannerService {

	@Autowired
	private AppBannerMapper mapper;

	@Override
	public Page<AppBanner> getAllBanner(Page<AppBanner> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<BannerVO> getBannerForAPI(Map<String, Object> map) {
		return mapper.getBannerForAPI(map);
	}

	@Override
	public Page<AppBannerStatisticalVo> statistical(Page<AppBannerStatisticalVo> page) {
		page.setResults(mapper.statistical(page));
		return page;
	}

}
