package com.rebs.db.domain;

import lombok.Data;

/**
 * 知识体系
 * @author zb
 *
 */
@Data
public class Knowledge {

    private Integer knowledgeId;
    private Integer parentId;
    private String idPath;
    private Integer levelNum;
    private Integer orderIndex;
    private String knowledgeName;
    private String description;
    private String memo;
    private Boolean isTemplate;
    private Boolean isPromotion;
    
    
}
