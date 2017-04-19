package com.linle.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.pay.service.PayService;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

@Transactional
@Service
public class PayServiceImpl implements PayService{
	public static final Logger _logger = LoggerFactory.getLogger(PayServiceImpl.class);
	@Value("${ping_apiKey}")
	private String apiKey;
	
	@Value("${ping_live_apiKey}")
	private String liveApiKey;
	@Override
	public Charge createCharge(Map<String, Object> payParams) {
		Charge charge  = new Charge();
		Pingpp.apiKey = liveApiKey;
//		Pingpp.privateKeyPath="h:/rsa_private_key.pem";
	    Map<String, String> app = new HashMap<String, String>();
	    app.put("id", "app_OeTmjDuPCSeDKyTO");
	    payParams.put("app",app);
	    payParams.put("currency","cny");
	    payParams.put("client_ip","127.0.0.1");
	    payParams.put("subject","Your Subject");
	    payParams.put("body","Your Body");
	    try {
	    	charge = Charge.create(payParams);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException
				| ChannelException e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return charge;
	}

	@Override
	public Refund refund(Charge charge,String description) {

        Refund refund = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("description", description);
        try {
            refund = charge.getRefunds().create(params);
            System.out.println(refund);
        } catch (AuthenticationException e) {
            e.printStackTrace(); _logger.error("出错了", e);
        } catch (InvalidRequestException e) {
            e.printStackTrace(); _logger.error("出错了", e);
        } catch (APIConnectionException e) {
            e.printStackTrace(); _logger.error("出错了", e);
        } catch (APIException e) {
            e.printStackTrace(); _logger.error("出错了", e);
        } catch (ChannelException e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
        return refund;
	}

	@Override
	public Charge retrieve(String chargeId) {
		Charge charge  = new Charge();
		try {
			charge = Charge.retrieve(chargeId);
			return charge;
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException
				| ChannelException e) {
			_logger.error("查询charge对象", e);
		}
		return null;
	}

}
