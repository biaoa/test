package com.linle.user.mapper;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.ChildAccount;

import component.BaseMapper;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月25日
 **/
public interface ChildAccountMapper  extends BaseMapper<ChildAccount>{
	public List<ChildAccount> getChildAccount(Page<ChildAccount> page);
	public ChildAccount getByUserId(Long id);
}
