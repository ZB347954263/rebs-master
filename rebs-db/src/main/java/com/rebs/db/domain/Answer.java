package com.rebs.db.domain;

import lombok.Data;

@Data
public class Answer {

    private Integer index;
    
    private String tip;
    
    private String content;
    
    private Boolean isSelect;
}
