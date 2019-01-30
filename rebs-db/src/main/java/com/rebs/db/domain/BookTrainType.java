package com.rebs.db.domain;

import lombok.Data;

@Data
public class BookTrainType {

    private Integer bookId;
    private Integer trainTypeId;
    private String typeName;
    private Integer parentId;
    private Integer orderIndex;
    
    
}
