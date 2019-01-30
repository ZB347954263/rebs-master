package com.rebs.db.dao;

import com.rebs.db.domain.WxLoginTime;

public interface WxLoginTimeMapper {
    
    int insert(WxLoginTime wxLoginTime);
    
    WxLoginTime selectByPrimaryKey(Integer employeeId);

    int updateByPrimaryKeySelective(WxLoginTime wxLoginTime);

}