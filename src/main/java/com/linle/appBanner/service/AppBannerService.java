package com.linle.appBanner.service;

import java.util.List;
import java.util.Map;

import com.linle.appBanner.model.AppBannerStatisticalVo;
import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.AppBanner;
import com.linle.mobileapi.v1.model.BannerVO;

public interface AppBannerService extends BaseService<AppBanner> {

	Page<AppBanner> getAllBanner(Page<AppBanner> page);

	List<BannerVO> getBannerForAPI(Map<String, Object> map);

	Page<AppBannerStatisticalVo> statistical(Page<AppBannerStatisticalVo> page);

}
