package com.chenyou.admin.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    //@Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    /*@Autowired
    @Qualifier("accessDecisionManager")
    private AccessDecisionManager accessDecisionManager;
    @Autowired
    private AuthenticationManager authenticationManager;*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        // object为FilterInvocation对象
        // super.beforeInvocation(fi);//源码
        // 1.获取请求资源的权限
        // 执行 Collection<ConfigAttribute> attributes = securityMetadataSource.getAttributes(fi);
        // 2.是否拥有权限
        // this.accessDecisionManager.decide(authenticated, fi, attributes);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}
