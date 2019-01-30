package com.rebs.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebs.db.dao.WxLoginTimeMapper;
import com.rebs.db.domain.WxLoginTime;

/**
 * @author zb
 *  微信登录次数、练习时长
 */
@Service
@Transactional
public class WxLoginTimeService {

    @Autowired
    private WxLoginTimeMapper wxLoginTimeMapper;
    
    public boolean insert(WxLoginTime wxLoginTime){
        return wxLoginTimeMapper.insert(wxLoginTime) == 1;
    }
    
    public WxLoginTime selectByPrimaryKey(Integer employeeId){
        return wxLoginTimeMapper.selectByPrimaryKey(employeeId);
    }

    public boolean updateByPrimaryKeySelective(WxLoginTime wxLoginTime){
        return wxLoginTimeMapper.updateByPrimaryKeySelective(wxLoginTime) == 1;
    }
}
