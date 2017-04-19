package com.linle.utilitiesChild.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.BaseServiceAdpter;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.OrderCode;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.Utilities;
import com.linle.utilitiesChild.model.UtilitiesChild;
import com.linle.utilitiesChild.service.UtilitiesChildService;
@Transactional
@Service
public class UtilitiesChildServiceImpl extends CommonServiceAdpter<UtilitiesChild> implements UtilitiesChildService {

	@Override
	public void insertUtilitiesChild(Utilities oldUtilities,Utilities utilities){
		try {
			UtilitiesChild record = new UtilitiesChild();
			// 插入数据
			record.setRefId(oldUtilities.getId());
			record.setCommunityId(oldUtilities.getCommunityId());
			record.setHouseOwner(oldUtilities.getHouseOwner());
			record.setHouseNumber(oldUtilities.getHouseNumber());
			if(utilities.getNewThisMeterReading()!=null&&new BigDecimal(utilities.getNewThisMeterReading())!=BigDecimal.ZERO){
				record.setLastMeterReading(utilities.getNewThisMeterReading());//子账单本次抄表
			}else{
				record.setLastMeterReading(oldUtilities.getThisMeterReading());
			}
			record.setThisMeterReading(utilities.getThisMeterReading());
			record.setActualConsumption(utilities.getActualConsumption());
			record.setPrice(utilities.getPrice());
			record.setPayable(utilities.getPayable());
			record.setPooledPrice(utilities.getPooledPrice());
			record.setYear(oldUtilities.getYear());
			record.setMonth(oldUtilities.getMonth());
			record.setStatus(oldUtilities.getStatus());
			record.setOrderNo(null);// yyyyMMddhhmmss+4位随机数的字符串  先生成订单号
			record.setType(oldUtilities.getType());
			record.setCreateDate(new Date());
			record.setMeterReadingDate(new Date());
			record.setInvoiceStatus(0);
			record.setRemark(utilities.getRemark());
			boolean b = insertSelective(record) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("创建缴费子记录出错了", e);
		}
		
	}

}
