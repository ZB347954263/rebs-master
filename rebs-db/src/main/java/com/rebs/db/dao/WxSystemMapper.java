package com.rebs.db.dao;

import java.util.List;

import com.rebs.db.domain.WxSystem;

public interface WxSystemMapper {
    
    
    List<WxSystem> selectByKeyName(String keyName);


}