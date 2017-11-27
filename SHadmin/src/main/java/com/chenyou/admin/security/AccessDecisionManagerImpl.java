package com.chenyou.admin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/11.
 */
@Component("accessDecisionManager")
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    final static Logger LOG = LoggerFactory.getLogger(AccessDecisionManagerImpl.class);

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        ConfigAttribute c = collection.iterator().next();
        if (c.equals(new SecurityConfig("null"))) {
            LOG.debug("403: collection is null, access denied!");
            throw new AccessDeniedException("403: collection is null, access denied!");
        }
        LOG.debug("access decide: " + collection.size());
        // 所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            // 访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            // 用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                LOG.debug("needPermission: " + needPermission + ", authority: " + ga.getAuthority());
                if (needPermission.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        LOG.debug("access decide: denied");
        // 没有权限 会跳转到login.jsp页面
        throw new AccessDeniedException("403: access denied!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
