package com.linle.entity.sys;

import java.math.BigDecimal;

import com.linle.withdrawBank.model.WithdrawBank;

public class Withdraw extends BaseDomain {

	private static final long serialVersionUID = 1450816351121505773L;

	private Long uId;

	private BigDecimal amount;

	private Integer statuss;

	private Users user;

	private BigDecimal applyAmount; // 申请提现金额

	private BigDecimal poundageAmount; // 手续费

	private BigDecimal cutAmount;// 系统抽成金额

	private String withdrawType; // 申请类型

	private WithdrawBank withdrawbank; // 提现银行卡

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getStatuss() {
		return statuss;
	}

	public void setStatuss(Integer statuss) {
		this.statuss = statuss;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getPoundageAmount() {
		return poundageAmount;
	}

	public void setPoundageAmount(BigDecimal poundageAmount) {
		this.poundageAmount = poundageAmount;
	}

	public BigDecimal getCutAmount() {
		return cutAmount;
	}

	public void setCutAmount(BigDecimal cutAmount) {
		this.cutAmount = cutAmount;
	}

	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

	public WithdrawBank getWithdrawbank() {
		return withdrawbank;
	}

	public void setWithdrawbank(WithdrawBank withdrawbank) {
		this.withdrawbank = withdrawbank;
	}
}