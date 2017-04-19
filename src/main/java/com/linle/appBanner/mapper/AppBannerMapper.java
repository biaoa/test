package com.linle.appBanner.mapper;

import java.util.List;
import java.util.Map;

import com.linle.appBanner.model.AppBannerStatisticalVo;
import com.linle.common.util.Page;
import com.linle.entity.sys.AppBanner;
import com.linle.mobileapi.v1.model.BannerVO;

import component.BaseMapper;

public interface AppBannerMapper extends BaseMapper<AppBanner>{

	List<BannerVO> getBannerForAPI(Map<String, Object> map);

	List<AppBannerStatisticalVo> statistical(Page<AppBannerStatisticalVo> page);
}