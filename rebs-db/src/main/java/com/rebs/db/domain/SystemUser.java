package com.rebs.db.domain;

import lombok.Data;

@Data
public class SystemUser {

    private String userId;
    
    private String password;
    
    private Integer employeeId;
    
    private Integer roleId;
    
    private String memo;
}
