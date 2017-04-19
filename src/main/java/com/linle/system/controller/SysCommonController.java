package com.linle.system.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.commodity.service.CommodityService;
import com.linle.common.base.BaseController;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.community.service.CommunityService;
import com.linle.communitySuggestions.service.CommunitySuggestionsService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.statistics.BaseStatistics;
import com.linle.entity.statistics.OrderStatistics;
import com.linle.entity.statistics.Select2Statistics;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.entity.sys.RepairRecord;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.HotCommodityVO;
import com.linle.entity.vo.SalesVO;
import com.linle.propertyCompany.service.PropertyCompanyService;
import com.linle.repairRecord.service.RepairRecordService;
import com.linle.shop.service.ShopService;
import com.linle.sysOrder.service.SysOrderService;


@Controller
@RequestMapping("/sys")
public class SysCommonController extends BaseController{
	
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
	
	@Autowired
	private RepairRecordService repairRecordService;
	
	@Autowired
	private CommunitySuggestionsService communitySuggestionsService;
	
	@RequestMapping(value = "/home")
	public  String common(Model model,HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
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
				long community_id=getCommunity().getId();
				//1.注册用户
				model.addAttribute("registerCount", userInfoService.getRegisterCount(getCommunity().getId()));
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("community_id",community_id);
				//2.今日缴费金额,线上缴费，线下缴费
//				OrderStatistics orderStatistics=orderService.getOrderAmountStatistics(map);
//				model.addAttribute("orderStatistics", orderStatistics);
				//3.1待处理报修总数
				long unRepairCount=repairRecordService.getCountUnRepair(community_id);
				model.addAttribute("unRepairCount", unRepairCount);
				//3.2获得最新一条待处理报修记录
				RepairRecord repairRecord=repairRecordService.getNewUnRepairRecord(community_id);
				model.addAttribute("repairRecord", repairRecord);
				//4.1未查看意见反馈总数
				long unAdviceCount=communitySuggestionsService.getCountUnAdvice(community_id);
				model.addAttribute("unAdviceCount", unAdviceCount);
				//4.2获得最新一条意见反馈记录
				long begin=0;
				CommunitySuggestions communitySuggestions=communitySuggestionsService.getOneNewAdvice(community_id, begin);
				model.addAttribute("communitySuggestions", communitySuggestions);
				return "/community/commodity_home";
				
			}else if(user.getIdentity()==UserType.SYS){ //admin
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("dateType", "today");
				//今日注册用户
				model.addAttribute("registerTodayCount", userInfoService.getRegisterCountByDate(map));
				//今日总营业额
				BigDecimal turnover = orderService.selectAllShopTurnover(map);
				model.addAttribute("sumDayturnover", turnover);
				//今日成交订单
				int validOrderCount = orderService.selectAllShopValidOrderCount(map);
				model.addAttribute("sumvalidOrderCount", validOrderCount);
				
				Map<String, Object> map2=new HashMap<String, Object>();
				//全部用户数
				model.addAttribute("registerSumCount", userInfoService.getRegisterCountByDate(map2));
				//全部小区数
				model.addAttribute("communityCount", communityService.getCommunityCountByDate(map2));
				//全部商家数
				model.addAttribute("shopCount", shopService.getShopCountByDate(map2));
				//全部物业数
				model.addAttribute("propertyCount", propertyCompanyService.getPropertyCompanyCountByDate(map2));
				
				//总营业额
				Map<String, Object> map3=new HashMap<String, Object>();
				map3.put("dateType",null);
				BigDecimal sumturnover = orderService.selectAllShopTurnover(map3);
				model.addAttribute("sumTurnover", sumturnover);
				
				//订单总数量，线上数量，线下数量
				OrderStatistics orderStatistics=orderService.getOrderCountStatistics(map3);
				model.addAttribute("orderStatistics", orderStatistics);
				
				//所有小区
//				List<Community> list=communityService.selectAllCommunity();
//				model.addAttribute("communityList",list);
			
				return "/common/admin_home";
//				return "/common/admin_home_false";
			}
			
			
		}
		
		//FIXME 不同的角色  看到的首页是不一样的
		return "/common/home";
	}
	
	@RequestMapping(value = "/getUnCommunitySuggestions")
	@ResponseBody
	public ResponseMsg getUnCommunitySuggestions(long begin){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			long community_id=getCommunity().getId();
			//4.1未查看意见反馈总数
			long unAdviceCount=communitySuggestionsService.getCountUnAdvice(community_id);
			paramsMap.put("unAdviceCount", unAdviceCount);
			//4.2获得最新一条意见反馈记录
			CommunitySuggestions communitySuggestions=communitySuggestionsService.getOneNewAdvice(community_id, begin);
			paramsMap.put("communitySuggestions", communitySuggestions);
			paramsMap.put("begin", begin);
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
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
			paramsMap.put("sumTurnover", sumturnover);
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
			
			paramsMap.put("registerSumCount", userCount);
			paramsMap.put("communityCount", communityCount);
			paramsMap.put("shopCount", shopCount);
			paramsMap.put("propertyCount", propertyCount);
			
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//得到每个小区注册用户数量
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
			if("user".equals(tagName)){//今日注册用户数量
				map.put("dateType", "today");
				paramsMap.put("list", userInfoService.getRegisteCommunityUserList(map));
			}else if("allUser".equals(tagName)){//全部用户数
				paramsMap.put("list", userInfoService.getRegisteCommunityUserList(map));
			}else if("community".equals(tagName)){//全部合作小区数
				paramsMap.put("list", communityService.getCommunityListByDate(map));
			}else if("shop".equals(tagName)){//全部商家数
				paramsMap.put("list", shopService.getShopListByDate(map));
			}else if("property".equals(tagName)){//全部物业数
				paramsMap.put("list", propertyCompanyService.getPropertyListByDate(map));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
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
	
	//用户注册统计--柱状图
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
			paramsMap.put("data", stringToArrayInt(userStatistics.getData()));
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
		
	//活跃用户统计--折线图
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
			}else if("thisYear".equals(timeRule)){
				 userStatistics=userInfoService.getActiveUserStatisticssByYear(params);
				 paramsMap.put("categories", stringToArrayStr(userStatistics.getCategories()));
			}
			paramsMap.put("data", stringToArrayInt(userStatistics.getData()));
			
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
			}else if("thisYear".equals(timeRule)){
				 userStatistics=userInfoService.getRegisterUserStatisticssByYear(params);
				 paramsMap.put("categories", stringToArrayStr(userStatistics.getCategories()));
			}
			paramsMap.put("data", stringToArrayInt(userStatistics.getData()));
			
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getRegisterUserStatisticss()出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
	
	//营业额统计--折线图
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
			}else if("thisYear".equals(timeRule)){
				statistics=orderService.getSalesStatisticssByYear(params);
				paramsMap.put("categories", stringToArrayStr(statistics.getCategories()));
			}
			paramsMap.put("data", stringToArrayInt(statistics.getData()));
			
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getActiveUserStatisticss出错了", e);
			return new ResponseMsg(1,"获取失败",null);
		}
		return new ResponseMsg(0, "", paramsMap);
	}
		
	
	//订单数量统计--折线图
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
			}else if("thisYear".equals(timeRule)){
				statistics=orderService.getOrdersStatisticssByYear(params);
				paramsMap.put("categories", stringToArrayStr(statistics.getCategories()));
			}
			paramsMap.put("data", stringToArrayInt(statistics.getData()));
			paramsMap.put("data2", stringToArrayInt(statistics.getData2()));
			
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
}
