package com.linle.event;

import org.springframework.context.ApplicationEvent;

import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月10日
 **/
public class PushMessageEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	private PushBean pushBean;
	private String tagName;
	private String alias;
	private String[] aliasArray;
	private PushFrom from;
	

	public PushMessageEvent(PushBean pushBean,String tagName,String alias,PushFrom from) {
		super(pushBean);
		this.pushBean = pushBean;
		this.tagName = tagName;
		this.alias = alias;
		this.from = from;
	}
	
	public PushMessageEvent(PushBean pushBean,String tagName,String[] aliasArray,PushFrom from) {
		super(pushBean);
		this.pushBean = pushBean;
		this.tagName = tagName;
		this.aliasArray = aliasArray;
		this.from = from;
	}
	public String[] getAliasArray() {
		return aliasArray;
	}


	public void setAliasArray(String[] aliasArray) {
		this.aliasArray = aliasArray;
	}
	public PushBean getPushBean() {
		return pushBean;
	}

	public void setPushBean(PushBean pushBean) {
		this.pushBean = pushBean;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public PushFrom getFrom() {
		return from;
	}

	public void setFrom(PushFrom from) {
		this.from = from;
	}
	
}
