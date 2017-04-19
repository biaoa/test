package com.linle.user.service;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.ChildAccount;
import com.linle.entity.sys.Users;
import com.linle.user.model.ChildAccountInfo;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月25日
 **/
public interface ChildAccountService {
	
	
	public Boolean addChildAccount(ChildAccountInfo childAccount, Users users, Long orgId);
	
	public Boolean updateChildAccount(ChildAccount childAccount);
	
	public Boolean delChildAccount(Long childAccount);
	/**
	 * 分页查询子帐号
	 * @param page
	 * @return
	 */
	public List<ChildAccount> findAllAccount(Page<ChildAccount> page);
	
	public ChildAccount getByUserId(Long id);

}
