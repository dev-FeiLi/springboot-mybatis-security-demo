package com.chenyou.admin.security;

import com.chenyou.admin.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Shell Li on 2017/11/24.
 */
@Component
public class SecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    final static Logger LOG = LoggerFactory.getLogger(SecurityMetadataSourceImpl.class);

    @Autowired
    private SysRoleService sysRoleService;

    public static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
    private static RequestMatcher pathMatcher;

    public SecurityMetadataSourceImpl() {
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest req = ((FilterInvocation) object).getRequest();
        //LOG.debug("request url: "+(req == null) + "," + req.toString());
        String permitAll[] = {"/", "/login", "/getcaptcha", "/favicon.ico", "/bootstrap/**", "/dist/**", "/plugins/**", "/source/**"};
        for (String element : permitAll) {// 系统不需要验证权限的路径
            pathMatcher = new AntPathRequestMatcher(element);
            if (pathMatcher.matches(req)) { // 如果请求的路径匹配成功的话，则通过
                LOG.debug("authority pass: " + element);
                return null;
            }
        }
        // 先判断权限映射表是否空，空的话重新加载
        try {
            if (resourceMap.size() == 0) {
                sysRoleService.loadAuthorityMapping();
            }
        } catch (Exception e) {
            LOG.error("SecurityMetadataSourceImpl init Authority error: ", e);
        }

        Collection<ConfigAttribute> returnCollection = new ArrayList<>();
        Iterator<String> it = resourceMap.keySet().iterator();
        while (it.hasNext()) { // 请求路径匹配所有需要认证权限的路径
            String resURL = it.next();
            pathMatcher = new AntPathRequestMatcher(resURL);
            if (pathMatcher.matches(req)) {
                returnCollection = resourceMap.get(resURL);// 匹配到的，返回需要拥有的角色集合
                return returnCollection;
            }
        }
        // 没有匹配到路径的，拒绝，该处(new SecurityConfig("null"))用于accessDecisionManager中decide方法的判断
        returnCollection.add(new SecurityConfig("null"));
        return returnCollection;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
