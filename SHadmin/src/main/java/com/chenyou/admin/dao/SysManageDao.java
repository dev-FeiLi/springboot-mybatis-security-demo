package com.chenyou.admin.dao;

import com.chenyou.admin.domain.SysManage;

import java.util.List;
import java.util.Map;

/**
 * Created by Shell Li on 2017/11/24.
 */
public interface SysManageDao {

    SysManage findOne(Long manId);

    SysManage findByManAccount(String manAccout);

    List<SysManage> findAll(Map<String, Object> param);

    int getCountAll();

    void insertManager(SysManage sysManage);
    void updateManager(SysManage sysManage);
}
