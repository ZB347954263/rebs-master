package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Item {
    
    private Integer itemId;
    private Integer bookId;
    private Integer orgId;
    private String bookName;
    private Integer chapterId;
    private String chapterName;
    private String chapterPath;
    private Integer categoryId;
    private String categoryName;
    private String categoryPath;
    private Integer organizationId;
    private String organizationName;
    private Integer typeId;
    private String typeName;
    private Integer completeTime;
    private Integer difficultyId;
    private String difficultyName;
    private String source;
    private String version;
    private BigDecimal score;
    private String content;
    private Integer answerCount;
    private String selectAnswer;
    private String standardAnswer;
    private String description;
    private Date outDateDate;
    private Integer usedCount;
    private Integer statusId;
    private String statusName;
    private String createPerson;
    private Date createTime;
    private Integer usageId;
    private String memo;
    private Integer strategyId;
    private Integer hasPicture;
    private String keyWord;
    private Integer parentItemId;
    private String motherCode;
    private Integer itemIndex;
    private Integer authors;
    private String shortName;
    private String[] strAnswer;
    
    private Integer standardAnswesInt;
    
    //选项
    private ArrayList<Answer> answerList;
    
    //多选题标准答案
    private ArrayList<Answer> standardAnswerList;
    
    /**
     * 题目状态，0未做，1 对 2 错题
     */
    private Integer isAnswer;
    
    
    /**
     * 员工的答案，多选 | 分割
     */
    private String answered;
    
    
    /**
     * 选择的错误答案
     */
    private String errorAnswer;
    private Integer errorId;
    //错误选项
    private ArrayList<Answer> errorAnswerList;
    
    //多选题错误答案
    private ArrayList<Answer> manyErrorAnswersList;
    
    
}
