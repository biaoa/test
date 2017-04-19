package com.linle.shop.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.SaltUtil;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.model.ShopInfoVO;
import com.linle.mobileapi.v1.model.ShopItem;
import com.linle.mobileapi.v1.model.ShopVO;
import com.linle.shop.mapper.ShopMapper;
import com.linle.user.mapper.UserMapper;
import com.linle.user.service.UserRoleService;
import com.linle.withdraw.service.WithdrawService;


@Service
@Transactional
public class ShopServiceImpl extends CommonServiceAdpter<Shop> implements ShopService {
	
	@Autowired
	private ShopMapper mapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleService roleService;
	
	@Autowired
	private WithdrawService withdrawService;
	
	@Override
	public Page<Shop> findAllShops(Page<Shop> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public boolean createShop(Shop shop) {
		// 新增用户
		String salt = SaltUtil.getRandomString(16);
		// FIXME 这里的用户密码 暂时写成123456 到时候要修改
		String password = new Sha1Hash("123456", salt).toString();
		Users user = new Users();

		user.setUserName(shop.getShopName());
		user.setName(shop.getShopName());
		user.setPassword(password);
		user.setSalt(salt);
		user.setStatus(UserStatusType.normal);
		user.setIdentity(UserType.SJ);
		
		if (userMapper.insertSelective(user) > 0) {
			logger.info("新增用户：" + user.getUserName() + "成功");
			// 新增角色
			UserRoleRelation userRoleRelation = new UserRoleRelation();
			userRoleRelation.setUser(user);
			userRoleRelation.setUserRole(roleService.getRoleByename("business"));
			if (roleService.addUserRoleRelation(userRoleRelation) > 0) {
				logger.info("新增用户：" + user.getUserName() + "，角色成功");
				// 新增代理商
				shop.setUser(user);
				shop.setCreateDate(new Date());
				shop.setShopStatus(0);
				return mapper.insertSelective(shop) > 0;
			}
		}
		return false;
	}

	@Override
	public Shop selectByUserID(Long id) {
		return mapper.selectByUserID(id);
	}

	@Override
	public List<ShopItem> getShopListForAPI(Map<String, Object> map) {
		List<ShopItem> shopList = mapper.getShopListForAPI(map);
		LocalDate date = LocalDate.now();
//		Map<String, Object> params = new HashMap<>();
//		params.put("year", DateUtil.getYear());
//		params.put("month", DateUtil.getMonth());77
		for (ShopItem shopItem : shopList) {
			//如果当前时间的 小时和分钟大于营业开始时间
			if(shopItem.getStatus()==0){
				shopItem.setOperateStatus(true);
			}else{
				shopItem.setOperateStatus(false);
			}
//			if (compare_date(date+" "+shopItem.getBeginDate())<=0) {
//				if(compare_date(date+" "+shopItem.getEndDate())>=0){
//					shopItem.setOperateStatus(true);
//				}
//			}
		}
		return shopList;
	}

	@Override
	public ShopInfoVO selectShopInfoAPI(Map<String, Object> map) {
		return mapper.selectShopInfoAPI(map);
	}
	
	@Override
	public List<Shop> getAllActivityShop(long communityId) {
		return mapper.getAllActivityShop(communityId);
	}
	
	public static int pasInt(String args){
		return Integer.parseInt(args);
	}
	
	
	public static int compare_date(String DATE1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

	@Override
	public int selectShopSales(Map<String, Object> map) {
		return mapper.selectShopSales(map);
	}

	@Override
	public int selectShopSaleCommunity(Map<String, Object> map) {
		return mapper.selectShopSaleCommunity(map);
	}

	@Override
	public List<ShopItem> getHomeAD(Map<String, Object> map) {
		List<ShopItem> shopList=mapper.getHomeAD(map);
		for (ShopItem shopItem : shopList) {
			//如果当前时间的 小时和分钟大于营业开始时间
			if(shopItem.getStatus()==0){
				shopItem.setOperateStatus(true);
			}else{
				shopItem.setOperateStatus(false);
			}
		}
		return shopList;
	}

	@Override
	public BigDecimal getShopTotalIncome(Long id) {
		return mapper.getShopTotalIncome(id);
	}

	@Override
	public BigDecimal getShopWaitIncome(Long id) {
		return mapper.getShopWaitIncome(id);
	}

	@Override
	public BigDecimal getShopBalance(Long id) {
		Shop shop = mapper.selectByPrimaryKey(id);
		BigDecimal shopTotalIncome = getShopTotalIncome(shop.getId());
		BigDecimal withdrawed = withdrawService.sumWithdrawMoney(shop.getUser().getId(),"commodity");
		BigDecimal shopBalance = shopTotalIncome.subtract(withdrawed);
		shopBalance = shopBalance.multiply(new BigDecimal((100-shop.getCut()))).divide(new BigDecimal(100),2,BigDecimal.ROUND_FLOOR);
		return shopBalance;
	}

	@Override
	public int getShopCountByDate(Map<String, Object> map) {
		return mapper.getShopCountByDate(map);
	}
	
	@Override
	public List<UserStatistics> getShopListByDate(Map<String, Object> map) {
		return mapper.getShopListByDate(map);
	}

	@Override
	public BigDecimal getShopToatlFunds(Long id) {
		return mapper.getShopToatlFunds(id);
	}
}
