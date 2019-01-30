package com.rebs.db.domain;

import lombok.Data;

@Data
public class Org {

    private Integer orgId;
    private Integer parentId;
    private String idPath;
    private Integer levelNum;
    private Integer orderIndex;
    private String shortName;
    private String fullName;
    private String address;
    
}
