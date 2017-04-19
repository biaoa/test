package com.linle.withdrawBank.model;

import com.linle.entity.sys.BaseDomain;
import com.linle.entity.sys.Users;

/**
 * 
 * @author pangd
 * @Description 提现银行卡
 * @date 2016年11月22日下午2:19:53
 */
public class WithdrawBank extends BaseDomain{

    private static final long serialVersionUID = -1004589979837478164L;

	private String accountName;

    private String accountNumber;

    private String accountBank;

    private String remark;

    private Long uId;
    
    private Users user;
    
    private Integer delFlag;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}