package com.linle.entity.sys;

import java.util.Date;

import com.linle.common.util.JsonUtil;
import com.pingplusplus.model.Refund;

//退款记录
public class RefundRecord extends BaseDomain{
	
	private static final long serialVersionUID = 4784888933381733242L;

	private String orderNo;

    private String refundStatus;

    private Integer amount;

    private String chargeId;

    private String refundId;

    private Date successDate;

    private String refundJson;
    
    private String channel; //支付方式  wx  alipay  upacp
    
    private String reason; //退款原因
    
    private Refund refund;
    
    private String url;

    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }


    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public String getRefundJson() {
        return refundJson;
    }

    public void setRefundJson(String refundJson) {
        this.refundJson = refundJson;
    }

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Refund getRefund() {
		
		return JsonUtil.fromJson(refundJson, Refund.class);
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}

	public String getUrl() {
		if(getRefund().getFailureMsg()!=null && getRefund().getFailureMsg() !=""){
			return getRefund().getFailureMsg().split("\\: ")[1];
		}
		return "";
	}

	public void setUrl(String url) {
		this.url = url;
	}
}