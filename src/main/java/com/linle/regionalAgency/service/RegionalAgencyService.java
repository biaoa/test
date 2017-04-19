package com.linle.regionalAgency.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.RegionalAgency;

/**
 * 
* @ClassName: RegionalAgencyService 
* @Description: 区域代理商
* @author pangd
* @date 2016年3月18日 下午1:19:45 
*
 */
public interface RegionalAgencyService extends BaseService<RegionalAgency>{
	
	public Page<RegionalAgency> findAllPropertyCompanys(Page<RegionalAgency> page);
	
	/**
	 * 
	* @Description: 新增区域代理商
	* @param @param agency
	* @param @return
	* @return boolean
	 */
	public boolean addRegionalAgency(RegionalAgency agency);
	
	/**
	 * 
	* @Description: 根据用户ID 获得对应的区域代理商信息
	* @param @param userid
	* @param @return
	* @return PropertyCompany
	 */
	public RegionalAgency getRegionalAgencyByuserID(Long userid);
	

}
