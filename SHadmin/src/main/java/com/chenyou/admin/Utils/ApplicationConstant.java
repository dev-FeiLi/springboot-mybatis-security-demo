package com.chenyou.admin.Utils;

/**
 * Created by Administrator on 2017/4/10.
 */
public class ApplicationConstant {
    public final static String LOGIN_PAGE_PATH = "/login"; // 登录页面的路径
    public final static String LOGOUT_PAGE_PATH = "/logout"; // 退出登录页面的路径
    public final static String LOGIN_CHECK_PATH = "/logcheck"; // 登录检查用户名密码的路径
    public final static String ERROR_PAGE_PATH = "/error"; // 系统错误路径
    public final static String LOGIN_CAPTCHA_PATH = "/getcaptcha"; // 验证码路径
    public final static String SYSTEM_INDEX_PATH = "/index"; // 系统首页的路径，登录成功的页面
    public final static String SESSION_VALIDATE_CODE = "sessionConstant"; //登录验证码的session键
    public final static String SESSION_ERROR_MSG = "errMsg"; // 存放在session中的错误信息
    public final static String SESSION_USER_CONTEXT = "userContext"; // 登录成功后，用户的session的key
    public final static String SESSION_USER_AUTHORITY = "roleAuthority"; // 登录成功后，用户的凭证，其实就是用户角色的ID
    public final static String SESSION_USER_ROLES = "roleName"; // 登录成功后，用户的角色的名称

    public final static String AD_IMG_URL = "cdnfiles/";
    public final static String CDN_URL = "http://img.9ads.net/"; // 图片CDN加速地址
}
