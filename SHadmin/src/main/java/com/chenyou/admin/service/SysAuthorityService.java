package com.chenyou.admin.service;

import com.chenyou.admin.dao.SysAuthorityDao;
import com.chenyou.admin.domain.SysAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shell Li on 2017/11/27.
 */
@Service
public class SysAuthorityService {

    final static Logger LOG = LoggerFactory.getLogger(SysAuthorityService.class);

    @Autowired
    private SysAuthorityDao sysAuthorityDao;

    public List<SysAuthority> findByParent(Long authParent){
        try {
            return sysAuthorityDao.findParent(authParent);
        } catch (Exception e) {
            LOG.error("findByParent error: ", e);
        }
        return null;
    }

    public List<SysAuthority> findAll(){
        return sysAuthorityDao.findAll(null);
    }

    public List<SysAuthority> findAll(int page, int size) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", page);
        param.put("size", size);
        return sysAuthorityDao.findAll(param);
    }

    public int getTotal(){
        return sysAuthorityDao.getCountAll();
    }

    public SysAuthority findOne(Long authId){
        return sysAuthorityDao.findOne(authId);
    }

    public void insertAuthority(SysAuthority sysAuthority){
        sysAuthorityDao.insertAuthority(sysAuthority);
    }

    public void updateAuthority(SysAuthority sysAuthority){
        sysAuthorityDao.updateAuthority(sysAuthority);
    }

}
