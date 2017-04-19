package com.linle.common.util;

public class BankVO {
	private String bank;
	
	private String validated;
	
	private String cardType;
	
	private String stat;
	
	private Object messages;
	
	private String key;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValidated() {
		return validated;
	}

	public void setValidated(String validated) {
		this.validated = validated;
	}

	public Object getMessages() {
		return messages;
	}

	public void setMessages(Object messages) {
		this.messages = messages;
	}
}
