package com.rebs.db.dao;

import com.rebs.db.domain.Org;

public interface OrgMapper {
    
    Org selectByPrimaryKey(String orgId);
   
}