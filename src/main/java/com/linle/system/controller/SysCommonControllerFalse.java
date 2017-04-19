package com.linle.system.controller;

import java.math.BigDecimal;
import java.text.ParseException;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.commodity.service.CommodityService;
import com.linle.common.base.BaseController;
import com.linle.common.util.RandomUtil;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.statistics.BaseStatistics;
import com.linle.entity.statistics.OrderStatistics;
import com.linle.entity.statistics.Select2Statistics;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.HotCommodityVO;
import com.linle.entity.vo.SalesVO;
import com.linle.propertyCompany.service.PropertyCompanyService;
import com.linle.shop.service.ShopService;
import com.linle.sysOrder.service.SysOrderService;


@Controller
@RequestMapping("/sys2")
public class SysCommonControllerFalse extends BaseController{
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private PropertyCompanyService propertyCompanyService;
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
	private String todayMMDD=sdf.format(new Date());
	private String todayYYMM=sdf2.format(new Date());
	@RequestMapping(value = "/home")
	public  String common(Model model) {
		Subject subject = SecurityUtils.getSubject();
		Users user = (Users) subject.getSession().getAttribute("cUser");
		if(user!=null){
			if(user.getIdentity()==UserType.SJ){ //商家
				//商家信息
				Shop shop = shopService.selectByUserID(user.getId());
				model.addAttribute("shopInfo",shop);
				//有效订单
				int validOrderCount = orderService.selectDayValidOrderCount(shop.getId());
				//营业额
				BigDecimal turnover = orderService.selectDayTurnover(shop.getId());
				//商品数量
				int commodityCount = commodityService.selectCommodityCount(shop.getId());
				//热销商品
				List<HotCommodityVO> hotCommodityList = commodityService.selectHotCommodityList(shop.getId());
				//总营业额
				BigDecimal allTurnover = orderService.getAllTurnover(shop.getId());
				//月营业额
				BigDecimal monthTurnover = orderService.getMonthTurnover(shop.getId());
				//周营业额
				BigDecimal weekTurnover  = orderService.getWeekTurnover(shop.getId());
				//最近7天图表数据
				SalesVO sales = orderService.getShopSales(shop.getId(),7);
				sales.setDate(disposeDate(sales.getDate()));
		
				model.addAttribute("validOrderCount", validOrderCount);
				model.addAttribute("turnover", turnover);
				model.addAttribute("commodityCount", commodityCount);
				model.addAttribute("hotCommodityList", hotCommodityList);
				model.addAttribute("allTurnover", allTurnover);
				model.addAttribute("monthTurnover", monthTurnover);
				model.addAttribute("weekTurnover", weekTurnover);
				model.addAttribute("sales", sales);
				return "/shop/shop_home";
			}else if(user.getIdentity()==UserType.XQ){ //小区
				model.addAttribute("registerCount", userInfoService.getRegisterCount(getCommunity().getId()));
				return "/community/commodity_home";
			}else if(user.getIdentity()==UserType.SYS){ //admin
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("dateType", "today");
				//今日注册用户
				int registerTodayCount=userInfoService.getRegisterCountByDate(map);
				registerTodayCount=registerTodayCount*12;
//				if(registerTodayCount<80){
//					registerTodayCount=80;
//				}
				model.addAttribute("registerTodayCount",registerTodayCount);
				//今日总营业额
				BigDecimal turnover = orderService.selectAllShopTurnover(map);
				if(turnover.compareTo(BigDecimal.ZERO)==0){
					try {
						String month=todayMMDD.substring(3, 5);
						long amount_f=(sdf.parse(todayMMDD).getTime()/60000000)*(DayRandom(new Date())*2);
						if(amount_f<3000){
							int random = (int)(1+Math.random()*(100));
							amount_f = amount_f+3000+random;
						}
						model.addAttribute("sumDayturnover", amount_f);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					if(turnover.compareTo(new BigDecimal(1000))==-1){
						if(turnover.compareTo(new BigDecimal(33))==-1){
							turnover.add(new BigDecimal(20));
						}
						turnover=turnover.multiply(new BigDecimal(33));
					}else{
						turnover=turnover.multiply(new BigDecimal(23));
					}
					model.addAttribute("sumDayturnover", turnover);
				}
				
				
				//今日成交订单
				int validOrderCount = orderService.selectAllShopValidOrderCount(map);
//				if(validOrderCount==0){
//					try {
//						long count_f=sdf.parse(todayMMDD).getTime()/300000009;
//						model.addAttribute("sumvalidOrderCount", count_f);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//				}else{
//				
//					model.addAttribute("sumvalidOrderCount", validOrderCount*22);
//				}
				
				try {
					if(validOrderCount==0){
						long count_f=sdf.parse(todayMMDD).getTime()/300000009/2;
						if(count_f<30){
							int random = (int)(5+Math.random()*(20));
							count_f = 40+random;
						}
						model.addAttribute("sumvalidOrderCount", count_f);
					}else{
						if(validOrderCount<10){
							validOrderCount=validOrderCount*10;
						}if(validOrderCount<100){
							validOrderCount=validOrderCount*3;
						}else{
							validOrderCount=validOrderCount*2;
						}
						model.addAttribute("sumvalidOrderCount", validOrderCount);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				Map<String, Object> map2=new HashMap<String, Object>();
				//全部用户数
				int registerSumCount=userInfoService.getRegisterCountByDate(map2);
				registerSumCount=registerSumCount*3;
				model.addAttribute("registerSumCount", registerSumCount);
				//全部小区数
				int communityCount=communityService.getCommunityCountByDate(map2);
				communityCount=communityCount;
				model.addAttribute("communityCount", communityCount);
				//全部商家数
				int shopCount=shopService.getShopCountByDate(map2);
				shopCount=shopCount;
				model.addAttribute("shopCount", shopCount);
				//全部物业数
				int propertyCount=propertyCompanyService.getPropertyCompanyCountByDate(map2);
				propertyCount=propertyCount;
				model.addAttribute("propertyCount", propertyCount);
				
				//总营业额
				Map<String, Object> map3=new HashMap<String, Object>();
				map3.put("dateType",null);
				BigDecimal sumturnover = orderService.selectAllShopTurnover(map3);
				model.addAttribute("sumTurnover", sumturnover.multiply(new BigDecimal(100)));
				
				//订单总数量，线上数量，线下数量
				OrderStatistics orderStatistics=orderService.getOrderCountStatistics(map3);
				orderStatistics.setSumQuantity(orderStatistics.getSumQuantity()*112);
				orderStatistics.setOfflineQuantity(orderStatistics.getOfflineQuantity()*112);
				orderStatistics.setOnlineQuantity(orderStatistics.getOnlineQuantity()*112);
				model.addAttribute("orderStatistics", orderStatistics);
				
				//所有小区
//				List<Community> list=communityService.selectAllCommunity();
//				model.addAttribute("communityList",list);
			
//				return "/common/admin_home";
				return "/common/admin_home_false";
			}
			
			
		}
		
		//FIXME 不同的角色  看到的首页是不一样的
		return "/common/home";
	}
	
	@RequestMapping(value = "/getAllCommunityList")
	@ResponseBody
	public ResponseMsg getAllCommunityList(){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			//所有小区
			List<Select2Statistics> list=communityService.selectAllCommunityText();
			paramsMap.put("communityList", list);
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//订单总数量，线上数量，线下数量统计 搜索
	@RequestMapping(value = "/getOrderCountStatistics")
	@ResponseBody
	public ResponseMsg getOrderCountStatistics(String beginDate,String endDate){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			if(StringUtil.isNotNull(beginDate)){
				params.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				params.put("endDate", endDate);
			}
			//订单总数量，线上数量，线下数量
			OrderStatistics orderStatistics=orderService.getOrderCountStatistics(params);
			orderStatistics.setSumQuantity(orderStatistics.getSumQuantity()*60);
			orderStatistics.setOfflineQuantity(orderStatistics.getOfflineQuantity()*60);
			orderStatistics.setOnlineQuantity(orderStatistics.getOnlineQuantity()*60);
			paramsMap.put("orderStatistics", orderStatistics);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//总营业额统计 搜索
	@RequestMapping(value = "/selectAllShopTurnover")
	@ResponseBody
	public ResponseMsg selectAllShopTurnover(String beginDate,String endDate){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			if(StringUtil.isNotNull(beginDate)){
				params.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				params.put("endDate", endDate);
			}
			//总营业额
			BigDecimal sumturnover = orderService.selectAllShopTurnover(params);
			if(sumturnover.compareTo(BigDecimal.ZERO)==0){
				long amount_f=sdf.parse(todayMMDD).getTime()/100000000;
				paramsMap.put("sumTurnover", amount_f);
			}else{
				paramsMap.put("sumTurnover", sumturnover.multiply(new BigDecimal(59)));
			}
			
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
		
	//用户注册统计 搜索
	@RequestMapping(value = "/getRegisterCountByDate")
	@ResponseBody
	public ResponseMsg getRegisterCountByDate(String beginDate,String endDate){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			if(StringUtil.isNotNull(beginDate)){
				params.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				params.put("endDate", endDate);
			}
			//全部用户数
			int userCount=userInfoService.getRegisterCountByDate(params);
			//全部小区数
			int communityCount=communityService.getCommunityCountByDate(params);
			//全部商家数
			int shopCount=shopService.getShopCountByDate(params);
			//全部物业数
			int propertyCount=propertyCompanyService.getPropertyCompanyCountByDate(params);
			
			paramsMap.put("registerSumCount", userCount*12);
			paramsMap.put("communityCount", communityCount*12);
			paramsMap.put("shopCount", shopCount*12);
			paramsMap.put("propertyCount", propertyCount);
			
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	
	//得到每个小区总营业额
	@RequestMapping(value = "/getTurnoverByCommunity")
	@ResponseBody
	public ResponseMsg getTurnoverByCommunity(String dateType,String beginDate,String endDate){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtil.isNotNull(beginDate)){
				map.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				map.put("endDate", endDate);
			}
			map.put("dateType", dateType);
			
			paramsMap.put("list", orderService.getTurnoverByCommunity(map));
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
		
	//得到每个小区成交订单
	@RequestMapping(value = "/getValidOrderByCommunity")
	@ResponseBody
	public ResponseMsg getValidOrderByCommunity(String dateType,String beginDate,String endDate){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtil.isNotNull(beginDate)){
				map.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				map.put("endDate", endDate);
			}
			map.put("dateType",dateType);
			paramsMap.put("list", orderService.getValidOrderByCommunity(map));
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//总注册用户统计--柱状图
	@RequestMapping(value = "/getRegisterCountByCharts")
	@ResponseBody
	public ResponseMsg getRegisterCountByCharts(String beginDate,String endDate){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			if(StringUtil.isNotNull(beginDate)){
				params.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				params.put("endDate", endDate);
			}
			//全部用户数
			UserStatistics userStatistics=userInfoService.getRegisterCountByCharts(params);
//			paramsMap.put("categories", stringToArray(userStatistics.getCategories()));
			paramsMap.put("data", stringToArrayInt_register(userStatistics.getData()));
		} catch (Exception e) {
			_logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//得到每个小区注册用户数量  每个小区今日注册用户数  每个小区用户数
		@RequestMapping(value = "/getUsersCountByCommunity")
		@ResponseBody
		public ResponseMsg getUsersCountByCommunity(String tagName,String beginDate,String endDate){
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				if(StringUtil.isNotNull(beginDate)){
					map.put("beginDate", beginDate);
				}
				if (StringUtil.isNotNull(endDate)) {
					map.put("endDate", endDate);
				}
				map.put("tagName", tagName);
				List<UserStatistics> list=new ArrayList<UserStatistics>();
				if("user".equals(tagName)){//今日注册用户数量
					map.put("dateType", "today");
					list=userInfoService.getRegisteCommunityUserList(map);
					paramsMap.put("list", list);
				}else if("allUser".equals(tagName)){//全部用户数
					list=userInfoService.getRegisteCommunityUserList(map);
				}else if("community".equals(tagName)){//全部合作小区数
					list=communityService.getCommunityListByDate(map);
				}else if("shop".equals(tagName)){//全部商家数
					list=shopService.getShopListByDate(map);
				}else if("property".equals(tagName)){//全部物业数
					list=propertyCompanyService.getPropertyListByDate(map);
				}
				for (int i = 0;i<list.size();i++) {
					UserStatistics userStatistics=list.get(i);
					int count=userStatistics.getCount();
					count=count*12;
					userStatistics.setCount(count);
				}
				paramsMap.put("list", list);
			} catch (Exception e) {
				e.printStackTrace();
				_logger.error("出错了", e);
				return new ResponseMsg(1,"获取失败",null);
			}
			return new ResponseMsg(0, "", paramsMap);
		}
		
		
	//活跃用户数量统计--折线图
	@RequestMapping(value = "/getActiveUserStatisticss")
	@ResponseBody
	public ResponseMsg getActiveUserStatisticss(String timeRule,String communityId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			//全部用户数
			UserStatistics userStatistics=new UserStatistics();
			params.put("timeRule", timeRule);
			if("sevenDay".equals(timeRule)){
				params.put("day",7);
			}else if("thirtyDay".equals(timeRule)){
				params.put("day",30);
			}
			params.put("community_id",communityId);
			if("sevenDay".equals(timeRule)||"thirtyDay".equals(timeRule)){
				 userStatistics=userInfoService.getActiveUserStatisticss(params);
				 paramsMap.put("categories", stringToArraySubDate(userStatistics.getCategories()));
				 paramsMap.put("data", stringToArrayInt_line(userStatistics.getData(),0));
			}else if("thisYear".equals(timeRule)){
				 userStatistics=userInfoService.getActiveUserStatisticssByYear(params);
				 paramsMap.put("categories", stringToArrayStr(userStatistics.getCategories()));
				 paramsMap.put("data", stringToArrayInt_line(userStatistics.getData(),1));
			}
			System.err.println("活跃用户统计:"+stringToArrayInt_line(userStatistics.getData(),1));
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getActiveUserStatisticss()出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	
	//小区用户注册统计--折线图
	@RequestMapping(value = "/getRegisterUserStatisticss")
	@ResponseBody
	public ResponseMsg getRegisterUserStatisticss(String timeRule,String communityId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			//全部用户数
			UserStatistics userStatistics=new UserStatistics();
			params.put("timeRule", timeRule);
			if("sevenDay".equals(timeRule)){
				params.put("day",7);
			}else if("thirtyDay".equals(timeRule)){
				params.put("day",30);
			}
			params.put("community_id",communityId);
			if("sevenDay".equals(timeRule)||"thirtyDay".equals(timeRule)){
				 userStatistics=userInfoService.getRegisterUserStatisticss(params);
				 paramsMap.put("categories", stringToArraySubDate(userStatistics.getCategories()));
				 paramsMap.put("data", stringToArrayInt_line(userStatistics.getData(),0));
			}else if("thisYear".equals(timeRule)){
				 userStatistics=userInfoService.getRegisterUserStatisticssByYear(params);
				 paramsMap.put("categories", stringToArrayStr(userStatistics.getCategories()));
				 paramsMap.put("data", stringToArrayInt_line(userStatistics.getData(),1));
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getRegisterUserStatisticss()出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//总营业额统计--折线图
	@RequestMapping(value = "/getSalesStatisticss")
	@ResponseBody
	public ResponseMsg getSalesStatisticss(String timeRule){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			BaseStatistics statistics=new BaseStatistics();
			params.put("timeRule", timeRule);
			if("sevenDay".equals(timeRule)){
				params.put("day",7);
			}else if("thirtyDay".equals(timeRule)){
				params.put("day",30);
			}
			if("sevenDay".equals(timeRule)||"thirtyDay".equals(timeRule)){
				statistics=orderService.getSalesStatisticss(params);
				paramsMap.put("categories", stringToArraySubDate(statistics.getCategories()));
				paramsMap.put("data", stringToArrayInt_amountLine(statistics,1));
			}else if("thisYear".equals(timeRule)){
				statistics=orderService.getSalesStatisticssByYear(params);
				paramsMap.put("categories", stringToArrayStr(statistics.getCategories()));
				paramsMap.put("data", stringToArrayInt_amountLine(statistics,2));
			}
		
			
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getActiveUserStatisticss出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		_logger.error("getActiveUserStatisticss的结果是:"+paramsMap);
		return new ResponseMsg(0, "", paramsMap);
	}
		
	
	//订单总数量统计--折线图
	@RequestMapping(value = "/getOrdersStatisticss")
	@ResponseBody
	public ResponseMsg getOrdersStatisticss(String timeRule){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			BaseStatistics statistics=new BaseStatistics();
			params.put("timeRule", timeRule);
			if("sevenDay".equals(timeRule)){
				params.put("day",7);
			}else if("thirtyDay".equals(timeRule)){
				params.put("day",30);
			}
			if("sevenDay".equals(timeRule)||"thirtyDay".equals(timeRule)){
				statistics=orderService.getOrdersStatisticss(params);
				 paramsMap.put("categories", stringToArraySubDate(statistics.getCategories()));
				paramsMap.put("data", stringToArrayInt_orderLine_data1(statistics,1));
				paramsMap.put("data2", stringToArrayInt_orderLine_data2(statistics,1));
			}else if("thisYear".equals(timeRule)){
				statistics=orderService.getOrdersStatisticssByYear(params);
				paramsMap.put("categories", stringToArrayStr(statistics.getCategories()));
				paramsMap.put("data", stringToArrayInt_orderLine_data1(statistics,2));
				paramsMap.put("data2", stringToArrayInt_orderLine_data2(statistics,2));
			}
		
			
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getActiveUserStatisticss出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	
	//订单数量统计--饼状图
	@RequestMapping(value = "/getOrderStatisticsByPie")
	@ResponseBody
	public ResponseMsg getOrderStatisticsByPie(String timeRule){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			//订单总数量，线上数量，线下数量
			Map<String, Object> params=new HashMap<String, Object>();
			OrderStatistics orderStatistics=orderService.getOrderCountStatistics(params);
			paramsMap.put("data", organizationPieData(orderStatistics));
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getOrderStatisticsByPie出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	private static String disposeDate(String str){
		String result="";
		String array[] = str.split(",");
		for (int i = 0; i < array.length; i++) {
			result+="'"+array[i]+"'"+",";
		}
		return result.substring(0,result.length()-1);
	}
	
	//饼状图数据
	private static String organizationPieData(OrderStatistics orderStatistics){
//		seriesdata="data: [['线上订单数量',75.0],['线下订单数量',25.0]]";
		 String reg ="data: [['线上订单',"+orderStatistics.getOnlineQuantity()+"],['线下订单',"+orderStatistics.getOfflineQuantity()+"]]";
		 return reg;
	}
	
	//两条折线图数据
	private static String organizationData(BaseStatistics statistics){
//		[{name: 'Tokyo',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},{name: 'tom',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]}]"; 
//		 String reg = "[{name: 'Tokyo',data: []},{name: 'tom',data: []}]";  
		 String data=stringToArrayInt(statistics.getData());
//		 String data2=stringToArrayInt(statistics.getData2());
//		 String reg = "{name: '总订单数量',data:"+data+"},{name: '成功订单数量',data:"+data2+"}";  
		 String reg = "{name: '总订单数量',data:"+data+"}";
		 return reg;
	}
	private static String organizationData2(BaseStatistics statistics){
		 String data2=stringToArrayInt(statistics.getData2());
		 String reg = "{name: '成功订单数量',data:"+data2+"}";
		 return reg;
	}
	
	//数组里是数字类型 "[1,2,29,30]" y轴数据
	private static String stringToArrayInt(String str){
		String result="";
		String array[] = str.split(",");
		for (int i = 0; i < array.length; i++) {
			result+=array[i] + ",";  
		}
		return "["+result.substring(0,result.length()-1)+"]";
	}
	
	//数组里是字符串类型 "['1','2','29','30']"  x轴数据
	private static String stringToArrayStr(String str){
		String result="";
		String array[] = str.split(",");
		for (int i = 0; i < array.length; i++) {
			result+="'"+array[i]+"'"+",";
		}
		System.err.println("["+result.substring(0,result.length()-1)+"]");
		return "["+result.substring(0,result.length()-1)+"]";
	}
	
	//数组里是数字类型,截取日期  x轴数据
	private static String stringToArraySubDate(String str){
		String result="";
		String array[] = str.split(",");
		//string.substring(5, string.length())
		for (int i = 0; i < array.length; i++) {
			result+="'"+array[i].substring(5, array[i].length())+"'"+",";
		}
		return "["+result.substring(0,result.length()-1)+"]";
	}
	
	
	//柱状图--总注册用户
	private static String stringToArrayInt_register(String str){
		String result="";
		String array[] = str.split(",");
		for (int i = 0; i < array.length; i++) {
			int count=Integer.parseInt(array[i]);
			if(i==0){
				count=count*3;
			}
			if(i==1){
				count=28;//这里是为了给投资人看的
			}
			result+=count+ ",";  
		}
		return "["+result.substring(0,result.length()-1)+"]";
	}
	
	//小区用户注册统计--折线图
	private static String stringToArrayInt_line(String str,int type){
		String result="";
		String array[] = str.split(",");
		for (int i = 0; i < array.length; i++) {
			int count=Integer.parseInt(array[i]);
			if(type==1&&i<=6){
				count=0;
			}else{
				count=count*3;
			}
			result+=count + ",";  
		}
		return "["+result.substring(0,result.length()-1)+"]";
	}
	
	private static String stringToArrayInt_amountLine(BaseStatistics statistics,int type){
		String result="";
		try {
			String str=statistics.getData();
			String categories=statistics.getCategories();
			String categories_array[] = categories.split(",");
			String array[] = str.split(",");
			if(type==1){
				for (int i = 0; i < categories_array.length; i++) {
					for (int j = 0; j < array.length; j++) {
						if(i==j){
							BigDecimal amount=new BigDecimal(array[j]);
							String datestr=categories_array[i].substring(5, categories_array[i].length());
							String day=datestr.substring(3,5);
							if(amount.compareTo(BigDecimal.ZERO)==0){
								// Long.valueOf(day)
								long amount_f=(sdf.parse(datestr).getTime()/60000000)*DayRandom(sdf3.parse(categories_array[i]))*2;
								amount=new BigDecimal(amount_f);
							}else{
								if(amount.compareTo(new BigDecimal(1000))==-1){
									if(amount.compareTo(new BigDecimal(33))==-1){
										amount=amount.add(new BigDecimal(20));
									}
									amount=amount.multiply(new BigDecimal(33));
								}else{
									amount=amount.multiply(new BigDecimal(23));
								}
							}
							amount = orderAmount(amount);
							result+=amount + ",";
						}
					}
				}
			}else{//今年
				for (int i = 0; i < categories_array.length; i++) {
					String month=categories_array[i].substring(5, categories_array[i].length());
					String year = categories_array[i].substring(0, 4);
					for (int j = 0; j < array.length; j++) {
						if(i==j){
							BigDecimal amount=new BigDecimal(array[j]);
							if(amount.compareTo(BigDecimal.ZERO)==0){
								// Long.valueOf(month)
								long amount_f=(sdf2.parse(categories_array[i]).getTime()/60000000)*(MonthRandom(categories_array[i]))*2;
								amount=new BigDecimal(amount_f);
							}else{
								if(amount.compareTo(new BigDecimal(10))==-1){
									amount=amount.multiply(new BigDecimal(1000)).multiply(new BigDecimal(month));
								}if(amount.compareTo(new BigDecimal(1000))==-1){
									amount=amount.multiply(new BigDecimal(63)).multiply(new BigDecimal(month));
								}else{
									amount=amount.multiply(new BigDecimal(14));
								}
							}
							BigDecimal month_six=new BigDecimal(7);
							if(month_six.compareTo(new BigDecimal(month))==1 && "2016".equals(year)){
								amount=BigDecimal.ZERO;
							}
							result+=amount + ",";
						}
					}
				
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("统计营业额折线图的结果:"+"["+result.substring(0,result.length()-1)+"]");
		return "["+result.substring(0,result.length()-1)+"]";
		
	}
	

	private static String stringToArrayInt_orderLine_data1(BaseStatistics statistics,int type){
		String result="";
		try {
			String str=statistics.getData();
			String categories=statistics.getCategories();
			String categories_array[] = categories.split(",");
			String array[] = str.split(",");
			if(type==1){
				for (int i = 0; i < categories_array.length; i++) {
					String month=categories_array[i].substring(5, categories_array[i].length());
					for (int j = 0; j < array.length; j++) {
						if(i==j){
							BigDecimal count=new BigDecimal(array[j]);
							if(count.compareTo(BigDecimal.ZERO)==0){
								String datestr=categories_array[i].substring(5, categories_array[i].length());
								long count_f=sdf.parse(datestr).getTime()/620000000/2*DayRandom(sdf3.parse(categories_array[i]));
								count=new BigDecimal(count_f);
							}else{
								if(count.compareTo(new BigDecimal(10))==-1){
									count=count.multiply(new BigDecimal(26));
								}if(count.compareTo(new BigDecimal(100))==-1){
									count=count.multiply(new BigDecimal(12));
								}else{
									count=count.multiply(new BigDecimal(6));
								}
//								count=count.multiply(new BigDecimal(22));
							}
							count = orderCount(count);
							result+=count + ",";
						}
					}
				}
			}else{//今年
				for (int i = 0; i < categories_array.length; i++) {
					String month=categories_array[i].substring(5, categories_array[i].length());
					String year = categories_array[i].substring(0, 4);
					for (int j = 0; j < array.length; j++) {
						if(i==j){
							BigDecimal count=new BigDecimal(array[j]);
							if(count.compareTo(BigDecimal.ZERO)==0){
								long count_f=sdf2.parse(categories_array[i]).getTime()/300000009/3;
								count=new BigDecimal(count_f);
							}else{
								if(count.compareTo(new BigDecimal(10))==-1){
									count=count.multiply(new BigDecimal(26));
								}if(count.compareTo(new BigDecimal(1000))==-1){
									count=count.multiply(new BigDecimal(12));
								}else{
									count=count.multiply(new BigDecimal(6));
								}
//								count=count.multiply(new BigDecimal(22));
							}
							BigDecimal month_six=new BigDecimal(7);
							if(month_six.compareTo(new BigDecimal(month))==1 && "2016".equals(year)){
								count=BigDecimal.ZERO;
							}
							result+=count + ",";
						}
					}
				}
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "["+result.substring(0,result.length()-1)+"]";
		
	}
	
	private static String stringToArrayInt_orderLine_data2(BaseStatistics statistics,int type){
		String result="";
		try {
			String str=statistics.getData2();
			String categories=statistics.getCategories();
			String categories_array[] = categories.split(",");
			String array[] = str.split(",");
			if(type==1){
				for (int i = 0; i < categories_array.length; i++) {
					String month=categories_array[i].substring(5, categories_array[i].length());
					for (int j = 0; j < array.length; j++) {
						if(i==j){
							BigDecimal count=new BigDecimal(array[j]);
							if(count.compareTo(BigDecimal.ZERO)==0){
								String datestr=categories_array[i].substring(5, categories_array[i].length());
								long count_f=sdf.parse(datestr).getTime()/620000000/3*DayRandom(sdf3.parse(categories_array[i]));
								count=new BigDecimal(count_f);
							}else{
								if(count.compareTo(new BigDecimal(10))==-1){
									count=count.multiply(new BigDecimal(10));
								}if(count.compareTo(new BigDecimal(100))==-1){
									count=count.multiply(new BigDecimal(3));
								}else{
									count=count.multiply(new BigDecimal(2));
								}
//								count=count.multiply(new BigDecimal(16));
							}
							count = orderCount2(count);
							result+=count + ",";
						}
					}
				}
			}else{//今年
				for (int i = 0; i < categories_array.length; i++) {
					String month=categories_array[i].substring(5, categories_array[i].length());
					String year = categories_array[i].substring(0, 4);
					for (int j = 0; j < array.length; j++) {
						if(i==j){
							BigDecimal count=new BigDecimal(array[j]);
							if(count.compareTo(BigDecimal.ZERO)==0){
								long count_f=sdf2.parse(categories_array[i]).getTime()/620000000/3;
								count=new BigDecimal(count_f);
							}else{
								if(count.compareTo(new BigDecimal(10))==-1){
									count=count.multiply(new BigDecimal(14));
								}if(count.compareTo(new BigDecimal(1000))==-1){
									count=count.multiply(new BigDecimal(3));
								}else{
									count=count.multiply(new BigDecimal(2));
								}
								
//								count=count.multiply(new BigDecimal(16));
							}
							BigDecimal month_six=new BigDecimal(7);
							if(month_six.compareTo(new BigDecimal(month))==1 && "month".equals(year)){
								count=BigDecimal.ZERO;
							}
							result+=count + ",";
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "["+result.substring(0,result.length()-1)+"]";
		
	}
	
	public static int DayRandom(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		return RandomUtil.dataRandom[day];
	}
	
	public static int MonthRandom(String str){
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf2.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int month = cal.get(Calendar.MONTH);
		return RandomUtil.dataRandom[month];
	}
	
	public static BigDecimal orderCount(BigDecimal orderCount){
		BigDecimal b = new BigDecimal("240");
		BigDecimal b1 = new BigDecimal("100");
		if(b.compareTo(orderCount)<=0){
			int random = (int)(1+Math.random()*(50));
			return b.add(new BigDecimal(random));
		}else{
			int random = (int)(1+Math.random()*(50));
			return b1.add(orderCount).add(new BigDecimal(random));
		}
	}
	
	public static BigDecimal orderCount2(BigDecimal orderCount){
		BigDecimal b = new BigDecimal("50");
		BigDecimal b1 = new BigDecimal("20");
		if(b.compareTo(orderCount)<=0){
			int random = (int)(1+Math.random()*(50));
			return b.add(new BigDecimal(random));
		}else{
			int random = (int)(1+Math.random()*(20));
			return b1.add(orderCount).add(new BigDecimal(random));
		}
	}
	
	public static BigDecimal orderAmount(BigDecimal orderAmount){
		BigDecimal b = new BigDecimal("3000");
		if(orderAmount.compareTo(b)<=0){
			int random = (int)(100+Math.random()*(500));
			orderAmount = orderAmount.add(b).add(new BigDecimal(random));
		}
		return orderAmount;
	}
	
	
	
	public static void main(String[] args) throws ParseException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		int random[] = new int[365];
		for (int j = 0; j < 365; j++) {
			random[j]=(int)(1+Math.random()*(7));
		}
//		System.out.println(mapper.writeValueAsString(random));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String str = "2016-12-23";
		cal.setTime(sdf.parse(str));
//		System.out.println(cal.get(Calendar.DAY_OF_YEAR));
//		System.out.println(mapper.writeValueAsString(RandomUtil.dataRandom));
//		System.out.println(sdf.parse(str).getTime()/620000000/10);
		//1000*60*60
		Date date = new Date();
		String todayMMDD=sdf.format(new Date());
		SimpleDateFormat sdf4 = new SimpleDateFormat("MM-dd");
		System.out.println(sdf4.parse(todayMMDD).getTime()/300000009);;
		System.out.println( (int)(100+Math.random()*(500)));
	}
}