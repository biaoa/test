package com.linle.sysOrder.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linle.capitalManage.vo.TransactionflowVo;
import com.linle.common.util.Page;
import com.linle.entity.statistics.BaseStatistics;
import com.linle.entity.statistics.OrderStatistics;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.vo.SalesVO;
import com.linle.funds.vo.Incomedetail;
import com.linle.mobileapi.shop.model.OrderInfoVo;
import com.linle.mobileapi.v1.model.OrderVo;
import com.linle.mobileapi.v1.request.OrderDetailVo2;
import com.linle.sysOrder.vo.CommodityOrderSendInfo;

import component.BaseMapper;

public interface SysOrderMapper extends BaseMapper<SysOrder>{

	SysOrder getOrderByOrderNo(Map<String, Object> map);

	List<OrderVo> getOrderList(Map<String, Object> map);

	SysOrder findOrder(Map<String, Object> map);

	SysOrder selectByOrderTypeAndBusinessType(Map<String, Object> map);

	int updateOrderPrice(SysOrder order);

	int closeOrder(SysOrder order);
	
	List<OrderVo> getOrderListAndDetail(Map<String, Object> map);

	int accepted(SysOrder order);

	int waitieforEeceiving(SysOrder order);

	int orderSuccess(SysOrder order);

	OrderDetailVo2 getOrderDetail(Map<String, Object> map);

	int getStatusCount(Map<String, Object> map);
	
	//订单评价后 修改收获时间
	int updateSendDate(SysOrder order);
	//商家10分钟没接单 自动关闭订单
	int cancelNoOperatOrder();
	//用户30分钟没有支付 自动关闭订单
	int cancelPayTimeOutOrder();
	//修改订单的chargeId
	int updateChargeId(Map<String, Object> params);
	//获得要退款的订单列表
	List<SysOrder> getRefundList();
	//客户端删除订单
	int delOrder(SysOrder order);
	//修改订单状态
	int updateOrderStatus(SysOrder order);
	
	List<SysOrder> getCommodityOrderList(Page<SysOrder> page);
	//通过订单编号，商家id 获得家园订单详情
	SysOrder getCommodityOrderDetail(Map<String, Object> detailMap);
	//获得订单的配送信息
	CommodityOrderSendInfo getSendInfo(String orderNo);
	//商家接单/取消
	int shoperOperateOrder(Map<String, Object> map);
	
	SysOrder selectByOrderNo(String orderNo);
	//通过订单号 修改订单状态
	int updateOrderStatusByOrderNo(Map<String, Object> map);
	//店铺今日有效订单数量
	int selectDayValidOrderCount(Long id);
	//店铺今日销售额
	BigDecimal selectDayTurnover(Long id);
	//获得店铺的总营业额
	BigDecimal getAllTurnover(Long id);
	//获得店铺本月营业额
	BigDecimal getMonthTurnover(Long id);
	//获得店铺本周营业额
	BigDecimal getWeekTurnover(Long id);
	//获得用户下单后 24小时未确认的订单
	List<SysOrder> selectUserNoOperateOrder();
	
	List<SysOrder> PaySpaceTimeOutOrder();
	
	//获得店铺最近x日的销量
	SalesVO getShopSales(Map<String, Object> map);
	
	List<Incomedetail> getShopIncomedetailList(Page<Incomedetail> page);

	BigDecimal getPaymetIncome(Map<String, Object> map);

	BigDecimal getCommunityShopShare(Long communityId);
	
	BigDecimal getCommunitySpaceIncome(Long communityId);
	
	List<Incomedetail> getCommunitydetailList(Page<Incomedetail> page);
	//商家移动端根据订单状态获得订单列表
	List<OrderInfoVo> getShopOrderListForAPI(Map<String, Object> map);
	//获得到商家待接单列表
	List<SysOrder> selectWaitAcceptOrder(Map<String, Object> params);
	//获得店铺今日订单数量
	int getShopTodayOrderCount(Long id);

	List<String> alonesendMessageUser(String orderNo);
	
	int updateTotalMoneyByOrderNo(Map<String, Object> map);

	SysOrder getOneOrderByOrderNo(String orderNo);

	int selectCountByOrderNo(String orderNo);


	BigDecimal selectAllShopTurnover(Map<String, Object> map);

	int selectAllShopValidOrderCount(Map<String, Object> map);

	List<BaseStatistics> getValidOrderByCommunity(Map<String, Object> map);

	List<BaseStatistics> getTurnoverByCommunity(Map<String, Object> map);

	BaseStatistics getSalesStatisticss(Map<String, Object> map);

	BaseStatistics getSalesStatisticssByYear(Map<String, Object> map);

	BaseStatistics getOrdersStatisticss(Map<String, Object> map);

	BaseStatistics getOrdersStatisticssByYear(Map<String, Object> map);

	//获得资金流水记录
	List<TransactionflowVo> getTransactionflow(Page<TransactionflowVo> page);

	OrderStatistics getOrderCountStatistics(Map<String, Object> map);

	int updateOrderStatusByHouseNumber(Map<String, Object> map);

	OrderStatistics getOrderAmountStatistics(Map<String, Object> map);

	int checkOrderIsJoinActivity(long orderId);

	int getBuyActivityProductNumByUserId(HashMap<String, Object> map);
	

}