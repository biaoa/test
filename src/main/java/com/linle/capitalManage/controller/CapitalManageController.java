package com.linle.capitalManage.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.linle.capitalManage.vo.TransactionflowVo;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.sysOrder.service.SysOrderService;

/**
 * 
 * @author pangd
 * @Description 资金管理
 * @date 2016年10月13日下午3:46:56
 */
@Controller
@RequestMapping("/capitalFlowManage")
public class CapitalManageController extends BaseController {
	
	@Autowired
	private SysOrderService orderService;
	
	//交易流水
	@RequiresPermissions(value="transactionflow_manage")
	@RequestMapping(value="/transactionflow",method=RequestMethod.GET)
	public String list(Model model,Integer pageNo,String beginDate,String endDate,String channel,String userName,String orderNo){
		Page<TransactionflowVo> page = new Page<>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			if(userName!=null){
				userName=new String(userName.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("channel", channel);
		map.put("userName", userName);
		map.put("orderNo", orderNo);
		page.setParams(map);
		
		try {
			orderService.getTransactionflow(page);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("交易流水方法出错:"+e);
		}
		model.addAttribute("userName", userName);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("channel", channel);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pagelist", page);
		return "capitalFlowManage/transactionflow";
	}
}
