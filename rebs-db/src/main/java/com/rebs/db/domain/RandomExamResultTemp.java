package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author wdd
 *  考试结果
 */
@Data
public class RandomExamResultTemp {
	
	private Integer randomExamResultId;
	private Integer orgid;
	private Integer randomExamId;
	private Integer examineeId;
    private Date beginTime;
    private Date currentTime;
    private Date endTime;
    private Integer examTime;
    private BigDecimal autoScore;
    private BigDecimal score;
    private BigDecimal correctRate;
    private Integer isPass;
    private Integer statusId;
    private String memo;
    private Integer randomExamResultIdStation;
    
    private String examName;
    private String examineeName;
    private String statusName;
    private String workNo;
    private String orgName;
    private String postName;
    
}
