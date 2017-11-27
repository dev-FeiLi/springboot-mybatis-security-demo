package com.chenyou.admin.security;



import com.chenyou.admin.domain.SysManage;
import com.chenyou.admin.domain.SysRole;
import com.chenyou.admin.service.SysManageService;
import com.chenyou.admin.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/4/11.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    final static Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final boolean enabled = true; // 是否可用
    private final boolean accountNonExpired = true; // 是否过期
    private final boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true; // 账号是否被锁定
    @Autowired
    private SysManageService sysManageService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LOG.debug("UserDetailsService loadUserByUsername start");
        try {
            SysManage manager = sysManageService.findByAccount(account);
            if (null != manager) {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Long roleId = manager.getManRole();
                if (roleId == null || roleId <= 0) throw new Exception("user's role is empty");
                SysRole sysRole = sysRoleService.findOne(roleId);
                if (sysRole == null) throw new Exception("user's role is invalidate");
                authorities.add(new SimpleGrantedAuthority(String.valueOf(sysRole.getRoleId())));
                accountNonLocked = "Y".equals(manager.getManStatus());
                UserDetails user = new User(manager.getManAccount(), manager.getManPasswd(), enabled,
                        accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
                LOG.debug("UserDetailsService loadUserByUsername return user: ", user);
                return user;
            }
            LOG.debug("UserDetailsService loadUserByUsername user not found");
            throw new UsernameNotFoundException("user not found");
        } catch (Exception e) {
            LOG.error("UserDetailsService loadUserByUsername error: ", e);
            throw new UsernameNotFoundException("user login found exception");
        }
    }
}
