package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

/**
 * @author wdd
 *  考试提交答案vo
 */
@Data
public class ExamVo {
	
	private Integer examId;
    
    /**
     * 类型2：考试
     */
    private Integer type;
    
    /**
     * 员工Id
     */
    private Integer employeeId;
    
    /**
     * 所有题目
     */
    private ArrayList<RandomExamItem> allLists;
    
    // 错题数
    private Integer errorItemCount;
    
    // 正确题数
    private Integer rightItemCount;
    
    //总题数
    private Integer totalItem;
    
    //分数
    private BigDecimal fraction;
    
    
    private Integer randomExamResultID;
    
    private Object randomExamResultIDReturn;
    
    private Date beginTime;
    private Date endTime;
    
    private Integer useExamTime;
    
    private RandomExamItem randomExamItem;
    
}
