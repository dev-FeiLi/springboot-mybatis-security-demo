package com.chenyou.admin.service;

import com.chenyou.admin.dao.SysAuthorityDao;
import com.chenyou.admin.dao.SysRoleDao;
import com.chenyou.admin.domain.SysAuthority;
import com.chenyou.admin.domain.SysRole;
import com.chenyou.admin.security.SecurityMetadataSourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Shell Li on 2017/11/24.
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysAuthorityDao sysAuthorityDao;

    public SysRole findOne(Long roleId){
        return sysRoleDao.findOneById(roleId);
    }

    public List<SysRole> findAll(){
        return sysRoleDao.findAllByPage(null);
    }
    public List<SysRole> findAll(int page, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", page);
        param.put("size", size);
        return sysRoleDao.findAllByPage(param);
    }

    public int getTotal(){
        return sysRoleDao.getCountAll();
    }

    public void insertRole(SysRole sysRole){
        sysRoleDao.insertRole(sysRole);
    }

    public void updateRole(SysRole sysRole){
        sysRoleDao.updateRole(sysRole);
    }


    // 刷新所有资源与权限的关系，该动作在系统启动之时，或者在新建或修改角色信息之后发生
    // 因为resourceMap是静态属性，不强制刷新则重新分配的权限不生效
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public void loadAuthorityMapping() throws Exception {
        Map<String, Collection<ConfigAttribute>> resourceTmpMap = new HashMap<>();
        List<SysRole> roleList = sysRoleDao.findAll();
        for (SysRole role : roleList) {
            String authString = role.getRoleAuths();
            if (null == authString || "".equals(authString)) continue;
            String[] authIds = authString.split(",");
            for (String id : authIds) {
                SysAuthority authority = sysAuthorityDao.findOne(Long.valueOf(id.trim()));
                String path = authority.getAuthUrl();
                Collection<ConfigAttribute> collection = resourceTmpMap.get(path);
                if (null == collection) {
                    collection = new ArrayList<>();
                }
                collection.add(new SecurityConfig(String.valueOf(role.getRoleId())));
                resourceTmpMap.put(path, collection);
            }
        }
        SecurityMetadataSourceImpl.resourceMap.clear();
        SecurityMetadataSourceImpl.resourceMap.putAll(resourceTmpMap);
    }
}
