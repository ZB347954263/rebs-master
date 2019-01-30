package com.rebs.db.dao;

import com.rebs.db.domain.WxIndex;

public interface WxIndexMapper {
    
    WxIndex selectIndexData(Integer employeeId);
   
}