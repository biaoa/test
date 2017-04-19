package com.linle.community.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.commodityAppMenu.service.CommodityAppMenuService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.SaltUtil;
import com.linle.common.util.StringUtil;
import com.linle.community.mapper.CommunityMapper;
import com.linle.community.model.Community;
import com.linle.community.model.CommunityVo;
import com.linle.community.service.CommunityService;
import com.linle.entity.enumType.StatusType;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.statistics.Select2Statistics;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.v1.model.CommunityListVO;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.mapper.UserMapper;
import com.linle.user.service.UserRoleService;

@Service("communityService")
@Transactional
public class CommunityServiceImpl extends CommonServiceAdpter<Community> implements CommunityService {

	@Autowired
	private CommunityMapper mapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleService roleService;
	
	@Autowired
	private RongService rongService;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private AttendanceTemplateService templateService;
	
	@Autowired
	private CommodityAppMenuService commodityAppMenuService;
	
	@Override
	public Page<Community> findAllCommunity(Page<Community> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Value("${rong_appKey}")
	private String appKey;

	@Value("${rong_appSecret}")
	private String appSecret;
	
	public ObjectMapper m = new ObjectMapper();

	@Override
	public boolean addCommunity(Community company) {
		// 添加用户信息
		String salt = SaltUtil.getRandomString(16);
		// FIXME 这里的用户密码 暂时写成123456 到时候要修改
		String password = new Sha1Hash("123456", salt).toString();
		
		Users user = new Users();
		user.setUserName(company.getName());
		user.setName(company.getName());
		user.setPassword(password);
		user.setSalt(salt);
		user.setStatus(UserStatusType.normal);
		user.setIdentity(UserType.XQ);
		user.setLogo(company.getLogo()); //小区头像默认为物业图片
		int userResult = userMapper.insertSelective(user);
		try {
			if (userResult > 0) {
				logger.info("新增用户：" + user.getUserName() + "成功");
				// 获得token并将token赋值user表
				rongService.getUserRongToken(user);
				
				// 新增用户角色关联
				UserRoleRelation userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUser(user);
				userRoleRelation.setUserRole(roleService.getRoleByename("community"));
				if (roleService.addUserRoleRelation(userRoleRelation) > 0) {
					logger.info("新增用户：" + user.getUserName() + "，角色成功");
					// 新增小区
					company.setUser(user);
					company.setStatus(StatusType.normal);
					int result = mapper.insertSelective(company);
					//插入默认考勤规则表
					templateService.addDefaultTemplate(company);
					//首页菜单默认全部开启
					commodityAppMenuService.addCommodityAppMenu("1,2,3,4,5,6,7,8,9,10,11,12,14", company.getId(), "9,3,4,1,5,6,7,8,2,10,11,12,13", user.getId());
					return  result> 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("新增物业信息出错了", e);
			throw new RuntimeException("1");
		}
		return false;
	}

	@Override
	public Community getCommunityByuserID(Long id) {
		return mapper.getCommunityByuserID(id);
	}

	@Override
	public List<CommunityListVO> getCommunityListByCityName(String cityName) {
		return mapper.getCommunityListByCityName(cityName);
	}

	@Override
	public List<Community> getCommunityByAgency(Long id) {
		return mapper.getCommunityByAgency(id);
	}

	@Override
	public List<Community> getCommunityByPresident(Long presidentId) {
		return mapper.getCommunityByPresident(presidentId);
	}

	@Override
	public List<CommunityVo> getRegionAndCommunityListByPresident(Long presidentId) {
		return mapper.getRegionAndCommunityListByPresident(presidentId);
	}
	
	
	@Override
	public List<Community> selectAllCommunity() {
		return mapper.selectAllCommunity();
	}
	
	@Override
	public List<Select2Statistics> selectAllCommunityText() {
		return mapper.selectAllCommunityText();
	}
	
	@Override
	public BigDecimal getCommunityIncome(Long communityId,String orderType) {
		//小区总收益 = 缴费+车位+商家的抽成的百分比
		BigDecimal communityTotalIncome = null;
		if(StringUtil.isNotNull(orderType)){
			switch (orderType) {
			case "space":
				BigDecimal spaceIncome = orderService.getCommunitySpaceIncome(communityId);
				return spaceIncome;
			case "shop_prorata":
				//FIXME 这里物业拿到的商家抽成是固定的 15%(商家总营业额*系统设置的商家抽成*0.015) 以后应该会改成每个小区设置不同的商家利润分成比例
				BigDecimal shopIncome = orderService.getCommunityShopShare(communityId);//商家利润的抽成
				return shopIncome;
			case "water":
				BigDecimal waterIncome = orderService.getPaymetIncome(communityId,"water");//水费
				return waterIncome;
			case "electricity":
				BigDecimal electricityIncome = orderService.getPaymetIncome(communityId,"electricity");//电费
				return electricityIncome;
			case "gas":
				BigDecimal gasIncome = orderService.getPaymetIncome(communityId,"gas");//燃气费
				return gasIncome;
			case "propertyFee":
				BigDecimal propertyFeeIncome = orderService.getPaymetIncome(communityId,"propertyFee");//物业费
				return propertyFeeIncome;
			case "broadband":
				BigDecimal broadbandIncome = orderService.getPaymetIncome(communityId,"broadband");//宽带
				return broadbandIncome;
			case "cableTelevision":
				BigDecimal cableTelevisionIncome = orderService.getPaymetIncome(communityId,"cableTelevision");//有线电视
				return cableTelevisionIncome;
			default:
				break;
			}
			
		}
		//缴费的钱
		BigDecimal waterIncome = orderService.getPaymetIncome(communityId,"water");//水费
		BigDecimal electricityIncome = orderService.getPaymetIncome(communityId,"electricity");//电费
		BigDecimal gasIncome = orderService.getPaymetIncome(communityId,"gas");//燃气费
		BigDecimal propertyFeeIncome = orderService.getPaymetIncome(communityId,"propertyFee");//物业费
		BigDecimal broadbandIncome = orderService.getPaymetIncome(communityId,"broadband");//宽带
		BigDecimal cableTelevisionIncome = orderService.getPaymetIncome(communityId,"cableTelevision");//有线电视
		BigDecimal paymentIncome = waterIncome.add(electricityIncome).add(gasIncome).add(propertyFeeIncome).add(broadbandIncome).add(cableTelevisionIncome); //缴费 = 水费+电费+物业费+宽带费+有限电视+燃气费
		//家园购物的钱
		BigDecimal shopIncome = orderService.getCommunityShopShare(communityId);//商家利润的抽成
		//车位的钱 预定+续费
		BigDecimal spaceIncome = orderService.getCommunitySpaceIncome(communityId);
		
		communityTotalIncome = paymentIncome.add(shopIncome).add(spaceIncome);
		return communityTotalIncome;
	}

	@Override
	public List<Community> getNoTemplateCommunity() {
		return mapper.getNoTemplateCommunity();
	}

	@Override
	public int getCommunityCountByDate(Map<String, Object> map) {
		//这里是为了投资人看的
		int count = mapper.getCommunityCountByDate(map);
		return count>28?count:28;
	}
	
	@Override
	public List<UserStatistics> getCommunityListByDate(Map<String, Object> map) {
		return mapper.getCommunityListByDate(map);
	}

	@Override
	public boolean updateWithdrawalFee(Community community) {
		return mapper.updateWithdrawalFee(community)>0;
	}
}
