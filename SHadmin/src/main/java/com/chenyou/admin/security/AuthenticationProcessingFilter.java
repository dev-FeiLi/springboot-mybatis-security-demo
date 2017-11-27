package com.chenyou.admin.security;


import com.chenyou.admin.Utils.ApplicationConstant;
import com.chenyou.admin.Utils.ToolUtil;
import com.chenyou.admin.domain.SysManage;
import com.chenyou.admin.domain.SysOperateLog;
import com.chenyou.admin.domain.SysRole;
import com.chenyou.admin.service.SysManageService;
import com.chenyou.admin.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/11.
 */
@Component
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    final static Logger LOG = LoggerFactory.getLogger(AuthenticationProcessingFilter.class);
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_PASSCODE_KEY = "passcode";
    /**
     * @deprecated If you want to retain the username, cache it in a customized {@code AuthenticationFailureHandler}
     */
    @Deprecated
    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String passcodeParameter = SPRING_SECURITY_FORM_PASSCODE_KEY;
    private boolean postOnly = true;

    @Autowired
    private SysManageService sysManageService;
    @Autowired
    private SysRoleService sysRoleService;


    // ~ Constructors
    // ===================================================================================================

    public AuthenticationProcessingFilter() {
        super(ApplicationConstant.LOGIN_CHECK_PATH);
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LOG.debug("login authentication filter excute");
        Long start = System.currentTimeMillis(), end = 0L;
        HttpSession session = request.getSession(false);

        if (postOnly && !"POST".equals(request.getMethod())) {
            LOG.error("login authentication filter method error: not supported " + request.getMethod());
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String passcode = obtainPasscode(request);
        String ip = ToolUtil.obtainRequestIp(request);

        try {
            Assert.hasText(username, "username must not null");
            Assert.hasText(password, "password must not null");
            Assert.hasText(passcode, "captcha must not null");
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        String sessionCode = (String) session.getAttribute(ApplicationConstant.SESSION_VALIDATE_CODE);
        LOG.debug("session passcode: " + sessionCode + ", param passcode: " + passcode);
        if (!sessionCode.equalsIgnoreCase(passcode)) {
            session.setAttribute(ApplicationConstant.SESSION_ERROR_MSG, "captcha does not match");
            throw new AuthenticationServiceException("captcha does not match");
        }

        SysOperateLog operateLog = new SysOperateLog();
        operateLog.setOptUrl(ApplicationConstant.LOGIN_PAGE_PATH);
        operateLog.setOptAddTime(new Date());
        operateLog.setOptIp(ip);
        operateLog.setOptStart(start);

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        Authentication authentication = null;
        try {
            authentication = this.getAuthenticationManager().authenticate(authRequest);
        } catch (AuthenticationException e) {
            LOG.error("用户登录失败 authentication error: ", e);
            end = System.currentTimeMillis();
            throw e;
        }

        try {
            // 通过认证,则写入操作日志表,同时更新用户的登录信息
            SysManage manage = sysManageService.findByAccount(username);
            manage.setManLastIp(manage.getManLoginIp());
            manage.setManLastTime(manage.getManLoginTime());
            manage.setManLoginIp(ip);
            manage.setManLoginTime(new Date());
            //sysManageService.save(manage);


            Collection<?> authorties = authentication.getAuthorities();
            session.setAttribute(ApplicationConstant.SESSION_USER_AUTHORITY, authorties);
            session.setAttribute(ApplicationConstant.SESSION_ERROR_MSG, null);
            session.setAttribute(ApplicationConstant.SESSION_USER_CONTEXT, manage);

            /*List<String> roleName = new ArrayList<String>();
            Set<SshRole> roleSet = manager.getRoleSet();
            for (SshRole role : roleSet) {
                roleName.add(role.getRoleName());
            }*/
            String roleName = "未知角色";
            SysRole role = sysRoleService.findOne(manage.getManRole());
            if (null != role && null != role.getRoleName()) {
                roleName = role.getRoleName();
            }
            session.setAttribute(ApplicationConstant.SESSION_USER_ROLES, roleName);

        } catch (Exception e) {
            LOG.error("authority error: ", e);
        }
        return authentication;
    }

    /**
     * Enables subclasses to override the composition of the password, such as by including additional values and a
     * separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to the password. A delimiter such
     * as a pipe (|) should be used to separate the password and extended value(s). The <code>AuthenticationDao</code>
     * will need to generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     * @return the password that will be presented in the <code>Authentication</code> request token to the
     * <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String obtainPasscode(HttpServletRequest request) {
        return request.getParameter(passcodeParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login request.
     *
     * @param usernameParameter the parameter name. Defaults to "j_username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setPasscodeParameter(String passcodeParameter) {
        Assert.hasText(passcodeParameter, "Passcode parameter must not be empty or null");
        this.passcodeParameter = passcodeParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to true, and an authentication
     * request is received which is not a POST request, an exception will be raised immediately and authentication will
     * not be attempted. The <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public String getPasscodeParameter() {
        return passcodeParameter;
    }
}
