package com.linle.preferentialActivity.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.SysOrder;
import com.linle.preferentialActivity.mapper.PreferentialActivityMapper;
import com.linle.preferentialActivity.model.PreferentialActivity;
import com.linle.preferentialActivity.service.PreferentialActivityService;
import com.linle.preferentialActivityRecord.model.PreferentialActivityRecord;
import com.linle.preferentialActivityRecord.service.PreferentialActivityRecordService;
import com.linle.sysBaseType.service.SysBaseTypeService;
import com.linle.sysOrder.service.SysOrderService;

@Service
@Transactional
public class PreferentialActivityServiceImpl extends CommonServiceAdpter<PreferentialActivity>
		implements PreferentialActivityService {

	@Autowired
	private PreferentialActivityMapper mapper;

	@Autowired
	private PreferentialActivityRecordService recordService;

	@Autowired
	private SysOrderService orderService;
	@Autowired
	private SysBaseTypeService sysBaseTypeService;
	
	@Override
	public Page<PreferentialActivity> getAllForPage(Page<PreferentialActivity> page) {
		List<PreferentialActivity> list= mapper.getAllData(page);
		 String str=null;
		for (PreferentialActivity preferentialActivity : list) {
			if(StringUtil.isNotNull(preferentialActivity.getScope())){
				String[] arr =preferentialActivity.getScope().split(",");
				 str=sysBaseTypeService.getTypeNameByModule("utilities", Arrays.asList(arr));
				 preferentialActivity.setTypeNames(str);
			}
		}
		page.setResults(list);
		return page;
	}
	
	@Override
	public boolean hasPreferential(SysOrder order) {
		// 如果订单金额>= 0.01 那么也不参加活动，因为支付的金额最少为0.01
		BigDecimal minMoney = new BigDecimal("0.01");
		if (order.getTotalMoney().compareTo(minMoney) != 1) {
			return false;
		}
		// 根据小区ID去查询活动表
		// FIXME 这里只是暂时这么写，以后活动多了 这里肯定是个list
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", order.getCommunityId());
		map.put("type", order.getType());
		PreferentialActivity Activity = mapper.selectHasPreferential(map);
		if(Activity==null){
			return false;
		}
		// 同一个订单只能参加一次活动
		if (recordService.getActivityRecordByOrderNo(order.getOrderNo()) != null) {
			return false;
		}
		// 如果活动限制了用户不能重复添加,判断用户是否参加过活动
		if (Activity.getRepeatFlag() != 0) {
			boolean isJoinActivity = recordService.isJoinPreferentialActivity(order.getUserId(), Activity.getId());
			return isJoinActivity == true ? false : true; // 如果参加过就返回没活动
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linle.preferentialActivity.service.PreferentialActivityService#
	 * joinPreferentialActivity(com.linle.entity.sys.SysOrder)
	 */
	@Override
	public void joinPreferentialActivity(SysOrder order) {
		// 如果有活动
		if (hasPreferential(order)) {
			// 获得活动信息
			Map<String, Object> map = new HashMap<>();
			map.put("communityId", order.getCommunityId());
			map.put("type", order.getType());
			PreferentialActivity Activity = mapper.selectHasPreferential(map);
			// 获得活动金额
			BigDecimal ativityTotalMoney = recordService.getActivityCostMoney(Activity.getId());
			// 如果活动金额>已消耗金额参加活动
			if (Activity.getActivityAmount().compareTo(ativityTotalMoney) == 1) {
				BigDecimal preferentialAmount = new BigDecimal(generatePreferentialMoney(Activity.getId()));
				BigDecimal totalMoney = order.getTotalMoney();
				// 如果优惠金额>=订单总金额
				if (preferentialAmount.compareTo(totalMoney) != -1) {
					preferentialAmount = totalMoney.subtract(new BigDecimal("0.01"));
				}
				totalMoney = totalMoney.subtract(preferentialAmount);
				// 插入优惠活动记录表
				PreferentialActivityRecord record = new PreferentialActivityRecord();
				record.setActivityId(Activity.getId());
				record.setOrderNo(order.getOrderNo());
				record.setPreferentialAmount(preferentialAmount);
				record.setPayFlag(0);
				record.setCreateDate(new Date());
				record.setUid(order.getUserId());
				recordService.insertSelective(record);
				// 修改订单信息
				order.setPreferentialAmount(preferentialAmount); // 优惠金额
				order.setTotalMoney(totalMoney); // 订单总金额
				orderService.updateByPrimaryKeySelective(order);
				_logger.info("订单:" + order.getOrderNo() + ",参加优惠活动:" + Activity.getActivityName() + "优惠金额:"
						+ preferentialAmount);
			}
		}
	}

	@Override
	public int generatePreferentialMoney(Long activityId) {
		// FIXME 这里因为时间问题就暂时写死了优惠金额的代码，以后有时间要改过来
		// FIXME 特殊逻辑 每5个 人 出一个5快的
		List<PreferentialActivityRecord> list = recordService.getPreferentialActivityRecord(activityId);
		int money = huodong();
		if (list.size() % 5 == 0) {
			if (money < 5) {
				return 5;
			}
		}
		return money;
	}

	public static int huodong() {
		Random ne = new Random();
		int n5 = ne.nextInt(100);
		int m = 0; // 结果数字
		if (n5 < 80) { // 80%几率 1-5元  	5-8
			m = testRandom(7, 5);
		} else { // 20%几率 5-10元  		8-10
			m = testRandom(10, 8);
		}
		return m;
	}

	public static int testRandom(int max, int min) {
		Random random = new Random();
		int result = random.nextInt(max - min + 1) + min;
		return result;
	}

}
