package com.linle.common.util;

import org.apache.shiro.util.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月27日
 **/


@Aspect
@Repository("formVerificationAspect")
public class FormVerificationAspect {
	final static Logger logger = LoggerFactory
			.getLogger(FormVerificationAspect.class);
	
	@Around("execution(public com.linle.common.util.ResponseMsg com.linle..controller.*Controller.*(..)) && args(obj,errors,..)")
	public Object validatorSign(ProceedingJoinPoint pjp, Object obj,
			BindingResult errors) throws Throwable {
		ResponseMsg errorMsg = new ResponseMsg(0, "", null);
		if (errors.hasErrors()) {
			for (ObjectError error : errors.getAllErrors()) {
				errorMsg.setMsg(error.getDefaultMessage() + ";");
			}
			errorMsg.setCode(1);
			return errorMsg;
		}
		Object resp = null;
		try {
			resp = pjp.proceed();
		} catch (Exception e) {
			errorMsg.setCode(10);
			errorMsg.setMsg("服务异常了！");
			return errorMsg;
		} finally {
			ThreadContext.unbindSubject();// 从当前线程解绑
		}
		return resp;
	}

}
