package com.rebs.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.OrgMapper;
import com.rebs.db.domain.Org;

@Service
public class OrgService {

    @Autowired
    private OrgMapper orgMapper;
    
    public Org selectByPrimaryKey(String orgId) {
        return orgMapper.selectByPrimaryKey(orgId);
    }
    
}
