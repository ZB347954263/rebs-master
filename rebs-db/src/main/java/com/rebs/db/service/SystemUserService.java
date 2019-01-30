package com.rebs.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.SystemUserMapper;
import com.rebs.db.domain.SystemUser;
import com.rebs.db.domain.SystemUserView;

@Service
public class SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    
    public SystemUser selectByPrimaryKey(String userId) {
        return systemUserMapper.selectByPrimaryKey(userId);
    }
    
    
    public List<SystemUserView> selectByMobilePhone(String mobilePhone) {
        return systemUserMapper.selectByMobilePhone(mobilePhone);
    }
    
    public List<SystemUserView> selectByWorkNo(String workNo) {
        return systemUserMapper.selectByWorkNo(workNo);
    }
    
    public List<SystemUserView> selectByOpenId(String openId) {
        return systemUserMapper.selectByOpenId(openId);
    }
    
    
    
}
