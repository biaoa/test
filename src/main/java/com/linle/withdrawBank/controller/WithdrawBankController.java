package com.linle.withdrawBank.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.withdrawBank.model.WithdrawBank;
import com.linle.withdrawBank.service.WithdrawBankService;
/**
 * 
 * @author pangd
 * @Description 提现银行卡管理
 * @date 2016年11月22日下午3:21:39
 */

@RequiresPermissions("withdraw_bank_manage")
@RequestMapping("sys/withdrawBank")
@Controller
public class WithdrawBankController extends BaseController {

	@Autowired
	private WithdrawBankService withdrawBankService;

	// 列表
	@RequestMapping("/list")
	public String list(Integer pageNo, Model model, String userName, Integer userType) {
		Page<WithdrawBank> page = new Page<>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			if (userName != null && !"".equals(userName)) {
				userName = new String(userName.getBytes("iso8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			_logger.error("提现银行卡列表报错:" + e);
		}
		params.put("userName", userName);
		params.put("userType", userType);
		page.setParams(params);
		
		withdrawBankService.getAllData(page);
		model.addAttribute("userName", userName);
		model.addAttribute("userType", userType);
		model.addAttribute("pagelist", page);
		return "withdrawBank/withdrawbank_list";
	}

	// 进入新增/修改
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String goAdd(Model model, Long id) {
		WithdrawBank bank = new WithdrawBank();
		if (id != null) {
			bank = withdrawBankService.selectByPrimaryKey(id);
		}
		model.addAttribute("bank", bank);
		return "withdrawbank/withdrawbank_add";
	}

	// 新增/修改
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(WithdrawBank bank) {
		try {
			if (bank.getId() != null) {
				withdrawBankService.updateByPrimaryKeySelective(bank);
			} else {
				withdrawBankService.insertSelective(bank);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("添加提现银行卡出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 验证用户名是否存在
	@RequestMapping(value = "/checkUserExist", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse checkUserExist(String userName) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.checkBankUserExsit(userName);
				if (user != null) {
					Map<String, Object> map = new HashMap<>();
					map.put("userType", user.getIdentity().getName());
					map.put("uid", user.getId());
					return new BaseResponse(0, "", map);
				}
				return new BaseResponse(1, "用户不存在或用户类型错误");
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("提现银行卡管理验证用户是否存在出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 删除
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(Long id) {
		try {
			WithdrawBank bank = withdrawBankService.selectByPrimaryKey(id);
			if (bank != null) {
				bank.setDelFlag(1);
				withdrawBankService.updateByPrimaryKeySelective(bank);
				return BaseResponse.OperateSuccess;
			} else {
				return new BaseResponse(1, "该银行卡不存在，请刷新重试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("删除提现银行卡出错:" + e);
			return BaseResponse.ServerException;
		}
	}
}
