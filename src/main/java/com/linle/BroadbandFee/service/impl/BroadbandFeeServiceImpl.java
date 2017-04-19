package com.linle.BroadbandFee.service.impl;

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

import com.linle.BroadbandFee.mapper.BroadbandFeeMapper;
import com.linle.BroadbandFee.service.BroadbandFeeService;
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
import com.linle.entity.sys.RoomNo;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.BroadbandFeeVO;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.request.BroadbandFeeRequest;
import com.linle.mobileapi.v1.response.BroadbandFeeResponse;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class BroadbandFeeServiceImpl extends CommonServiceAdpter<BroadbandFee> implements BroadbandFeeService {

	FileInputStream fis = null;

	@Autowired
	private BroadbandFeeMapper mapper;

	@Autowired
	private UserInfoService userService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SysOrderService orderService;

	@Autowired
	private OrderDetailService detailService;
	@Autowired
	private CommunityService  communityService;
	@Override
	public ResponseMsg BroadbandFeeInfo(String path, Long id, Integer type) {
		List<BroadbandFeeVO> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		int excelLi=2;
		String existRoomNO="";//没有导入成功的房号，原因是数据库已存在
		try {
			fis = new FileInputStream(path);
			ExcelToolkit<BroadbandFeeVO> util = new ExcelToolkit<BroadbandFeeVO>(BroadbandFeeVO.class);
			list = util.importExcel("", fis, 2, 0);
			for (BroadbandFeeVO v : list) {
				excelLi++;
				v.setHouseNumber(StringUtil.returnNewHouseNo(v.getHouseNumber()));
				BroadbandFee fee = new BroadbandFee();
				// FIXME 这里不知道为什么excel的数据是2016/3/15 读进来就成了 16-3-15 真特么有毒啊。
				// 先手动补个20吧。以后有时间改 2016-03-22 19:18:13
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
		 		params.put("houseNumber",StringUtil.returnNewHouseNo(v.getHouseNumber()));
				params.put("month", month);
				params.put("year", year);
				boolean flag=this.getBroadbandFeeCount(params);
				if(flag){//存在 true
					if("".equals(existRoomNO)){
						existRoomNO=v.getHouseNumber();
					}else{
						existRoomNO=existRoomNO+";"+v.getHouseNumber();
					}
					count++;
					continue;
				}
				
				// 插入数据
				fee.setHouseOwner(v.getName());
				fee.setHouseNumber(StringUtil.returnNewHouseNo(v.getHouseNumber()));
				fee.setPayable(new BigDecimal(StringUtil.isNotNull(v.getPayable())?v.getPayable():"0"));
				if((!"".equals(v.getPayable())&&fee.getPayable().compareTo(BigDecimal.ZERO)==1)||checkPayStatus(v.getPayStatus())){//大于0
					fee.setStatus(1);
					fee.setOrderNo(OrderCode.GenerationOrderCode());// yyyyMMddhhmmss+4位随机数的字符串
				}else{
					fee.setStatus(3);
				}
				fee.setCommunityId(id);
				fee.setYear(year);
				fee.setMonth(month);
				fee.setType(type);
				fee.setCreateDate(new Date());
				int result = mapper.insertSelective(fee);
				if(!"".equals(v.getPayable())&&fee.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
					createOrder(fee.getId());
				}
				if (!(result>0)) {
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
//			throw new RuntimeException("物业费上传文件出现错误");
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
	public boolean getBroadbandFeeCount(Map<String, Object> params) {
		return mapper.getBroadbandFeeCount(params)>0?true:false;
	}
	
	/**
	 * 获得日期，用于页面列表应缴月份筛选条件
	 */
	@Override
	public Map<String, Object>  getDateCondition(Map<String, Object> params){
		BroadbandFee broadbandFee=mapper.getRecentlyDate(params);
		if(broadbandFee==null){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, +1);
			params.put("year", calendar.get(Calendar.YEAR));
			params.put("month", calendar.get(Calendar.MONTH));
		}else{
			params.put("year", broadbandFee.getYear());
			params.put("month", broadbandFee.getMonth());
		}
		return params;
	}
	
	@Override
	public List<String> getNeedMessageList(Long id, String date, String type) {
		Map<String, Object> map = new HashMap<>();
		map.put("year", date.split("-")[0]);
		map.put("month", date.split("-")[1]);
		map.put("type", type);
		map.put("community_id", id);
		return mapper.needMessage(map);
	}
	@Override
	public BaseResponse updateFee(BroadbandFee broadbandFee) {
		try {
			
			 if(broadbandFee.getPayable()!=null && broadbandFee.getPayable().equals(BigDecimal.ZERO)){
				 return new BaseResponse(1,"金额错误,请重新输入");
			 }
			BroadbandFee old_record=mapper.selectByPrimaryKey(broadbandFee.getId());
			 //修改缴费
			 broadbandFee.setUpdateDate(new Date());
			 boolean feeResult=mapper.updateByPrimaryKeySelective(broadbandFee)>0;
			 //保存历史纪录
			 if(old_record.getBroadbandJson()==null||"".equals(old_record.getBroadbandJson())){
				 broadbandFee.setBroadbandJson(m.writeValueAsString(old_record));
			 }else{
				 BroadbandFee new_record=mapper.selectByPrimaryKey(broadbandFee.getId());
				 String utilitiesJson=new_record.getBroadbandJson();//将utilitiesJson赋空
				 new_record.setBroadbandJson(null);
				 broadbandFee.setBroadbandJson("["+utilitiesJson+","+m.writeValueAsString(new_record)+"]");
			 }
			 mapper.updateBroadbandJson(broadbandFee);
				 
			 int count= orderService.selectCountByOrderNo(broadbandFee.getOrderNo());
			 if(count>0){
				 //修改订单
				 boolean orderResult=orderService.updateTotalMoneyByOrderNo(broadbandFee.getOrderNo(), broadbandFee.getPayable());
				 //修改订单明细
				 boolean detailResult=detailService.updateProductPriceByOrderNo(broadbandFee.getOrderNo(), broadbandFee.getPayable());
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
	
	public List<BroadbandFeeVO> getBroadbandFeeExportUsers(long commpanyId,int typeId) {
 		Map<String, Object> params = new HashMap<>();
 		params.put("community_id",commpanyId);
 		params.put("type",typeId);
		params.put("month", DateUtil.getMonth()-1);
		params.put("year", DateUtil.getYear());
		return mapper.getBroadbandFeeExportUsers(params);
	}
	@Override
	public Page<BroadbandFee> findAllBroadbandFee(Page<BroadbandFee> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public BroadbandFee  getStatisticQuantity(Map<String, Object> map) {
		return mapper.getStatisticQuantity(map);
	}
	
	@Override
	public BroadbandFeeResponse getBroadbandFeeForAPI(BroadbandFeeRequest req) {
		Subject subject = SecurityUtils.getSubject();
		Users user = userService.findUserInfoByUserName(subject.getPrincipal().toString());
		BroadbandFeeResponse res = new BroadbandFeeResponse();
		Map<String, Object> params = new HashMap<>();
		params.put("community_id", user.getCommunity().getId());
		params.put("houseNumber", gethouseNumber(user));
//		params.put("month", DateUtil.getMonth());//上月
//		params.put("year", DateUtil.getYear());
		params.put("type", req.getType());
		res.setCode(0);
		res.setMsg("获取成功");
		res.setData(mapper.getBroadbandFeeAPI(params));
		return res;
	}

	public String gethouseNumber(Users user) {
		try {
			RoomNo no = user.getAddressDetails();
			if (no != null) {
				return no.getBuilding() + "-" + no.getUnit() + "-" + no.getRoom();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "";
	}

	@Override
	public boolean createOrder(Long paymentId) {
		BroadbandFee fee = selectByPrimaryKey(paymentId);
		List<Users> userList = userInfoService.selectUserByRoom(fee.getCommunityId(), fee.getHouseNumber());
		for (Users users : userList) {
			createFeeOrderAndDetail(fee,users);
		}

		return true;
	}

	//创建缴费订单
	public boolean createFeeOrderAndDetail(BroadbandFee fee,Users users){
		try {
			Community community=communityService.selectByPrimaryKey(users.getCommunity().getId());//根据小区id获得小区对象
			String type = fee.getType() == 1 ? "宽带费用" : "有线电视";
			String remark = community.getName() + fee.getHouseNumber()+"," + fee.getYear()+"年"+
					+ fee.getMonth()+"月" + type;
			SysOrder order = new SysOrder();
			order.setOrderNo(fee.getOrderNo());
			order.setType(fee.getType() == 1 ? "broadband" : "cableTelevision");
			order.setDetails(fee.getId().toString());
			order.setTotalMoney(fee.getPayable());
			if(fee.getStatus()==1){//未交
				order.setOrderStatus(1);//待付款
			}else if(fee.getStatus()==2){//线上已缴
				order.setOrderStatus(4);//已完成
			}else if(fee.getStatus()==3){//线下已缴
				order.setOrderStatus(10);//线下付款
			}
			order.setUserId(users.getId());
			order.setBusinessType("小区物业");
			order.setBusinessId(fee.getCommunityId());
			order.setCreateDate(new Date());
			order.setRemark(remark);
			order.setCommunityId(community.getId());
			orderService.insertSelective(order);
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order.getId());
			detail.setProductName(remark);
			detail.setProductQuantity(1);
			detail.setProductPrice(fee.getPayable());
			detail.setRemark(remark);
			detail.setCreateDate(new Date());
			detail.setOrderType(fee.getType() == 1 ? "broadband" : "cableTelevision");
			detail.setPicture(fee.getType() == 1 ? "resources/images/order/order-kuandai.png"
					: "/resources/images/order/order-tv.png");
			detail.setContent(remark);
			detailService.insertSelective(detail);
			if(fee.getStatus()==1){//未交
				//推送
				PushBean pushBean = new PushBean();
				if (fee.getType()==1) {
					pushBean.setType(PushType.BROADBAND_MSG);//"宽带"
				}else if (fee.getType()==2) {
					pushBean.setType(PushType.CABLETELEVISION_MSG);//"有线电视"
				}
				String msg = "您的" + fee.getYear() + "年" + fee.getMonth() + "月" + (fee.getType() == 1 ? "宽带费用" : "有线电视") + "账单已出，请您及时缴费";
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
		return mapper.paySuccessupdateStatus(orderNo)>0;
	}

	@Override
	public boolean offline(String orderNo) {
		BroadbandFee fee = mapper.selectByOrderNo(orderNo);
		//第一步 先去把缴费记录中的同批次订单状态标记为已线下缴费(比如1-1-101里面有两个用户。只要把其中一个标记为线下付款，另外一个也自动变为线下付款)
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", fee.getCommunityId());
		map.put("year", fee.getYear());
		map.put("month", fee.getMonth());
		map.put("houseNumber", fee.getHouseNumber());
		map.put("type", fee.getType());
		boolean feeResult = mapper.updateStatusForOffline(map)>0;
		//第二步 订单表中的记录改为线下付款
		boolean orderResult = orderService.updateOrderStatusByOrderNo(orderNo, 10);//将订单记录表中的记录改为下线付款
		return feeResult;
	}
	
	@Override
	public List<BroadbandFee> selectBroadbandFeeByHousenumber(long roomNoId,long communityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", communityId);
		map.put("roomNoId", roomNoId);
		return mapper.selectBroadbandFeeByHousenumber(map);
	}
	
	public static boolean checkPayStatus(String payStatus){
		if(StringUtil.isNull(payStatus) || "0".equals(payStatus)){
			return true;
		}
		return false;
	}
}
