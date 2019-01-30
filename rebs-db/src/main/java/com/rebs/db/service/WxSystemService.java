package com.rebs.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebs.db.dao.WxSystemMapper;
import com.rebs.db.domain.WxSystem;


@Service
@Transactional
public class WxSystemService {

    @Autowired
    private WxSystemMapper wxSystemMapper;
    
    
    public List<WxSystem> selectByKeyName(String keyName){
        return wxSystemMapper.selectByKeyName(keyName);
    }

}
