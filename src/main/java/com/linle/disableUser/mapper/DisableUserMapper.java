package com.linle.disableUser.mapper;

import com.linle.disableUser.model.DisableUser;

import component.BaseMapper;

public interface DisableUserMapper extends BaseMapper<DisableUser>{

	void deleteByUserId(long userid);
    
}