package com.chenyou.admin.dao;

import com.chenyou.admin.domain.SysRole;

import java.util.List;
import java.util.Map;

/**
 * Created by Shell Li on 2017/11/24.
 */
public interface SysRoleDao {
    SysRole findOneById(Long roleId);

    List<SysRole> findAll();

    List<SysRole> findAllByPage(Map<String, Object> param);

    int getCountAll();

    void insertRole(SysRole sysRole);
    void updateRole(SysRole sysRole);
}
