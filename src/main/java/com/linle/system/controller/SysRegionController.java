package com.linle.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.entity.sys.SysRegion;
import com.linle.system.service.RegionService;


@Controller
@RequestMapping("/sys")
public class SysRegionController extends BaseController{
	
	@Autowired
	private RegionService regionService;
	
	/**
	 * 获取市级别
	 * @param 省级ID
	 * @return
	 */
	
	@RequestMapping(value = "/getCityList/{parentId}", method = RequestMethod.POST)
	public @ResponseBody List<SysRegion> getCityList(@PathVariable Long parentId) {
		List<SysRegion> cityList = regionService.getCitys(regionService.getById(parentId));
		return cityList;
	}
	
	/**
	 * 获取县区级别
	 * @param 市级ID
	 * @return
	 */
	@RequestMapping(value = "/getAreaList/{parentId}", method = RequestMethod.POST)
	public @ResponseBody List<SysRegion> getAreaList(@PathVariable Long parentId) {
		List<SysRegion> cityList = regionService.getCountys(regionService.getById(parentId));
		return cityList;
	}
}
