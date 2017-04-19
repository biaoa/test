package com.linle.funds;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.Shop;
import com.linle.funds.vo.Incomedetail;
import com.linle.shop.service.ShopService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.withdraw.service.WithdrawService;
import com.linle.withdrawBank.model.WithdrawBank;
import com.linle.withdrawBank.service.WithdrawBankService;

@RequestMapping("/sys/funds")
@Controller
public class FundsController extends BaseController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private WithdrawService withdrawService;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private WithdrawBankService withdrawBankService;
	
	@RequiresPermissions("shop_earnings")
	@RequestMapping("/shopfund")
	public String shopfund(Integer pageNo,Model model,String orderNo,String beginDate,String endDate){
		Shop shop = getShopByUserID();
		if(shop==null){
			return "error/nodata";
		}
		//商家总收益
		BigDecimal shopTotalIncome = shopService.getShopTotalIncome(shop.getId());
		//商家待结算金额
		BigDecimal shopWaitIncome = shopService.getShopWaitIncome(shop.getId());
		//商家可提现余额(总收益-已提现的=可提现余额)
//		BigDecimal b1 = shopService.getShopBalance(shop.getId());
		BigDecimal withdrawed = withdrawService.sumWithdrawMoney(shop.getUser().getId(),"shop");
		BigDecimal shopBalance = shopTotalIncome.subtract(withdrawed);
		
		BigDecimal totalWithdrawed = shopService.getShopToatlFunds(shop.getUser().getId());
		//抽成只是超过优惠部分抽成
		if(totalWithdrawed.compareTo(shop.getPreferentialCutAmount())>=0){
			shopBalance = shopBalance.multiply(new BigDecimal((100-shop.getCut()))).divide(new BigDecimal(100),2,BigDecimal.ROUND_FLOOR);//可提现金额
		}
		//收益明细 
		Map<String, Object> params = new HashMap<>();
		if (StringUtil.isNotNull(orderNo)) {
			params.put("orderNo", orderNo);
		}
		if(StringUtil.isNotNull(beginDate)){
			params.put("beginDate", beginDate);
		}
		if (StringUtil.isNotNull(endDate)) {
			params.put("endDate", endDate);
		}
		params.put("shopId", shop.getId());
		Page<Incomedetail> page = new Page<>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		page.setParams(params);
		orderService.getShopIncomedetailList(page);
		//提现银行卡
		List<WithdrawBank> bankList = withdrawBankService.getBanksByUid(shop.getUser().getId());
		model.addAttribute("bankList", bankList);
		BigDecimal shopTotalFunds = shopService.getShopToatlFunds(shop.getUser().getId());//已提现金额
		//优惠金额
		model.addAttribute("surplusPreferential",shop.getPreferentialCutAmount().subtract(shopTotalFunds));//优惠金额
		//抽成
		model.addAttribute("cut", shop.getCut());
		//提现手续费
		model.addAttribute("withdrawalFee", shop.getWithdrawalFee());
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("shopTotalIncome", shopTotalIncome);
		model.addAttribute("shopWaitIncome", shopWaitIncome);
		model.addAttribute("shopBalance", shopBalance);
		model.addAttribute("pagelist", page);
		return "fundsManage/shop_funds_manage";
	}
	
	@RequiresPermissions("community_earnings")
	@RequestMapping("/communityfund")
	public String communityfund(Integer pageNo,Model model,String orderType,String beginDate,String endDate){
		Community community = getCommunity();
		if(community==null){
			return "error/nodata";
		}
		//小区总收益 = 缴费+车位+商家的抽成的百分比
		BigDecimal communityTotalIncome = communityService.getCommunityIncome(community.getId(),orderType);
		//小区可提现余额(总收益-已提现的=可提现余额)
		BigDecimal withdrawed = withdrawService.sumWithdrawMoney(community.getUser().getId(),orderType);
		BigDecimal communityBalance = communityTotalIncome.subtract(withdrawed);
		Map<String, Object> params = new HashMap<>();
		if (StringUtil.isNotNull(orderType)) {
			params.put("orderType", orderType);
		}
		if(StringUtil.isNotNull(beginDate)){
			params.put("beginDate", beginDate);
		}
		if (StringUtil.isNotNull(endDate)) {
			params.put("endDate", endDate);
		}
		params.put("communityId", community.getId());
		Page<Incomedetail> page = new Page<>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		page.setParams(params);
		orderService.getCommunitydetailList(page);
		List<WithdrawBank> bankList = withdrawBankService.getBanksByUid(community.getUser().getId());
		model.addAttribute("bankList", bankList);
		model.addAttribute("orderType", orderType);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pagelist", page);
		model.addAttribute("communityBalance", communityBalance);
		model.addAttribute("communityTotalIncome", communityTotalIncome);
		model.addAttribute("withdrawalFee", community.getWithdrawalFee());
		return "fundsManage/community_funds_manage";
	}
}
