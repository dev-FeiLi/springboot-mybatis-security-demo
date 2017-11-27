package com.chenyou.admin.service;

import com.chenyou.admin.dao.SysManageDao;
import com.chenyou.admin.domain.SysManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Shell Li on 2017/11/24.
 */
@Service
public class SysManageService {

    @Autowired
    private SysManageDao sysManageDao;

    public SysManage findOne(Long manId){
        return  sysManageDao.findOne(manId);
    }

    public SysManage findByAccount(String account) {
        return sysManageDao.findByManAccount(account);
    }

    public List<SysManage> findAll(Map<String, Object> param){
        return sysManageDao.findAll(param);
    }

    public int getTotal(){
        return sysManageDao.getCountAll();
    }

    public void insertManager(SysManage sysManage){
        sysManageDao.insertManager(sysManage);
    }

    public void updateManager(SysManage sysManage){
        sysManageDao.updateManager(sysManage);
    }
}
