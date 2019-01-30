package com.rebs.db.domain;

import lombok.Data;

/**
 * 考试题目数和分数
 * @author wdd
 *
 */
@Data
public class RandomExamSubject {
	private Integer randomExamSubjectId;
    private Integer randomExamId;
    private Integer orderIndex;
    private Integer itemTypeId;
    private String subjectName;
    private String remark;
    private Integer itemCount;
    private Integer unitScore;
    private Integer totalScore;
    private String memo;  
    private String typeName;  

}
