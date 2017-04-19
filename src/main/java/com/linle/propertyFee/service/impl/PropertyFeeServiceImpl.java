package com.linle.propertyFee.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.common.util.ExcelToolkit;
import com.linle.common.util.OrderCode;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.BroadbandFee;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.PropertyFee;
import com.linle.entity.sys.RoomNo;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.entity.sys.Utilities;
import com.linle.entity.vo.PropertyFeeVO;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.request.PropertyFeeRequest;
import com.linle.mobileapi.v1.response.PropertyFeeResponse;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.propertyFee.mapper.PropertyFeeMapper;
import com.linle.propertyFee.service.PropertyFeeService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.service.UserInfoService;

@Service("PropertyFeeService")
@Transactional
public class PropertyFeeServiceImpl extends CommonServiceAdpter<PropertyFee> implements PropertyFeeService {
	
	@Autowired
	private UserInfoService userService;
	
	FileInputStream fis = null;
	
	@Autowired
	private PropertyFeeMapper mapper;
	
	@Autowired
	private SysOrderService orderService;

	@Autowired
	private OrderDetailService detailService;
	@Autowired
	private CommunityService  communityService;
	
	@Override
	public ResponseMsg propertyFeeInfo(String path, Long id,String type) {
		List<PropertyFeeVO> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		int excelLi=2;
		String existRoomNO="";//没有导入成功的房号，原因是数据库已存在
		try {
			fis = new FileInputStream(path);
			ExcelToolkit<PropertyFeeVO> util = new ExcelToolkit<PropertyFeeVO>(PropertyFeeVO.class);
			list = util.importExcel("", fis, 2, 0);
			for (PropertyFeeVO v : list) {
				excelLi++;
				v.setRoomNo(StringUtil.returnNewHouseNo(v.getRoomNo()));
				PropertyFee fee = new PropertyFee();
				//FIXME 这里不知道为什么excel的数据是2016/3/15 读进来就成了 16-3-15 真特么有毒啊。 先手动补个20吧。以后有时间改 2016-03-22 19:18:13
				if(v.getFeeTime().length()<10){
					fee.setFeeTime(("20"+v.getFeeTime()));
				}else{
					fee.setFeeTime(v.getFeeTime());
				}
				Integer year=DateUtil.getYear(sdf.parse(fee.getFeeTime()));
				Integer month=DateUtil.getMonth(sdf.parse(fee.getFeeTime()));
				
				//判断该小区该月该房号是否有已上传过同房号记录，若有则跳过
				Map<String, Object> params = new HashMap<>();
		 		params.put("community_id", id);
		 		params.put("houseNumber",StringUtil.returnNewHouseNo(v.getRoomNo()));
				params.put("month", month);
				params.put("year", year);
				boolean flag=this.getPropertyFeeCount(params);
				if(flag){//存在 true
					if("".equals(existRoomNO)){
						existRoomNO=v.getRoomNo();
					}else{
						existRoomNO=existRoomNO+";"+v.getRoomNo();
					}
					count++;
					continue;
				}
				
				// 插入数据
				fee.setHouseOwner(v.getName());
				fee.setHouseNumber(StringUtil.returnNewHouseNo(v.getRoomNo()));
				fee.setHouseAcreage(StringUtil.isNotNull(v.getAcreage())?Float.valueOf(v.getAcreage()):0);
				fee.setYear(year);
				fee.setMonth(month);
				System.out.println(v.getPrice());
				fee.setPrice(new BigDecimal(StringUtil.isNotNull(v.getPrice())?v.getPrice():"0"));
				fee.setPayable(new BigDecimal(StringUtil.isNotNull(v.getPayable())?v.getPayable():"0"));
				if((!"".equals(v.getPayable())&&fee.getPayable().compareTo(BigDecimal.ZERO)==1) || checkPayStatus(v.getPayStatus())){//大于0
					fee.setStatus(1);
					fee.setOrderNo(OrderCode.GenerationOrderCode());// yyyyMMddhhmmss+4位随机数的字符串
				}else{
					fee.setStatus(3);
				}
				fee.setCommunityId(id);
				if(StringUtil.isNotNull(fee.getFeeTime())){
					fee.setYear(DateUtil.getYear(sdf.parse(fee.getFeeTime())));
					fee.setMonth(DateUtil.getMonth(sdf.parse(fee.getFeeTime())));
				}
				fee.setCreateDate(new Date());
				boolean b =insertSelective(fee) > 0;
				if(!"".equals(v.getPayable())&&fee.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
					createOrder(fee.getId());
				}
				if (!b) {
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
//			throw new RuntimeException("1");//有异常全部回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ResponseMsg(1, "第" + excelLi+"行数据格式不对，请核对再上传！", null);
		}
		if(!"".equals(existRoomNO)){
			return new ResponseMsg(0, "共:" + list.size() + "，失败:" + count+"，房号("+existRoomNO+")已存在，不能重复导入", null);
		}else{
			return new ResponseMsg(0, "共:" + list.size() + "，失败:" + count, null);
		}
	}

	//判断该小区该月该房号是否有已上传过同房号记录
	public boolean getPropertyFeeCount(Map<String, Object> params) {
		return mapper.getPropertyFeeCount(params)>0?true:false;
	}
	
	/**
	 * 获得日期，用于页面列表应缴月份筛选条件
	 */
	@Override
	public Map<String, Object>  getDateCondition(Map<String, Object> params){
		PropertyFee propertyFee=mapper.getRecentlyDate(params);
		if(propertyFee==null){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, +1);
			params.put("year", calendar.get(Calendar.YEAR));
			params.put("month", calendar.get(Calendar.MONTH));
		}else{
			params.put("year", propertyFee.getYear());
			params.put("month", propertyFee.getMonth());
		}
		return params;
	}
	
	@Override
	public Page<PropertyFee> findAllPropertyFee(Page<PropertyFee> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public PropertyFee  getStatisticQuantity(Map<String, Object> map) {
		return mapper.getStatisticQuantity(map);
	}
	
	@Override
	public List<String> getNeedMessageList(Long id, String date) {
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", id);
		map.put("year", date.split("-")[0]);
		map.put("month", date.split("-")[1]);
		return mapper.getNeedMessageList(map);
	}

	@Override
	public BaseResponse updateFee(PropertyFee propertyFee) {
		try {
			 if(propertyFee.getPayable()!=null && propertyFee.getPayable().equals(BigDecimal.ZERO)){
				 return new BaseResponse(1,"金额错误,请重新输入");
			 }
			PropertyFee old_record=mapper.selectByPrimaryKey(propertyFee.getId());
			 //修改缴费
			 propertyFee.setUpdateDate(new Date());
			 boolean feeResult=mapper.updateByPrimaryKeySelective(propertyFee)>0;
			 //保存历史纪录
			 if(old_record.getPropertyJson()==null||"".equals(old_record.getPropertyJson())){
				 propertyFee.setPropertyJson(m.writeValueAsString(old_record));
			 }else{
				 PropertyFee new_record=mapper.selectByPrimaryKey(propertyFee.getId());
				 String utilitiesJson=new_record.getPropertyJson();//将utilitiesJson赋空
				 new_record.setPropertyJson(null);
				 propertyFee.setPropertyJson("["+utilitiesJson+","+m.writeValueAsString(new_record)+"]");
			 }
			 mapper.updatePropertyJson(propertyFee);
			 int count= orderService.selectCountByOrderNo(propertyFee.getOrderNo());
			 if(count>0){
				 //修改订单
				 boolean orderResult=orderService.updateTotalMoneyByOrderNo(propertyFee.getOrderNo(), propertyFee.getPayable());
				 //修改订单明细
				 boolean detailResult=detailService.updateProductPriceByOrderNo(propertyFee.getOrderNo(), propertyFee.getPayable());
				 if(!(feeResult&&detailResult&&orderResult)){
					 throw new RuntimeException("1");//有异常全部回滚
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			return BaseResponse.OperateFail;
		}
		return BaseResponse.OperateSuccess;
		
	}
	
	@Override
	public PropertyFeeResponse getPropertyFeeForAPI(PropertyFeeRequest req) {
		Subject subject = SecurityUtils.getSubject();
		Users user = userService.findUserInfoByUserName(subject.getPrincipal().toString());
		PropertyFeeResponse res = new PropertyFeeResponse();
 		Map<String, Object> params = new HashMap<>();
 		params.put("community_id", user.getCommunity().getId());
 		params.put("houseNumber",gethouseNumber(user));
//		params.put("month", DateUtil.getMonth());
//		params.put("year", DateUtil.getYear());
		res.setCode(0);
		res.setMsg("获取成功");
		res.setData(mapper.getPropertyFeeAPI(params));
		return res;
	}
	
	@Override
	public List<PropertyFeeVO> getPropertyFeeExportUsers(long commpanyId) {
 		Map<String, Object> params = new HashMap<>();
 		params.put("community_id",commpanyId);
		params.put("month", DateUtil.getMonth()-1);
		params.put("year", DateUtil.getYear());
		return mapper.getPropertyFeeExportUsers(params);
	}
	
	public String gethouseNumber(Users user){
		try {
			RoomNo no =  user.getAddressDetails();
			if (no!=null) {
				return no.getBuilding()+"-"+no.getUnit()+"-"+no.getRoom();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "";
	}

	@Override
	public boolean createOrder(Long id) {
		PropertyFee  propertyFee = selectByPrimaryKey(id);
		List<Users> userList = userService.selectUserByRoom(propertyFee.getCommunityId(), propertyFee.getHouseNumber());
		for (Users users : userList) {
			createFeeOrderAndDetail(propertyFee,users);
		}
		return true;
	}
	
	//创建缴费订单
	public boolean createFeeOrderAndDetail(PropertyFee propertyFee,Users users){
		try {
			String type = "物业费";
			String orderType = "propertyFee";
			String picture="resources/images/order/order-wuye.png";
			Community community=communityService.selectByPrimaryKey(users.getCommunity().getId());//根据小区id获得小区对象
			String remark = community.getName() + propertyFee.getHouseNumber() +","+ propertyFee.getYear()+"年"+
					+ propertyFee.getMonth()+"月" + type;
			SysOrder order = new SysOrder();
			order.setOrderNo(propertyFee.getOrderNo());
			order.setType(orderType);
			order.setDetails(propertyFee.getId().toString());
			order.setTotalMoney(propertyFee.getPayable());
			if(propertyFee.getStatus()==1){//未交
				order.setOrderStatus(1);//待付款
			}else if(propertyFee.getStatus()==2){//线上已缴
				order.setOrderStatus(4);//已完成
			}else if(propertyFee.getStatus()==3){//线下已缴
				order.setOrderStatus(10);//线下付款
			}
			order.setUserId(users.getId());
			order.setBusinessType("小区物业");
			order.setBusinessId(propertyFee.getCommunityId());
			order.setCreateDate(new Date());
			order.setRemark(remark);
			order.setCommunityId(community.getId());
			orderService.insertSelective(order);
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order.getId());
			detail.setProductName(remark);
			detail.setProductQuantity(1);
			detail.setProductPrice(propertyFee.getPayable());
			detail.setRemark(remark);
			detail.setCreateDate(new Date());
			detail.setOrderType(orderType);
			detail.setPicture(picture);
			detail.setContent(remark);
			detailService.insertSelective(detail);
			if(propertyFee.getStatus()==1){//未交
				// 推送
				PushBean pushBean = new PushBean();
				pushBean.setType(PushType.PROPERTYFEE_MSG);// 物业费
				String msg = "您的" + propertyFee.getYear() + "年" + propertyFee.getMonth() + "月" + "物业费账单已出，请您及时缴费";
				pushBean.setRefId("");
				pushBean.setContent(msg);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", users.getId()+"", PushFrom.LINLE_USER));
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("创建订单出错了", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
		}
		return true;
	
	}
		
	@Override
	public boolean paySuccessupdateStatus(String orderNo) {
		return mapper.paySuccessupdateStatus(orderNo);
	}

	@Override
	public boolean offline(String orderNo) {
		PropertyFee fee = mapper.selectByOrderNo(orderNo);
		//第一步 先去把缴费记录中的同批次订单状态标记为已线下缴费(比如1-1-101里面有两个用户。只要把其中一个标记为线下付款，另外一个也自动变为线下付款)
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", fee.getCommunityId());
		map.put("year", fee.getYear());
		map.put("month", fee.getMonth());
		map.put("houseNumber", fee.getHouseNumber());
		boolean feeResult = mapper.updateStatusForOffline(map)>0;
		//第二步 订单表中的记录改为线下付款
		boolean orderResult = orderService.updateOrderStatusByOrderNo(orderNo, 10);//将订单记录表中的记录改为下线付款
		return feeResult;
	}

	@Override
	public List<PropertyFee> selectPropertyFeeByHousenumber(long roomNoId,long communityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", communityId);
		map.put("roomNoId", roomNoId);
		return mapper.selectPropertyFeeByHousenumber(map);
	}
	
	public static boolean checkPayStatus(String payStatus){
		if(StringUtil.isNull(payStatus) || "0".equals(payStatus)){
			return true;
		}
		return false;
	}
}
