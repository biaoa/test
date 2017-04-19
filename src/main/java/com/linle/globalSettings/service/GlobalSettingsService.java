package com.linle.globalSettings.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.globalSettings.model.GlobalSettings;

public interface GlobalSettingsService extends BaseService<GlobalSettings>{

	GlobalSettings selectBySettingKey(long communityId, String settingKey);

	Page<GlobalSettings> getAllForPage(Page<GlobalSettings> page);

}
