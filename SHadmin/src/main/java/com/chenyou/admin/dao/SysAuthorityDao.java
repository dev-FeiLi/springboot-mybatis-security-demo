package com.chenyou.admin.dao;

import com.chenyou.admin.domain.SysAuthority;

import java.util.List;
import java.util.Map;

/**
 * Created by Shell Li on 2017/11/24.
 */
public interface SysAuthorityDao {

    SysAuthority findOne(Long authId);

    List<SysAuthority> findParent(Long auth_parent);

    List<SysAuthority> findAll(Map<String, Object> param);

    int getCountAll();

    void updateAuthority(SysAuthority sysAuthority);

    void insertAuthority(SysAuthority sysAuthority);

}
