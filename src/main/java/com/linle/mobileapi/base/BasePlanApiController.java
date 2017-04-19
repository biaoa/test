package com.linle.mobileapi.base;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.linle.common.base.BaseController;
import com.linle.entity.sys.LoginToken;
import com.linle.entity.sys.Users;
import com.linle.shiro.UserLoginToken;
import com.linle.user.service.LoginTokenService;
import com.linle.user.service.UserInfoService;

public class BasePlanApiController extends BaseController {

//    private final static Logger logger = LoggerFactory.getLogger(ApiParamValidatorAspect.class);

    @ModelAttribute
    public void putBaeInfo(Model model) {
        Users user = getSessionUser();
        if (user != null) {
            model.addAttribute("id", user.getId());
            model.addAttribute("name", user.getUserName());
            model.addAttribute("time", user.getCreateDate());
            _logger.debug("Users:{}.Name:{},Time:{}", user, user.getUserName(), user.getCreateDate());
        } else {
            _logger.debug("Session不存在User!");
        }
    }

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    UserInfoService userInfoService;

    public Users getSessionUser() {
        return (Users) SecurityUtils.getSubject().getSession().getAttribute("cUser");
    }


    public void validatorSid(String sid) throws Throwable {
        _logger.debug("Sid:{}",sid);
        if(StringUtils.isBlank(sid)){
            throw new RuntimeException("请先登陆!");
        }
        Subject subject = SecurityUtils.getSubject();
        if (StringUtils.isNotEmpty(sid)) {
            LoginToken dbToken = loginTokenService.getByToken(sid);
            if (null != dbToken) {
                UserLoginToken token = new UserLoginToken();
                token.setUsername(dbToken.getUserName());
                token.setPassword(dbToken.getPassword().toCharArray());
                token.setLoginMode(UserLoginToken.LoginMode.token);
                try {
                    subject.login(token);
                    Session session = subject.getSession();
                    Users userInfo = userInfoService.findUserInfoByUserName(dbToken.getUserName());
                    session.setAttribute("cUser", userInfo);
                } catch (AuthenticationException e) {
                        _logger.debug("登录已过期，请重新登录");
                }
            }
        }
    }
}
