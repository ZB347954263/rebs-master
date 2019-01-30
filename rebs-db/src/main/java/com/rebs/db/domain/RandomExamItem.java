package com.rebs.db.domain;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

/**
 * 考试题目数和分数
 * @author wdd
 *
 */
@Data
public class RandomExamItem {
	private Integer randomExamItemId;
    private Integer itemId;
    private Integer bookId;
    private Integer chapterId;
    private Integer categoryId;
    private Integer orgId;
    private Integer typeId;
    private Integer completeTime;
    private Integer difficultyId;
    private String source;  
    private String version;  
    private Integer score;
    private String content;
    private Integer answerCount;
    private String selectAnswer;
    private String standardAnswer;
    private String description;
    private Date outdateDate;
    private Integer usedCount;
    private Integer statusId;
    private String createPerson;
    private Date createTime;
    private String memo;
    private Integer subjectId;
    private Integer randomExamId;
    private Integer strategyId;
    private Integer errorDate;
    private Integer weight;
    private Integer parentItemId;
    private String motherCode;
    private Integer itemIndex;
    
    private String typeName;
    private String answer;
    
    private String[] strAnswer;

    private Integer standardAnswesInt;
    
    //选项
    private ArrayList<Answer> answerList;
    
    //选项(用于样式，选过的Answer 的isSelect给true)
    private ArrayList<Answer> options;
    
    //多选题标准答案
    private ArrayList<Answer> standardAnswerList;
    
    /**
     * 题目状态，0未做，1 对 2 错题
     */
    private Integer isAnswer;
    
    //做过的题给true
    private Boolean isNoFirst;
    
    /**
     * 员工的答案，多选 | 分割
     */
    private String answered;
    

}
