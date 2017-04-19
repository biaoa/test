package com.linle.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.appBanner.model.AppbannerAccessRecord;
import com.linle.appBanner.service.AppbannerAccessRecordService;
import com.linle.event.AppBannerAccessEvent;


@Component
public class AppBannerAccessListener implements ApplicationListener<AppBannerAccessEvent> {
	protected final Logger _logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AppbannerAccessRecordService appbannerAccessRecordService;

	@Async
	@Override
	public void onApplicationEvent(AppBannerAccessEvent event) {
		try {
			AppbannerAccessRecord record = new AppbannerAccessRecord();
			record.setBannerId(Long.valueOf(event.getSource().toString()));
			record.setUid(event.getUid());
			record.setCommunityId(event.getCommunityId());
			appbannerAccessRecordService.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("插入banner访问记录出错");
		}
		
	}

}
