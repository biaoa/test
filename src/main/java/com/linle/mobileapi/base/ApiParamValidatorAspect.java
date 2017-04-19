package com.linle.mobileapi.base;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.linle.entity.sys.LoginToken;
import com.linle.entity.sys.Users;
import com.linle.shiro.UserLoginToken;
import com.linle.shiro.UserLoginToken.LoginMode;
import com.linle.user.service.LoginTokenService;
import com.linle.user.service.UserInfoService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月28日
 **/
@Aspect
@Repository("apiParamValidatorAspect")
public class ApiParamValidatorAspect {
    private final static Logger logger = LoggerFactory.getLogger(ApiParamValidatorAspect.class);
    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    UserInfoService userInfoService;
    
    public final static String APP_KEY = "001";
	public final static String APP_SECRET = "abc";


    @Around(value = "execution(public com.linle.mobileapi.base.BaseResponse  com.linle.mobileapi..controller.*Controller.*(..)) && args(apiRequest,errors,servletRequest,..)")
    public Object validatorSign(ProceedingJoinPoint pjp, BaseRequest apiRequest, Errors errors, HttpServletRequest servletRequest) throws Throwable {
        String sid = apiRequest.getSid();
        if(StringUtils.isEmpty(sid) ){
        	if (apiRequest instanceof NeedLoginRequest && servletRequest.getCookies() != null) {
                for (Cookie cookie : servletRequest.getCookies()) {
                    logger.debug("获取CookieName:{},Cookie值:{}", cookie.getName(), cookie.getValue());
                    if (cookie.getName().equals("sid")) {
                        sid = cookie.getValue();
                        logger.debug("cookie获取的Sid:{}", sid);
                    }
                }
            }
        }
        ErrorResponse errorResponse = new ErrorResponse();
        if (apiRequest instanceof NeedLoginRequest && StringUtils.isEmpty(sid)) {
            errorResponse.setCode(9);
            errorResponse.setMsg("请先登录");
            return errorResponse;
        }
        errorResponse.setCode(1);
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                errorResponse.setMsg(error.getDefaultMessage());
            }
            errorResponse.setCode(1);
            return errorResponse;
        }
//        Map<String, String[]> parame = servletRequest.getParameterMap();
    	// 客户端身份验证及参数签名验证
//		String[] appKey = parame.get("appKey");
	/*	if (!APP_KEY.equals(appKey[0])) {
			errorResponse.setCode(22);
			errorResponse.setMsg("您的appKey不存在");
			return errorResponse;
		}*/
//		String[] clientSign = parame.get("sign");
//		String serverSign = sign(parame, APP_SECRET);
//		logger.debug("客户端签名：" + clientSign[0]);
//		logger.debug("服务端签名：" + serverSign);
//		if (!clientSign[0].equals(serverSign)) {
//			errorResponse.setCode(19);
//			errorResponse.setMsg("签名验证失败");
//			return errorResponse;
//		}
        Subject subject = SecurityUtils.getSubject();
        if (StringUtils.isNotEmpty(sid)) {
            LoginToken dbToken = loginTokenService.getByToken(sid);
            if (null != dbToken) {
                UserLoginToken token = new UserLoginToken();
                token.setUsername(dbToken.getUserName());
                token.setPassword(dbToken.getPassword().toCharArray());
                token.setLoginMode(LoginMode.token);
                try {
                    subject.login(token);
                    Session session = subject.getSession();
                    Users userInfo = userInfoService.findUserInfoByUserName(dbToken.getUserName());
                    session.setAttribute("cUser", userInfo);
                } catch (AuthenticationException e) {
                    if (apiRequest instanceof NeedLoginRequest) {
                        errorResponse.setCode(2);
                        errorResponse.setMsg("登录已过期，请重新登录");
                        return errorResponse;
                    }
                }
            }
            if (null == dbToken && apiRequest instanceof NeedLoginRequest) {
                errorResponse.setCode(2);
                errorResponse.setMsg("登录已过期，请重新登录");
                return errorResponse;
            }
        }
        Object resp = null;
        try {
            resp = pjp.proceed();
        } catch (Exception e) {
            logger.error("调用" + pjp.proceed() + "出异常了哦：", e);
            errorResponse.setCode(10);
            errorResponse.setMsg("服务异常了！");
            return errorResponse;
        } finally {
            subject.logout();
            ThreadContext.unbindSubject();
        }
        return resp;
    }
    
    
    private String sign(Map<String, String[]> parameters, String appSecret)
			throws IOException {
		Map<String, String[]> map = new TreeMap<String, String[]>(parameters);
		map.remove("sign");
		StringBuilder para = new StringBuilder();
		para.append(appSecret);
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			para.append(entry.getKey());
			String[] values = entry.getValue();
			for (int i = 0; i < values.length; i++) {
				para.append(values[i]);
			}

		}
		para.append(appSecret);
		logger.debug("加密前的明文：" + para.toString());
		byte[] sha1Digest = getSHA1Digest(para.toString());
		return byte2hex(sha1Digest);
	}

	private byte[] getSHA1Digest(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			bytes = md.digest(data.getBytes("utf-8"));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	}

	private String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}


}
