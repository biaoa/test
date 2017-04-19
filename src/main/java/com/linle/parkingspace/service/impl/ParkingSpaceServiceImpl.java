package com.linle.parkingspace.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.ArrayUtil;
import com.linle.common.util.DateUtil;
import com.linle.common.util.OrderCode;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.entity.sys.Garage;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.ParkingSpace;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.garage.service.GarageService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.parkingspace.mapper.ParkingSpaceMapper;
import com.linle.parkingspace.service.ParkingSpaceService;
import com.linle.spaceRecord.service.SpaceRecordService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class ParkingSpaceServiceImpl extends CommonServiceAdpter<ParkingSpace> implements ParkingSpaceService {
	
	public final static ObjectMapper objmapper = new ObjectMapper(); 
	
	@Autowired
	private ParkingSpaceMapper mapper;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private GarageService garageService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SpaceRecordService spaceRecordService;
	
	
	@Override
	public ParkingSpace selectByGarageId(Long id) {
		return mapper.selectByGarageId(id);
	}

	@Override
	public BaseResponse saveOrder(Long gid,String spaceid, String select_space, String price_list, String total,String type,String dateType,String orderType) {
		
		Subject subject = SecurityUtils.getSubject();
		Users user = (Users) subject.getSession().getAttribute("cUser");
		user = userInfoService.getById(user.getId());
		Community community = user.getCommunity();
		Garage garage = garageService.selectByPrimaryKey(gid);
		//1 预约才锁定车位
		if ("space".equals(orderType)) {
			//判断车位是否可选
			ParkingSpace space =selectByPrimaryKey(Long.valueOf(spaceid));
			String select = select_space.replace("\"","");//选中的车位
			select=select.substring(1, select.length()-1);
			if(space.getSolded()!=null){
				int[] selectIndex = new int[select.length()];
				for (int i = 0; i < select.split(",").length; i++) {
					selectIndex[i]=StringUtil.ArrayIndexOf(space.getSolded().split(","), select.split(",")[i]);
				}
				for (int i : selectIndex) {
					if(i>0){
						return new BaseResponse(1, "车位已被预定");
					}
				}
			}
			ParkingSpace s = selectByPrimaryKey(space.getId());
			s.setSolded(StringAdd(space.getSolded(), select_space));
			updateByPrimaryKeySelective(s);
		}
		//创建订单
		String orderNo = OrderCode.GenerationOrderCode();
		SysOrder order = new SysOrder();
		order.setOrderNo(orderNo);
		order.setType(orderType); //车位
		order.setDetails(dateType); 
		order.setTotalMoney(new BigDecimal(total));
		order.setOrderStatus(1);//未支付
		order.setUserId(user.getId());
		order.setBusinessType("小区物业");
		order.setBusinessName(community.getName());
		order.setBusinessId(community.getId());
		order.setCreateDate(new Date());
		order.setUpdateDate(DateUtil.addMinute(new Date(), 30)); //最晚支付时间
		order.setBuyerAddress(user.getCommunity().getName()+user.getAddressDetails().getOverall());
		order.setBuyerPhone(user.getMobilePhone());
		order.setCommunityId(user.getCommunity().getId());
		if ("spaceRenew".equals(orderType)) {
			order.setRemark("车位续费");
		}else if("space".equals(orderType)){
			order.setRemark("车位预定");
		}
		orderService.insertSelective(order);
		//创建订单明细
		//商品数组 
		 String[] product= StringToArray(select_space);
		 String[] price =  StringToArray(price_list);
		for (int i = 0; i <product.length ; i++) {
			OrderDetail detail  = new OrderDetail();
			detail.setOrderId(Long.valueOf(order.getId()));
			detail.setProductName(community.getName()+""+garage.getName()+""+product[i]);
			detail.setProductQuantity(1);
			detail.setProductPrice(new BigDecimal(price[i]));
			if ("spaceRenew".equals(orderType)) {
				detail.setRemark("车位续费：类型为"+type);
				detail.setOrderType("spaceRenew");
				detail.setPicture("resources/images/order/order-xufei.png");
			}else if("space".equals(orderType)){
				detail.setRemark("车位预定：类型为"+type);
				detail.setOrderType("space");
				detail.setPicture("resources/images/order/order-chewei.png");
			}
			detail.setContent(order.getRemark()+" "+garage.getName()+""+product[i]);
			detail.setCreateDate(new Date());
			detail.setOther(product[i]);
			detail.setProductId(garage.getId()); //这里存的是车库ID
			orderDetailService.insertSelective(detail);
		}
		BaseResponse res =   new BaseResponse();
		res.setCode(0);
		res.setMsg("订单信息保存完成");
		Map<String, Object> result = new HashMap<>();
		result.put("orderNo", order.getOrderNo());
		res.setResult(result);
		return res;
	}
	
	/**
	 * 
	* @Description: 把字符串放到数组中 (["1_3","2_3"])
	* @param @param arr
	* @param @param str
	* @param @return
	* @return String
	 */
	public String StringAdd(String arr,String str){
		str= str.replace("\"","");
		str = str.substring(1, str.length()-1);
		if (!arr.equals("")) {
			arr+=","+str;
		}else{
			arr=str;
		}
		return arr;
	}
	
	public static String[] StringToArray(String str){
		str= str.replace("\"","");
		str = str.substring(1, str.length()-1);
		return str.split(",");
	}
	
	@Override
	public boolean saveParkingStopOrder(Long gid, String spaceid, String select_space,String spaceRecordId,String price,String dateType) {
		
		//释放车位
		Subject subject = SecurityUtils.getSubject();
		Users user = (Users) subject.getSession().getAttribute("cUser");
		user = userInfoService.getById(user.getId());
		Community community = user.getCommunity();
		Garage garage = garageService.selectByPrimaryKey(gid);
		//1 修改车位
		ParkingSpace space =selectByPrimaryKey(Long.valueOf(spaceid));
		ParkingSpace s = new ParkingSpace();
		s.setId(space.getId());
		s.setSolded(StringUtil.join(ArrayUtil.ArrayRemoveIndex(space.getSolded().split(","),select_space), ","));
		updateByPrimaryKeySelective(s);
		//把车位购买记录改为转租
		spaceRecordService.parkingLease(Long.valueOf(spaceRecordId));
		//保存订单
		String orderNo = OrderCode.GenerationOrderCode();
		SysOrder order = new SysOrder();
		order.setOrderNo(orderNo);
		order.setType("spaceLease"); //车位转租
		order.setDetails(spaceRecordId); //车位购买记录ID
		order.setOrderStatus(0);//未支付
		order.setUserId(user.getId());
		order.setBusinessType("小区物业");
		order.setBusinessName(community.getName());
		order.setBusinessId(community.getId());
		order.setCreateDate(new Date());
		order.setUpdateDate(DateUtil.addMonth(new Date(), 12)); //最晚支付时间
		order.setRemark("车位转租");
		order.setBuyerAddress(user.getCommunity().getName()+user.getAddressDetails().getOverall());
		order.setBuyerPhone(user.getMobilePhone());
		orderService.insertSelective(order);
		OrderDetail detail  = new OrderDetail();
		detail.setOrderId(Long.valueOf(order.getId()));
		detail.setProductName(community.getName()+""+garage.getName()+""+select_space);
		detail.setProductQuantity(1);
		detail.setProductPrice(new BigDecimal(calculatePrice(dateType, price)));
		detail.setRemark("车位转租");
		detail.setCreateDate(new Date());
		detail.setOther(select_space);
		detail.setProductId(garage.getId()); //这里存的是车库ID
		detail.setOrderType("spaceLease");
		detail.setPicture("resources/images/order/order-zhuanzu.png");
		detail.setContent(detail.getRemark()+" "+garage.getName()+""+select_space);
		orderDetailService.insertSelective(detail);
		return true;
	}
	//计算单价
	public String calculatePrice(String type,String price){
		//计算每个月的单价
		if ("0".equals(type)) {
			return price;
		}else if("1".equals(type)){
			return sub(price, "3")+"";
		}else if("2".equals(type)){
			return sub(price, "12")+"";
		}
		return "";
	}
	
	public static double sub(String value1,String value2){
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.divide(b2,2,RoundingMode.FLOOR).doubleValue();
		}

	@Override
	public boolean del(Long id) {
		return mapper.del(id)>0;
	}

	@Override
	public boolean freedSpace(String orderNo) {
		SysOrder order = orderService.selectByOrderNo(orderNo);
		if(order==null){
			return false;
		}
		//获得订单详情
		List<OrderDetail> detailList = orderDetailService.getDetailList(order.getId());
		if(detailList.isEmpty()){
			return false;
		}
		String[] solded = new String[detailList.size()];//车位数组
		Long garageId = detailList.get(0).getProductId();//车库ID
		for (int i = 0;i<detailList.size();i++) {
			solded[i]=detailList.get(i).getOther();
		}
		ParkingSpace parkingSpace = selectByGarageId(garageId);
		//获得车位的索引
		int[] soldedIndex = new int[solded.length];
		for (int j = 0; j < solded.length; j++) {
			soldedIndex[j]= StringUtil.ArrayIndexOf(parkingSpace.getSolded().split(","), solded[j]);
		}
		String[] newSolded = parkingSpace.getSolded().split(",");
		if(soldedIndex.length>0){
			 newSolded = StringUtil.removeArrayItem(parkingSpace.getSolded().split(","),soldedIndex);
		}
		parkingSpace.setSolded(StringUtil.join(newSolded, ","));
		return updateByPrimaryKey(parkingSpace)>0;
	}

}
