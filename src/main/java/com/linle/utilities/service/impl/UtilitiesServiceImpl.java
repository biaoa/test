package com.linle.utilities.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.BigDecimalUtil;
import com.linle.common.util.DateUtil;
import com.linle.common.util.ExcelToolkit;
import com.linle.common.util.OrderCode;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.RoomNo;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.entity.sys.Utilities;
import com.linle.entity.vo.WaterVO;
import com.linle.event.PushMessageEvent;
import com.linle.globalSettings.service.GlobalSettingsService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.request.UtilitiesRequest;
import com.linle.mobileapi.v1.response.UtilitiesResponse;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.priceSetting.model.PriceSetting;
import com.linle.priceSetting.service.PriceSettingService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.service.UserInfoService;
import com.linle.utilities.mapper.UtilitiesMapper;
import com.linle.utilities.service.UtilitiesService;

@Transactional
@Service("UtilitiesService")
public class UtilitiesServiceImpl extends CommonServiceAdpter<Utilities> implements UtilitiesService {
	public ObjectMapper m = new ObjectMapper();
	
	@Autowired
	private UtilitiesMapper mapper;
	
	@Autowired
	private UserInfoService userService;
	
	@Autowired
	private SysOrderService orderService;

	@Autowired
	private OrderDetailService detailService;
	@Autowired
	private CommunityService  communityService;

	@Autowired
	private GlobalSettingsService globalSettingsService;
	
	@Autowired
	private PriceSettingService priceSettingService;
	
	FileInputStream fis = null;

	@Override
	public Page<Utilities> findAllUtilities(Page<Utilities> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	@Override
	public Page<Utilities> findAllOwnerUtilities(Page<Utilities> page) {
		page.setResults(mapper.findAllOwnerUtilities(page));
		return page;
	}
	
	
	@Override
	public Page<WaterVO> findAllUtilitiesVo(Page<WaterVO> page) {
		page.setPageSize(100);
		page.setResults(mapper.getAllDataVo(page));
		return page;
	}
	
	@Override
	public Utilities  getStatisticQuantity(Map<String, Object> map) {
		return mapper.getStatisticQuantity(map);
	}
	
	//Excel上传数据  生成上月缴费账单
	public ResponseMsg utilitiesInfo(String path, String type, Long communityId){
		List<WaterVO> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BigDecimal settingPrice=this.getSettingPrice(communityId, type);
		int count = 0;
		int excelLi=2;
		String existRoomNO="";//没有导入成功的房号，原因是数据库已存在
		String errorRoomNO="";//没有导入成功的房号，原因是本次抄表小于上次抄表
		String typeStr="";
		if(Integer.parseInt(type)==1){//1 水费 2电费 3燃气费'
			typeStr="water";
		}else if(Integer.parseInt(type)==2){
			typeStr="electricity";
		}else if(Integer.parseInt(type)==3){
			typeStr="gas";
		}
		try {
			fis = new FileInputStream(path);
			ExcelToolkit<WaterVO> util = new ExcelToolkit<WaterVO>(WaterVO.class);
			list = util.importExcel("", fis, 2, 0);
			for (WaterVO v : list) {
				excelLi++;
				Utilities utilities = new Utilities();
				//这里是防止用户输入7-101
				v.setRoomNo(StringUtil.returnNewHouseNo(v.getRoomNo()));
				//FIXME 这里不知道为什么excel的数据是2016/3/15 读进来就成了 16-3-15 真特么有毒啊。 先手动补个20吧。以后有时间改 2016-03-22 19:18:13
				Date recordTime=new Date();
				if(v.getRecordTime().length()<10){
					recordTime=sdf.parse("20"+v.getRecordTime());
				}else{
					recordTime=sdf.parse(v.getRecordTime());
				}
				Integer year=DateUtil.getYear(recordTime);
				Integer month=DateUtil.getMonth(recordTime);
				
				//判断该小区该月该房号是否有已上传过同房号记录，若有则跳过
				Map<String, Object> params = new HashMap<>();
		 		params.put("community_id", communityId);
		 		params.put("houseNumber",StringUtil.returnNewHouseNo(v.getRoomNo()));
		 		params.put("type", type);
				params.put("month", month);
				params.put("year", year);
				boolean flag=this.getUtilitiesCount(params);
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
			
				utilities.setLastMeterReading(v.getLastMeterReading().equals("")?0:Float.valueOf(v.getLastMeterReading()));
				utilities.setThisMeterReading(v.getThisMeterReading().equals("")?0:Float.valueOf(v.getThisMeterReading()));
				if(BigDecimalUtil.compareTo(new BigDecimal(utilities.getThisMeterReading()), new BigDecimal(utilities.getLastMeterReading()))==-1){
					if("".equals(errorRoomNO)){
						errorRoomNO=v.getRoomNo();
					}else{
						errorRoomNO=errorRoomNO+";"+v.getRoomNo();
					}
					count++;
					continue;
				}
				utilities.setCommunityId(communityId);
				utilities.setHouseOwner(v.getName());
				utilities.setHouseNumber(StringUtil.returnNewHouseNo(v.getRoomNo()));//防止输入1-101
				utilities.setActualConsumption(v.getActualConsumption().equals("")?0:Float.valueOf(v.getActualConsumption()));
				utilities.setPrice(v.getPrice().equals("")?BigDecimal.ZERO:new BigDecimal(v.getPrice()));
				utilities.setPooledPrice(v.getPooledPrice().equals("")?BigDecimal.ZERO:new BigDecimal(v.getPooledPrice()));
				BigDecimal amount=v.getPayable().equals("")?BigDecimal.ZERO:new BigDecimal(v.getPayable());
				BigDecimal lastBalance=v.getBalance().equals("")?BigDecimal.ZERO:new BigDecimal(v.getBalance());
				utilities.setAmount(amount);//金额 应缴 20
				utilities.setLastBalance(lastBalance);//上期结余 30
				BigDecimal thisPayFee=amount.subtract(lastBalance); //本期应付=金额-上期结余
				BigDecimal balance=BigDecimal.ZERO;//本期结余
				if(BigDecimalUtil.compareTo(lastBalance,amount)!=-1){//上期结余大于等于本期应缴金额
					balance=thisPayFee.multiply(new BigDecimal(-1));
					thisPayFee=BigDecimal.ZERO;
				}else{//上期结余小于本期应缴金额,
					balance=lastBalance;
					thisPayFee=amount;
				}
				utilities.setBalance(balance);
				utilities.setPayable(thisPayFee);
				utilities.setYear(year);
				utilities.setMonth(month);
				if((!"".equals(v.getPayable())&&utilities.getPayable().compareTo(BigDecimal.ZERO)==1)||checkPayStatus(v.getPayStatus())){//大于0
					utilities.setStatus(1);
					utilities.setOrderNo(OrderCode.GenerationOrderCode());// yyyyMMddhhmmss+4位随机数的字符串  先生成订单号
				}else{
					utilities.setStatus(3);//金额为0，直接更改为线下缴费，不用生成订单
				}
				utilities.setType(Integer.parseInt(type));
				utilities.setCreateDate(new Date());
				utilities.setMeterReadingDate(new Date());
					
				//未缴费的记录带到本期账单生成之后，第1步 订单表中的记录改为已关闭   这个必须在第二步之前操作
				Map<String, Object> map = new HashMap<>();
				map.put("type",Integer.parseInt(type));
				map.put("houseNumber", v.getRoomNo());
				map.put("community_id", communityId);
				map.put("year", year);//排除当前生成账单的月份
				map.put("month", month);
				//获得上期最新缴费记录--按房间号
				Utilities utilitiesOld=mapper.getRecentlyDate(params);
				if(BigDecimalUtil.compareTo(utilitiesOld.getPrice(), settingPrice)==0&&utilitiesOld.getStatus()==1){//未交
					orderService.updateOrderStatusByOrderNo(utilitiesOld.getOrderNo(),5);//将订单记录表中的记录改为已关闭
					//第2步把缴费记录中的状态改为“已带到下期缴费”
					map.put("id", utilitiesOld.getId());
					this.updateUtilitiesStatusForNext(map);
				}
					
				boolean b = insertSelective(utilities) > 0;
				
				if(!"".equals(v.getPayable())&&utilities.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
					//创建订单
					createOrder(utilities.getId());
				}
				if (!b) {
					count++;
				}
			}
		
		} 
		catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
//			throw new RuntimeException("1");//有异常全部回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			return new ResponseMsg(1, "第" + excelLi+"行数据格式不对，请核对再上传！", null);
		}
		 String errorRoomStr="";
		 if(!"".equals(errorRoomNO)){
			errorRoomStr="房号("+errorRoomNO+")填入数据有问题，本次抄表不能小于上次抄表";
		 }
		if(!"".equals(existRoomNO)){
			return new ResponseMsg(0, "共:" + list.size() + "，失败:" + count+"，房号("+existRoomNO+")已存在，不能重复导入;"+errorRoomStr, null);
		}else{
			return new ResponseMsg(0, "共:" + list.size() + "，失败:" + count+errorRoomStr, null);
		}
	}

	public BigDecimal getSettingPrice(long community_id,String type){
		String typeStr="";
		int typeId=Integer.parseInt(type);
		if(typeId==1){//1 水费 2电费 3燃气费'
			typeStr="water";
		}else if(typeId==2){
			typeStr="electricity";
		}else if(typeId==3){
			typeStr="gas";
		}
		PriceSetting priceSetting=priceSettingService.selectByType(community_id, typeStr, null);
		return priceSetting==null||priceSetting.getPrice()==null?BigDecimal.ZERO:priceSetting.getPrice();
	}
	
	//手动生成缴费账单
	@Override
	public ResponseMsg utilitiesCreateOrder(String jsonStr,Long community_id, String type,String recordTime,String yearMonth) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BigDecimal settingPrice=this.getSettingPrice(community_id, type);
		
		int count = 0;
		String existRoomNO="";//没有导入成功的房号，原因是数据库已存在
		Gson gson = new Gson();
		List<WaterVO> list = gson.fromJson(jsonStr, new TypeToken<List<WaterVO>>() {}.getType()); 
		for (WaterVO v : list) {
			Utilities utilities = new Utilities();
		 
			Date recordTime2=sdf.parse(recordTime);//抄表时间
			String year=yearMonth.split("-")[0];//应缴年份
			String month=yearMonth.split("-")[1];//应缴月份
			
			//判断该小区该月该房号是否有已上传过同房号记录，若有则跳过
			Map<String, Object> params = new HashMap<>();
	 		params.put("community_id", community_id);
	 		params.put("houseNumber",v.getRoomNo());
	 		params.put("type", type);
			params.put("month", month);
			params.put("year", year);
			boolean flag=this.getUtilitiesCount(params);
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
			utilities.setCommunityId(community_id);
			utilities.setHouseOwner(v.getName());
			utilities.setHouseNumber(v.getRoomNo());
			utilities.setLastMeterReading(v.getLastMeterReading().equals("")?0:Float.valueOf(v.getLastMeterReading()));
			utilities.setThisMeterReading(v.getThisMeterReading().equals("")?0:Float.valueOf(v.getThisMeterReading()));
			
			BigDecimal  lastMeterReading=BigDecimalUtil.stringToBigDecimal(v.getLastMeterReading());
			BigDecimal  thisMeterReading=BigDecimalUtil.stringToBigDecimal(v.getThisMeterReading());
			BigDecimal  actualConsumption=thisMeterReading.subtract(lastMeterReading);//实用
			BigDecimal  pooledPrice=BigDecimalUtil.stringToBigDecimal(v.getPooledPrice());//公摊
			utilities.setActualConsumption(actualConsumption.floatValue());
			BigDecimal payable=BigDecimal.ZERO;
			if(BigDecimalUtil.compareTo(settingPrice, BigDecimal.ZERO)==0){
				return new ResponseMsg(1,"单价不能为空，需要去缴费单价模块设置单价", null);
			}
			//应缴=实用*单价+公摊
			payable=actualConsumption.multiply(settingPrice).add(pooledPrice);
			
			utilities.setPrice(settingPrice);
			BigDecimal lastBalance=BigDecimalUtil.stringToBigDecimal(v.getBalance());
			utilities.setAmount(payable);//金额 应缴 20
			utilities.setLastBalance(lastBalance);//上期结余 30
			BigDecimal thisPayFee=payable.subtract(lastBalance); 
			BigDecimal balance=BigDecimal.ZERO;//本期结余
			if(BigDecimalUtil.compareTo(lastBalance,payable)!=-1){//上期结余大于等于本期应缴金额
				balance=thisPayFee.multiply(new BigDecimal(-1));
				thisPayFee=BigDecimal.ZERO;
			}else{//上期结余小于本期应缴金额,
				balance=lastBalance;
				thisPayFee=payable;
			}
			utilities.setBalance(balance);
			utilities.setPayable(thisPayFee);//本期应付=金额-上期结余
			utilities.setPooledPrice(pooledPrice);
			utilities.setYear(Integer.parseInt(year));
			utilities.setMonth(Integer.parseInt(month));
			if(!"".equals(v.getPayable())&&payable.compareTo(BigDecimal.ZERO)==1){//大于0
				utilities.setStatus(1);
				utilities.setOrderNo(OrderCode.GenerationOrderCode());// yyyyMMddhhmmss+4位随机数的字符串  先生成订单号
			}else{
				utilities.setStatus(3);
			}
			utilities.setType(Integer.parseInt(type));
			utilities.setCreateDate(new Date());
			utilities.setMeterReadingDate(recordTime2);
			
			//未缴费的记录带到本期账单生成之后，第1步 订单表中的记录改为已关闭   这个必须在第二步之前操作
			Map<String, Object> map = new HashMap<>();
			map.put("type",Integer.parseInt(type));
			map.put("houseNumber", v.getRoomNo());
			map.put("community_id", community_id);
			map.put("year", year);//排除当前生成账单的月份
			map.put("month", month);
			//获得上期最新缴费记录--按房间号
			Utilities utilitiesOld=mapper.getRecentlyDate(params);
			//当系统设置的单价和上期最新缴费单价相同时
			if(BigDecimalUtil.compareTo(utilitiesOld.getPrice(), settingPrice)==0&&utilitiesOld.getStatus()==1){//未交
				orderService.updateOrderStatusByOrderNo(utilitiesOld.getOrderNo(),5);//将订单记录表中的记录改为已关闭
				//第2步把缴费记录中的状态改为“已带到下期缴费”
				map.put("id", utilitiesOld.getId());
				this.updateUtilitiesStatusForNext(map);
			}
			
			
			boolean b = insertSelective(utilities) > 0;
			
			if(!"".equals(v.getPayable())&&utilities.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
				//创建订单
				createOrder(utilities.getId());
			}
			if (!b) {
				count++;
			}
		}
		if(!"".equals(existRoomNO)){
			return new ResponseMsg(0, "共:" + list.size() + "，失败:" + count+"，房号("+existRoomNO+")本月已存在，不能重复导入", null);
		}else{
			return new ResponseMsg(0, "共:" + list.size() + "，失败:" + count, null);
		}
	}	
		
	//判断该小区该月该房号是否有已上传过同房号记录
	public boolean getUtilitiesCount(Map<String, Object> params) {
		return mapper.getUtilitiesCount(params)>0?true:false;
	}
	
	/**
	 * 获得最老的日期，用于页面列表应缴月份筛选条件, 2016-6 2016-10两月份未交 获得的是2016-6月份的
	 */
	@Override
	public Map<String, Object>  getDateCondition(Map<String, Object> params){
		Utilities utilities=null;
		utilities=mapper.getRecentlyDate(params);
		if(utilities==null){
			params.put("month", DateUtil.getMonth());
			params.put("year", DateUtil.getYear());
		}else{
			params.put("year", utilities.getYear());
			params.put("month", utilities.getMonth());
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
	public UtilitiesResponse getUtilitiesForAPI(UtilitiesRequest req) {
		Subject subject = SecurityUtils.getSubject();
		Users user = userService.findUserInfoByUserName(subject.getPrincipal().toString());
		UtilitiesResponse res = new UtilitiesResponse();
 		Map<String, Object> params = new HashMap<>();
 		params.put("community_id", user.getCommunity().getId());
 		params.put("houseNumber",gethouseNumber(user));
 		params.put("type", req.getType());
//		params.put("month", DateUtil.getMonth());
//		params.put("year", DateUtil.getYear());
		res.setCode(0);
		res.setMsg("获取成功");
		res.setData(mapper.getUtilitiesForAPI(params));
		return res;
	}
	
	
	@Override
	public List<WaterVO> getUtilitiesExportUsers(long commpanyId,int typeId) {
 		Map<String, Object> params = new HashMap<>();
 		params.put("community_id",commpanyId);
 		params.put("type",typeId);
 		//获取上次缴费的时间  年-月
 		Map<String, Object> map=this.getDateCondition(params);
		
// 		params.put("month", DateUtil.getMonth()-1);
//		params.put("year", DateUtil.getYear());

 		params.put("month", map.get("month"));
		params.put("year", map.get("year"));
		params.put("settingPrice", this.getSettingPrice(commpanyId, typeId+""));
		return mapper.getUtilitiesExportUsers(params);
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
	public int createOrder(Long utilitiesId) {
		Utilities utilities = selectByPrimaryKey(utilitiesId);
		List<Users> userList = userService.selectUserByRoom(utilities.getCommunityId(), utilities.getHouseNumber());
		for (Users users : userList) {
			createFeeOrderAndDetail(utilities,users);
		}
		return 1;
	}

	//创建缴费订单
	public boolean createFeeOrderAndDetail(Utilities utilities,Users users){
		try {
			String type = "";
			String orderType = "";
			String picture="";
			Community community=communityService.selectByPrimaryKey(users.getCommunity().getId());//根据小区id获得小区对象
			switch (utilities.getType()) {
			case 1:
				type="水费";
				orderType="water";
				picture="resources/images/order/order-water.png";
				break;
			case 2:
				type="电费";
				orderType="electricity";
				picture="resources/images/order/order-dianfei.png";
				break;
			case 3:
				type="燃气费";
				orderType="gas";
				picture="resources/images/order/order-ranqi.png";
				break;
			}
			String remark = community.getName() + utilities.getHouseNumber()+"," + utilities.getYear()+"年"+
					+ utilities.getMonth()+"月" + type;
			SysOrder order = new SysOrder();
			order.setOrderNo(utilities.getOrderNo());
			order.setType(orderType);
			order.setDetails(utilities.getId().toString());
			order.setTotalMoney(utilities.getPayable());
			if(utilities.getStatus()==1){//未交
				order.setOrderStatus(1);//待付款
			}else if(utilities.getStatus()==2){//线上已缴
				order.setOrderStatus(4);//已完成
			}else if(utilities.getStatus()==3){//线下已缴
				order.setOrderStatus(10);//线下付款
			}
			order.setUserId(users.getId());
			order.setBusinessType("小区物业");
			order.setBusinessId(utilities.getCommunityId());
			order.setCreateDate(new Date());
			order.setRemark(remark);
			order.setCommunityId(community.getId());
			orderService.insertSelective(order);
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order.getId());
			detail.setProductName(remark);
			detail.setProductQuantity(1);
			detail.setProductPrice(utilities.getPayable());
			detail.setRemark(remark);
			detail.setCreateDate(new Date());
			detail.setOrderType(orderType);
			detail.setPicture(picture);
			detail.setContent(remark);
			detailService.insertSelective(detail);
			if(utilities.getStatus()==1){//未交
				// 推送
				PushBean pushBean = new PushBean();
				if ("water".equals(orderType)) {
					pushBean.setType(PushType.WATER_MSG);// 水费
				} else if ("electricity".equals(orderType)) {
					pushBean.setType(PushType.ELECTRICITY_MSG);// 电费
				} else if ("gas".equals(orderType)) {
					pushBean.setType(PushType.GAS_MSG);// 燃气费
				}
				String msg = "您的" + utilities.getYear() + "年" + utilities.getMonth() + "月" + type + "账单已出，请您及时缴费";
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
	public boolean updateStatus(String orderNo) {
		return mapper.updateStatus(orderNo)>0;
	}

	@Override
	public BaseResponse updateFee(Utilities utilities) {
		try {
			
			 if(utilities.getPayable()!=null && utilities.getPayable().equals(BigDecimal.ZERO)){
				 return new BaseResponse(1,"金额错误,请重新输入");
			 }
			
			 Utilities old_record=mapper.selectByPrimaryKey(utilities.getId());
			 //修改缴费
			 utilities.setUpdateDate(new Date());
			 boolean feeResult=mapper.updateByPrimaryKeySelective(utilities)>0;
			 //保存历史纪录
			 if(old_record.getUtilitiesJson()==null||"".equals(old_record.getUtilitiesJson())){
				 utilities.setUtilitiesJson(m.writeValueAsString(old_record));
			 }else{
				 Utilities new_record=mapper.selectByPrimaryKey(utilities.getId());
				 String utilitiesJson=new_record.getUtilitiesJson();//将utilitiesJson赋空
				 new_record.setUtilitiesJson(null);
				 utilities.setUtilitiesJson("["+utilitiesJson+","+m.writeValueAsString(new_record)+"]");
			 }
			 mapper.updateUtilitiesJson(utilities);
			 //修改订单
			 int count= orderService.selectCountByOrderNo(utilities.getOrderNo());
			 if(count>0){
				 boolean orderResult=orderService.updateTotalMoneyByOrderNo(utilities.getOrderNo(), utilities.getPayable());
				 //修改订单明细
				 boolean detailResult=detailService.updateProductPriceByOrderNo(utilities.getOrderNo(), utilities.getPayable());
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
	public long offline(String orderNo) {
		Utilities utilities = mapper.selectByOrderNo(orderNo);
		//第一步 先去把缴费记录中的同批次订单状态标记为已线下缴费(比如1-1-101里面有两个用户。只要把其中一个标记为线下付款，另外一个也自动变为线下付款)
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", utilities.getCommunityId());
		map.put("year", utilities.getYear());
		map.put("month", utilities.getMonth());
		map.put("houseNumber", utilities.getHouseNumber());
		map.put("type", utilities.getType());
		boolean utilitiesResult = mapper.updateStatusForOffline(map)>0;
		//第二步 订单表中的记录改为线下付款
		boolean orderResult = orderService.updateOrderStatusByOrderNo(orderNo, 10);//将订单记录表中的记录改为下线付款
		return utilities.getId();
	}
	
	//状态改为“带到下期缴费”
	@Override
	public boolean updateUtilitiesStatusForNext(Map<String, Object> map){
		return mapper.updateUtilitiesStatusForNext(map)>0;
	}
	
	//查询该房号之前未缴费的单子总金额
	@Override
	public BigDecimal selectBeforeUnPayableSum(Map<String, Object> map){
		return mapper.selectBeforeUnPayableSum(map);
	}
	
	@Override
	public List<Utilities> selectUtilitiesByHousenumber(long roomNoId,long communityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", communityId);
		map.put("roomNoId", roomNoId);
		return mapper.selectUtilitiesByHousenumber(map);
	}

	@Override
	public boolean paySuccessupdateStatus(String orderNo) {
		return mapper.paySuccessupdateStatus(orderNo)>0;
	}
	
	public static boolean checkPayStatus(String payStatus){
		if(StringUtil.isNull(payStatus) || "0".equals(payStatus)){
			return true;
		}
		return false;
	}
}
