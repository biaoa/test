package com.linle.disableUser.service;

import com.linle.common.base.BaseService;
import com.linle.disableUser.model.DisableUser;

public interface DisableUserService extends BaseService<DisableUser>{

	void deleteByUserId(long userid);

}
