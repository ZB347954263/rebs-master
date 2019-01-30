package com.rebs.db.domain;

import java.util.Date;

import lombok.Data;

/**
 * @author wdd
 *  根据UserId获取已经考试的列表传进参数对象
 */
@Data
public class PastExamIN {
	
	private Integer examId;
	
	private Integer userID;
    
    private Integer examModeId;
    
    private Date beginDate;
    
    private Date endDate;
    
}
