package com.linle.sentInfo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.SentInfo;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.event.RongMessageEvent;
import com.linle.io.rong.models.TxtMessage;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.sentInfo.service.SentInfoService;
import com.linle.sysOrder.service.SysOrderService;

@Controller
@RequestMapping("/sentInfo")
public class SentInfoController extends BaseController {
	
	@Autowired
	private RongService rongService;
	
	@Autowired
	private SentInfoService senfInfoService;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private OrderDetailService detailService;
	
	@RequiresPermissions("express_manager")
	@RequestMapping("/list")
	public String list(Integer pageNo,Model model,String orderNo,String uname,String createDate,String beginDate ,String endDate,Integer sentStatus){
		Page<SentInfo> page = new Page<SentInfo>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",getCommunity().getId());
		params.put("orderNo", orderNo);
		params.put("uname", uname);//用户名 或者手机号码
		params.put("createDate", createDate);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("sentStatus", sentStatus);
		page.setParams(params);
		senfInfoService.getAllsentOrder(page);
		
		model.addAttribute("pagelist", page);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("uname", uname);
		model.addAttribute("createDate", createDate);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("sentStatus", sentStatus);
		return "sentOrder/sent_order_list";
	}
	
	@RequiresPermissions("express_manager")
	@RequestMapping("/operate")
	@ResponseBody
	public BaseResponse operate(Long sid,Integer status){
		Subject subject = SecurityUtils.getSubject();
		try {
			if (subject.isAuthenticated()) {
				
				SentInfo sent = senfInfoService.selectByPrimaryKey(sid);
				sent.setStatus(status);
				senfInfoService.updateStatus(sent);
				//发送消息
				SysOrder order = orderService.selectByOrderTypeAndBusinessType("sent",sid);
				
				if (status==1) {
					order.setOrderStatus(2); //如果选择拒绝服务。那么关闭订单
					orderService.closeOrder(order);
				}else if(status==2){
					order.setOrderStatus(6);
					orderService.accepted(order);//如果选择受理，那么将订单状态改为已受理
				}
				Users userInfo = (Users) subject.getSession().getAttribute("cUser");
				List<String> toUserIds= new ArrayList<String>();
				toUserIds.add(sent.getUserId().toString());
				String msg = "您的订单号为"+order.getOrderNo()+"寄件请求物业已处理。结果是:"+(status==1?"不处理":"处理");
//				rongService.sendMessage(userInfo.getId(), toUserIds, new TxtMessage(message));
				//融云发送消息
				TxtMessage message=new TxtMessage(msg);//文本信息
				applicationContext.publishEvent(new RongMessageEvent(userInfo.getId(), toUserIds, message));
				
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
	}
	@RequiresPermissions("express_manager")
	@RequestMapping("/sendpay")
	@ResponseBody
	public BaseResponse sendpay(Long sid,String price,String remark){
		try {
			Subject subject = SecurityUtils.getSubject();
			SentInfo sent = senfInfoService.selectByPrimaryKey(sid);
			sent.setStatus(3);
			senfInfoService.updateStatus(sent);
			//发送消息
			SysOrder order = orderService.selectByOrderTypeAndBusinessType("sent",sid);
			order.setTotalMoney(new BigDecimal(price));
			order.setOrderStatus(1);//状态改为待支付
			
			//去把订单明细里面的产品价格改了
			List<OrderDetail> detailList = detailService.getDetailList(order.getId());
			if (!detailList.isEmpty()) {
				OrderDetail detail = detailList.get(0);// 寄件的详情实际上只有一条明细
				detail.setProductPrice(order.getTotalMoney());
				detailService.updateByPrimaryKeySelective(detail);
			}
			if (subject.isAuthenticated()) {
				//修改订单价格,状态
				boolean isok =  orderService.updateOrderPrice(order);
				Users userInfo = (Users) subject.getSession().getAttribute("cUser");
				List<String> toUserIds= new ArrayList<String>();
				toUserIds.add(sent.getUserId().toString());
				String msg = "您的订单号为"+order.getOrderNo()+"寄件请求物业已处理"+"。请及时支付快递费用:"+price;
				if(remark!=null && !"".equals(remark)){
					msg+="。物业留言："+remark;
				}
//				rongService.sendMessage(userInfo.getId(), toUserIds, new TxtMessage(message));
				
				//融云发送消息
				TxtMessage message=new TxtMessage(msg);//文本信息
				applicationContext.publishEvent(new RongMessageEvent(userInfo.getId(), toUserIds, message));
				
				_logger.info("消息发送成功");
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
