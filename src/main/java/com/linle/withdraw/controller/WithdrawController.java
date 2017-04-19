package com.linle.withdraw.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.BigDecimalUtil;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.Users;
import com.linle.entity.sys.Withdraw;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.shop.service.ShopService;
import com.linle.withdraw.service.WithdrawService;
import com.linle.withdrawBank.model.WithdrawBank;
import com.linle.withdrawBank.service.WithdrawBankService;

/**
 * 
 * @author pangd
 * @Description 系统提现相关
 */
@Controller
@RequestMapping("/sys/withdraw")
public class WithdrawController extends BaseController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private WithdrawService withdrawService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private WithdrawBankService withdrawBankService;
	
	@RequestMapping("/list")
	public String list(Model model,String userName,Integer status,String beginDate,String endDate){
		Page<Withdraw> page = new Page<>();
		Map<String, Object> map = new HashMap<>();
		Users user = getCurrentUser();
		try {
			if(userName!=null){
				userName=new String(userName.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (status==null) {
			status = -1;
		}
		map.put("userName", userName);
		map.put("status", status);
		if(StringUtil.isNotNull(beginDate)){
			map.put("beginDate", beginDate);
		}
		if (StringUtil.isNotNull(endDate)) {
			map.put("endDate", endDate);
		}
		if (!(user.getIdentity().equals(UserType.SYS) || user.getIdentity().equals(UserType.CWB))) {
			map.put("uid", user.getId());
		}
		page.setParams(map);
		withdrawService.findAllWithdraw(page);
		model.addAttribute("pagelist", page);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		return "withdraw/withdraw_list";
	}
	

	@RequestMapping(value = "/income", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse withdraw(BigDecimal incomeMoney,BigDecimal applyAmount,String orderType,Long bank) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				WithdrawBank withdrawBank = withdrawBankService.selectByPrimaryKey(bank);
				if(withdrawBank==null){
					return new BaseResponse(1, "提现银行卡信息不存在");
				}
				BigDecimal poundageFee = new BigDecimal("0.012");
				Users user= getCurrentUser();
				if (user.getIdentity().equals(UserType.SJ)) {
					Shop shop = getShopByUserID();
					poundageFee = shop.getWithdrawalFee();
					//商家总收益
					BigDecimal shopTotalIncome = shopService.getShopTotalIncome(shop.getId());
					//商家可提现余额(总收益-已提现的=可提现余额)
					BigDecimal withdrawed = withdrawService.sumWithdrawMoney(shop.getUser().getId(),"shop"); //商家的提现类型 先固定
					BigDecimal shopBalance = shopTotalIncome.subtract(withdrawed);
					//剩余优惠金额
					BigDecimal surplusPreferential = surplusPreferential(shop.getPreferentialCutAmount(), withdrawed);
					//抽成金额
					BigDecimal cutAmount = new BigDecimal("0");
					//如果提现金额大于剩余优惠金额
					if(applyAmount.compareTo(surplusPreferential)==1){
						//提现金额-剩余优惠金额=抽成金额
						BigDecimal shopCut = new BigDecimal(shop.getCut());
						cutAmount = BigDecimalUtil.divide(BigDecimalUtil.sub(applyAmount, surplusPreferential).multiply(shopCut), new BigDecimal("100"));
					}
					BigDecimal poundageFeeAmount = BigDecimalUtil.mul(applyAmount, poundageFee);//手续费
					BigDecimal maxIncome =shopBalance.subtract(poundageFeeAmount).subtract(cutAmount);
					shopBalance = shopBalance.multiply(new BigDecimal((100-shop.getCut()))).divide(new BigDecimal(100),2,BigDecimal.ROUND_FLOOR);
					if (incomeMoney.compareTo(maxIncome)!=1) {
						Withdraw withdraw = new Withdraw();
						withdraw.setuId(user.getId());
						withdraw.setAmount(incomeMoney);//实际到账金额
						withdraw.setStatuss(0);
						withdraw.setCreateDate(new Date());
						withdraw.setApplyAmount(applyAmount);//申请金额
						withdraw.setPoundageAmount(BigDecimalUtil.mul(applyAmount, poundageFee));//手续费
						withdraw.setCutAmount(cutAmount);//抽成金额
						withdraw.setWithdrawType("shop");
						withdraw.setWithdrawbank(withdrawBank);
						withdrawService.insertSelective(withdraw);
						return new BaseResponse(0, "提现申请已提交");
					}else{
						return new BaseResponse(1, "提现金额大于可提现金额");
					}
				}else if(user.getIdentity().equals(UserType.XQ)){
					if(withdrawBank==null){
						return new BaseResponse(1, "银行卡不存在");
					}
					Community community = getCommunity();
					poundageFee = community.getWithdrawalFee();
					BigDecimal communityTotalIncome = communityService.getCommunityIncome(community.getId(),orderType);
//					communityTotalIncome = BigDecimalUtil.mul(communityTotalIncome, actual);
					// communityTotalIncome.multiply(actual);
					BigDecimal withdrawed = withdrawService.sumWithdrawMoney(community.getUser().getId(),orderType);
					//小区可提现余额
					BigDecimal communityBalance = communityTotalIncome.subtract(withdrawed);
					if(incomeMoney.compareTo(communityBalance)!=1){
						Withdraw withdraw = new Withdraw();
						withdraw.setuId(user.getId());
						withdraw.setAmount(incomeMoney);//实际到账金额
						withdraw.setStatuss(0);
						withdraw.setCreateDate(new Date());
						withdraw.setApplyAmount(applyAmount);//申请金额
						withdraw.setPoundageAmount(BigDecimalUtil.mul(applyAmount, poundageFee));//手续费
						withdraw.setWithdrawType(orderType);
						withdraw.setWithdrawbank(withdrawBank);
						withdrawService.insertSelective(withdraw);
						return new BaseResponse(0, "提现申请已提交");
					}else{
						return new BaseResponse(1, "提现金额大于可提现金额");
					}
				}
				
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("income_confirm")
	@RequestMapping(value="confirm/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse confirm(@PathVariable Long id){
		try {
			Withdraw withdraw = withdrawService.selectByPrimaryKey(id);
			if(withdraw==null || withdraw.getStatuss()!=0){
				return new BaseResponse(1, "不能处理该提现申请");
			}
			withdraw.setStatuss(1);
			withdraw.setUpdateDate(new Date());
			withdrawService.updateByPrimaryKeySelective(withdraw);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	public  BigDecimal surplusPreferential(BigDecimal totalPreferential,BigDecimal usePreferential){
		if(usePreferential.compareTo(totalPreferential)<0){
			return totalPreferential.subtract(usePreferential);
		}
		return new BigDecimal("0");
	}
}
