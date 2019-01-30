package com.rebs.db.domain;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author wdd
 *  考试结果
 */
@Data
public class RandomExamResultAnswerCurrent {
	
	/**
	 * 考试考生结果ID
	 */
	private Integer randomExamResultId;
    
    /**
     * 试卷试题ID
     */
    private Integer randomExamItemId;
    
    /**
     * 答案（使用|分隔）
     */
    private String answer;
    /**
     * 考试时间
     */
    private Integer examTime;
    /**
     * 评分分数
     */
    private BigDecimal judgeScore;
    /**
     * 评分状态ID
     */
    private Integer judgeStatusId;
    /**
     * 状态名称
     */
    private Integer judgeStatusName;
    /**
     * 评语
     */
    private String judgeRemark;
    
    private Integer randomExamResultAnswerId;
    private String selectAnswer;
    private String standardAnswer;
    private String statusName;
}
