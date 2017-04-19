package com.linle.system.service;

import java.util.List;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月8日
 **/

public interface SendMailService {
	
	/**
	 * 发送邮件
	 * @param to
	 * @param from
	 * @param subject
	 * @param text
	 * @throws Exception
	 */
	public void sendMail(List<String> to,String from,String subject,String text)throws Exception;
	
}
