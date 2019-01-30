package com.rebs.db.domain;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Book {
    
    private Integer bookId;
    private String bookName;
    private Integer knowledgeId;
    private String bookNo;
    private Integer publishOrg;
    private Date publishDate;
    private String authors;
    private String revisers;
    private String bookmaker;
    private String coverDesigner;
    private String keywords;
    private Integer pageCount;
    private Integer wordCount;
    private String discription;
    private String url;
    private String lastUpdatePerson;
    private Date lastUpdateDate;
    private String memo;
    private Integer isGroupLeader;
    private Integer techeicianTypeId;
    private Integer orderIndex;
    private Integer version;
    private String publishOrgName;
    private String knowledgeName;
    private String authorsName;
    
    private ArrayList<Object> orgidAL = new ArrayList<Object>();
    private ArrayList<Object> postidAL = new ArrayList<Object>();
    private ArrayList<Object> trainTypeidAL = new ArrayList<Object>();
    private String strTrainTypeNames;
    
    
    
}
