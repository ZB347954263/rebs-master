package com.rebs.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.WxIndexMapper;
import com.rebs.db.domain.WxIndex;

@Service
public class WxIndexService {

    @Autowired
    private WxIndexMapper wxIndexMapper;
    /**
     * 获取考试最高分，收藏数量，错题数量
     * @param employeeId
     * @return
     */
    public WxIndex selectIndexData(Integer employeeId) {
        return wxIndexMapper.selectIndexData(employeeId);
    }
    
}
