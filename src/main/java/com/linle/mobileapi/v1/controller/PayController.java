package com.linle.mobileapi.v1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.IPUtil;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.PayRequest;
import com.linle.mobileapi.v1.response.PayResponse;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.preferentialActivity.service.PreferentialActivityService;
import com.linle.sysOrder.service.SysOrderService;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.EventData;
import com.pingplusplus.model.Webhooks;

@Controller
@RequestMapping("/api/1")
public class PayController extends BaseController {

	@Autowired
	private SysOrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Value("${ping_appid}")
	private String appid;

	@Value("${ping_apiKey}")
	private String apiKey;
	
	@Value("${ping_live_apiKey}")
	private String liveApiKey;
	
	@Autowired
	private PreferentialActivityService preferentialActivityService;
	/**
	 * 支付
	 * 
	 * @throws ChannelException
	 * @throws APIException
	 * @throws APIConnectionException
	 * @throws InvalidRequestException
	 * @throws AuthenticationException
	 * @throws IOException
	 *             method = RequestMethod.GET
	 */
	@ResponseBody
	@RequestMapping(value = "/pay",method=RequestMethod.POST)
	public BaseResponse Pay(PayRequest req,Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException, ChannelException, IOException {
		try {
			Subject subject = SecurityUtils.getSubject();
			PayResponse res = new PayResponse();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> chargeParams = new HashMap<String, Object>();
				if (req.getChannel() != null && req.getOrderNo() != null && req.getChannel().isEmpty()
						&& req.getOrderNo().isEmpty()) {
					res.setCode(1);
					res.setMsg("支付类型或订单不能空");
					return res;
				}
				if (!verifyChannel(req.getChannel())) {
					res.setCode(1);
					res.setMsg("支付方式有误");
					return res;
				}
				SysOrder order = orderService.getOrderByOrderNo(req.getOrderNo(),user.getId());
				if (order == null) {
					res.setCode(1);
					res.setMsg("订单不存在");
					return res;
				}
				if(order.getTotalMoney().equals(BigDecimal.ZERO)){
					res.setCode(1);
					res.setMsg("订单金额为0");
					return res;
				}
				List<OrderDetail> details = orderDetailService.getDetailList(order.getId());
				//判断是否有优惠
				if (preferentialActivityService.hasPreferential(order)) {
					//参加活动
					preferentialActivityService.joinPreferentialActivity(order);
				}
				Pingpp.apiKey = liveApiKey;
//				Pingpp.apiKey = apiKey;
				Pingpp.privateKeyPath = servletRequest.getSession().getServletContext().getRealPath("/")
						+ "WEB-INF\\classes\\rsa_private_key.pem";
				
				Map<String, String> app = new HashMap<String, String>();
				Map<String, String> initialMetadata = new HashMap<>();
				//自定义参数
				initialMetadata.put("uid", user.getId().toString());
				initialMetadata.put("order", order.getOrderNo());
				app.put("id", appid);
				//ping++支付参数
				chargeParams.put("app", app);
				if(req.getChannel().equals("wx")){
					chargeParams.put("order_no", order.getOrderNo()+"w"+getNow());
				}else{
					chargeParams.put("order_no", order.getOrderNo());
				}
				chargeParams.put("amount", order.getTotalMoney().multiply(new BigDecimal("100")));
				chargeParams.put("channel", req.getChannel());
				chargeParams.put("currency", "cny");
				chargeParams.put("client_ip", IPUtil.getIpAddress(servletRequest));
				if(getWordCountRegex(order.getRemark())>32){
					String str = order.getRemark().substring(order.getRemark().indexOf(",")+1, order.getRemark().length());
					chargeParams.put("subject", str); // 商品名称
				}else{
					chargeParams.put("subject", order.getRemark()); // 商品名称
				}
				chargeParams.put("body", productDescription(details)); // 商品描述
				chargeParams.put("metadata", initialMetadata);
				Charge charge = Charge.create(chargeParams);
				// 更新订单charge_id
				Map<String, Object> params = new HashMap<>();
				params.put("orderNo", req.getOrderNo());
				params.put("chargeId", charge.getId());
				params.put("uid",user.getId());
				params.put("channel", req.getChannel());
				orderService.updateChargeId(params);
				res.setCode(0);
				res.setMsg("获取正常");
				res.setData(charge.toString());
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	@RequestMapping("/doWebhooks")
	public void doWebhooks(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 request.setCharacterEncoding("UTF8");
		  _logger.error("doWebhooks开始。。。。。。。。。。。。。。。。。。。"+DateUtil.getCurrentDateTime());
		//获取头部所有信息
	        Enumeration headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = request.getHeader(key);
	            System.out.println(key+" "+value);
	        }
	        // 获得 http body 内容
	        BufferedReader reader = request.getReader();
	        StringBuffer buffer = new StringBuffer();
	        String string;
	        while ((string = reader.readLine()) != null) {
	            buffer.append(string);
	        }
	        reader.close();
	        // 解析异步通知数据
	        Event event = Webhooks.eventParse(buffer.toString());
	        _logger.error("获得到event信息是"+m.writeValueAsString(event));
//	        EventData data=event.getData();
//	        _logger.error("获得到data信息是"+m.writeValueAsString(data));
	        Object obj = Webhooks.getObject(event.toString());
//	        _logger.error("获得到Object信息是"+m.writeValueAsString(obj));
	        if ("charge.succeeded".equals(event.getType())) {
	        	Charge charge = (Charge) obj;
	        	String userid=charge.getMetadata().get("uid");
	        	String orderNo=charge.getMetadata().get("order");
//	        	SysOrder sysOrder=orderService.getOrderByOrderNo(orderNo, Long.valueOf(userid));
        		BaseResponse res=orderService.orderSuccess(Long.valueOf(userid), orderNo);
	        	if(res.getCode()==0){
	        		 response.setStatus(200);
	        	}else{
	        		response.setStatus(500);
	        	}
//	        	System.out.println("webhooks 发送了 Charge");
//	            System.out.println("付款状态：" + charge.getPaid() + " 订单号：" + charge.getOrderNo());
//	            System.out.println("自定义参数Metadata（用户信息）"+m.writeValueAsString(charge.getMetadata()));
	            
	            _logger.error("webhooks 发送了 Charge"+m.writeValueAsString(charge));
	            _logger.error("付款状态：" + charge.getPaid() + " 订单号：" + charge.getOrderNo());
	            _logger.error("自定义参数Metadata（用户信息）"+m.writeValueAsString(charge.getMetadata()));
	            _logger.error("doWebhooks结束。。。。。。。。。。。。。。。。。。。"+DateUtil.getCurrentDateTime());
	        } else if ("refund.succeeded".equals(event.getType())) {
	            response.setStatus(200);
	            System.out.println("webhooks 发送了 Refund");
	        } else {
	            response.setStatus(500);
	        }
	        
	       
	}
	
	
	
	
	
	
	
	
	
	
	private boolean verifyChannel(String channel) {
		if (channel != null) {
			if (channel.equals("wx") || channel.equals("alipay") || channel.equals("upacp")) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException, ChannelException {
//		Map<String, Object> chargeParams = new HashMap<String, Object>();
//		Pingpp.apiKey = "sk_test_ibbTe5jLGCi5rzfH4OqPW9KC";
//		Pingpp.privateKeyPath = "h:/rsa_private_key.pem";
//		chargeParams.put("order_no", "123456789");
//		chargeParams.put("amount", 100);
//		Map<String, String> app = new HashMap<String, String>();
//		app.put("id", "app_1Gqj58ynP0mHeX1q");
//		chargeParams.put("app", app);
//		chargeParams.put("channel", "alipay");
//		chargeParams.put("currency", "cny");
//		chargeParams.put("client_ip", "127.0.0.1");
//		chargeParams.put("subject", "Your Subject");
//		chargeParams.put("body", "Your Body");
//		System.out.println(Charge.create(chargeParams).toString());
		
	}

	public static String productDescription(List<OrderDetail> details) {
		String str = "";
		if (details.isEmpty()) {
			return str;
		}
		for (OrderDetail orderDetail : details) {
			str += orderDetail.getProductName() + "," + orderDetail.getRemark();
		}
		return str;
	}
	
	public static String getNow(){
		Date d=new Date();
		SimpleDateFormat sd=new SimpleDateFormat("hhmmss");
		return sd.format(d);
	}
	
	  public static  int getWordCountRegex(String s){
	        s = s.replaceAll("[^\\x00-\\xff]", "**");  
	        int length = s.length();  
	        return length;  
	    }
	
}
