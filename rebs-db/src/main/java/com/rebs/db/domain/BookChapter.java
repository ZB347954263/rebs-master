package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class BookChapter {

    private Integer bookId;
    private Integer chapterId;
    private Integer parentId;
    private String idPath;
    private Integer levelNum;
    private Integer orderIndex;
    private String chapterName;
    private String description;
    private String memo;
    private String referenceRegulation;
    private String url;
    private String studyDemand;
    private BigDecimal studyHours;
    private Integer hasExam;
    private String examForm;
    private String lastUpdatePerson;
    private Date lastUpdateDate;
    private String namePath;
    private Boolean isEdit;
    private Boolean isCannotSeeAnswer;
    private Boolean isMotherItem;
    
}
