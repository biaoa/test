package com.linle.sysOrder.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linle.capitalManage.vo.TransactionflowVo;
import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.statistics.BaseStatistics;
import com.linle.entity.statistics.OrderStatistics;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.SalesVO;
import com.linle.funds.vo.Incomedetail;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.shop.model.OrderInfoVo;
import com.linle.mobileapi.shop.request.AcceptOrderRequest;
import com.linle.mobileapi.v1.model.OrderVo;
import com.linle.mobileapi.v1.request.CommodityRequest;
import com.linle.mobileapi.v1.request.OperateOrderRequest;
import com.linle.mobileapi.v1.request.OrderDetailVo2;
import com.linle.mobileapi.v1.request.RefundOrderRequest;
import com.linle.sysOrder.vo.CommodityOrderSendInfo;

public interface SysOrderService extends BaseService<SysOrder> {
	public BaseResponse orderSuccess(long userId,String orderNo);
	
	SysOrder getOrderByOrderNo(String orderNo,Long uid);

	List<OrderVo> getOrderList(Map<String, Object> map);
	
	
	List<OrderVo> getOrderListAndDetail(Map<String, Object> map);

	SysOrder findOrder(Map<String, Object> map);
	
	SysOrder selectByOrderTypeAndBusinessType(String type, Long sid);
	/**
	 * 
	 * @Description 修改订单价格
	 * @param order
	 * @return boolean
	 */
	boolean updateOrderPrice(SysOrder order);
	/**
	 * 
	 * @Description 关闭订单
	 * @param order
	 * @return boolean
	 * $
	 */
	boolean closeOrder(SysOrder order);
	/**
	 * 
	 * @Description 受理订单
	 * @param order
	 * @return boolean
	 * $
	 */
	boolean accepted(SysOrder order);
	/**
	 * 
	 * @Description 订单状态改为待发货
	 * @param order
	 * @return boolean
	 * $
	 */
	boolean waitieforEeceiving(SysOrder order);
	/**
	 * 
	 * @Description 订单完成
	 * @param order
	 * @return boolean
	 * $
	 */
	boolean orderSuccess(SysOrder order);
	/**
	 * 
	 * @Description 获得订单详情。包含买/卖家信息
	 * @param orderNo
	 * @param orderType
	 * @return OrderDetailVo2
	 * $
	 */
	OrderDetailVo2 getOrderDetail(String orderNo,String orderType,Long uid);
	
	/**
	 * 
	 * @Description 根据订单状态查询订单数量
	 * @param uid
	 * @param status
	 * @return int
	 * $
	 */
	int getStatusCount(Long uid, int status);
	
	/**
	 * 
	 * @Description  家园提交订单
	 * @param req
	 * @return BaseResponse
	 * $
	 */
	BaseResponse createCommodityOrder(CommodityRequest req);
	
	SysOrder selectByOrderNo(String orderNo);
	
	int  updateSendDate(SysOrder order);
	/**
	 * 
	 * @Description  关闭10分钟内没有接单的商家
	 * @return int
	 * $
	 */
	int cancelNoOperatOrder();
	/**
	 * 
	 * @Description 关闭30分钟内未支付的订单
	 * @return int
	 * $
	 */
	int cancelPayTimeOutOrder();

	boolean updateChargeId(Map<String, Object> params);
	
	/**
	 * 
	 * @Description 获得要退款的订单列表
	 * @return List<SysOrder>
	 * $
	 */
	List<SysOrder> getRefundList();
	/**
	 * 
	 * @Description app操作订单
	 * @param req
	 * @param order
	 * @return BaseResponse
	 * $
	 */
	BaseResponse operateOrder(OperateOrderRequest req, SysOrder order);
	
	/**
	 * 
	 * @Description 客户端删除订单
	 * @param order
	 * @return boolean
	 * $
	 */
	boolean delOrder(SysOrder order);
	
	/**
	 * 
	 * @Description 修改订单状态
	 * @param order
	 * @return boolean
	 * $
	 */
	boolean updateOrderStatus(SysOrder order);
	
	/**
	 * 
	 * @Description  商家看自己的订单
	 * @param page
	 * @return Page<SysOrder>
	 * $
	 */
	Page<SysOrder> selectCommodityOrderList(Page<SysOrder> page);
	
	/**
	 * 
	 * @Description 通过订单编号和商品ID 获得家园订单明细
	 * @param detailMap
	 * @return SysOrder
	 * $
	 */
	SysOrder getCommodityOrderDetail(Map<String, Object> detailMap);
	/**
	 * 
	 * @Description  获得订单的配送信息
	 * @param orderNo
	 * @return CommodityOrderSendInfo
	 * $
	 */
	CommodityOrderSendInfo getSendInfo(String orderNo);
	/**
	 * 
	 * @Description  商家接单/拒绝
	 * @param map
	 * @return boolean
	 * $
	 */
	boolean shoperOperateOrder(Map<String, Object> map);
	//家园订单退款
	BaseResponse refundOrder(SysOrder order,RefundOrderRequest req,Long folderId);
	
	//通过订单号 修改订单状态
	boolean updateOrderStatusByOrderNo(String orderNo,Integer status);
	
	//查询店铺今日有效订单
	int selectDayValidOrderCount(Long id);
	//获得店铺今日销售额
	BigDecimal selectDayTurnover(Long id);
	//获得店铺的总营业额
	BigDecimal getAllTurnover(Long id);
	//获得本月营业额
	BigDecimal getMonthTurnover(Long id);
	//获得本周营业额
	BigDecimal getWeekTurnover(Long id);
	//获得用户下单后 24小时未确认的订单
	List<SysOrder> selectUserNoOperateOrder();
	//关闭30分钟未支付的车位订单
	int cancelPaySpaceTimeOutOrder();
	
	//用户在商家接单前 取消订单
	BaseResponse noOperateCanceOrder(SysOrder order);
	
	//获得店铺最近x日的销量
	SalesVO getShopSales(Long id, int day);
	//商家获得资金明细
	Page<Incomedetail> getShopIncomedetailList(Page<Incomedetail> page);
	/**
	 * 
	 * @Description 获得小区某个缴费的总收益
	 * @param communityId 小区ID
	 * @param type 缴费类型
	 * @return BigDecimal
	 * $
	 */
	BigDecimal getPaymetIncome(Long communityId, String type);
	/**
	 * 
	 * @Description 根据小区ID 获得该小区的商家利润分成
	 * @param communityId
	 * @return BigDecimal
	 * $
	 */
	BigDecimal getCommunityShopShare(Long communityId);
	/**
	 * 
	 * @Description 根据小区ID 获得该小区的车位相关的收入
	 * @param communityId
	 * @return BigDecimal
	 * $
	 */
	BigDecimal getCommunitySpaceIncome(Long communityId);
	
	/**
	 * 
	 * @Description 获得小区的收益明细
	 * @param page
	 * @return Page<Incomedetail>
	 * $
	 */
	Page<Incomedetail> getCommunitydetailList(Page<Incomedetail> page);
	
	/**
	 * 
	 * @Description 商家移动端获得订单列表
	 * @param map
	 * @return List<OrderInfoVo>
	 */
	List<OrderInfoVo> getShopOrderListForAPI(Map<String, Object> map);
	
	/**
	 * 
	 * @Description 商家移动端接单/一键接单
	 * @param req 
	 * @param id 商家ID
	 * @return BaseResponse
	 * $
	 */
	BaseResponse acceptOrder(AcceptOrderRequest req, Long id);
	
	/**
	 * 
	 * @Description 根据订单号查询 相关的user_id 缴费的单独通知用到
	 * @param orderNo
	 * @return List<String>
	 * $
	 */
	List<String> alonesendMessageUser(String orderNo);

	boolean updateTotalMoneyByOrderNo(String orderNo, BigDecimal totalMoney);

	boolean createFeeOrderAndDetailByRoomNo(Users users);

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

	/**
	 * 
	 * @Description 获得资金流水记录
	 * @param page
	 * @return Page<TransactionflowVo>
	 * $
	 */
	Page<TransactionflowVo> getTransactionflow(Page<TransactionflowVo> page);

	OrderStatistics getOrderCountStatistics(Map<String, Object> map);

	boolean updateOrderStatusByHouseNumber(Map<String, Object> map);
	
	public void sayHello();

	OrderStatistics getOrderAmountStatistics(Map<String, Object> map);

	boolean checkOrderIsJoinActivity(long orderId);

	int getBuyActivityProductNumByUserId(HashMap<String, Object> map);
}
