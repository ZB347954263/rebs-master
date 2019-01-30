package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 考试信息
 * @author wdd
 *
 */
@Data
public class RandomExam {
	private Integer randomExamId;
    private Integer orgId;
    private Integer categoryId;
    private Integer examTypeId;
    private String examName;
    private Integer examTime;
    private Date beginTime;
    private Date endTime;
    private Integer examModeId;
    private Integer minExamTimes;
    private Integer maxExamTimes;
    private BigDecimal convertTotalScore;
    private BigDecimal passScore;
    private Integer autoSaveInterval;     
    private Integer isUnderControl;
    private Integer isAutoScore;
    private Integer canSeeAnswer;
    private Integer canSeeScore;
    private Integer isPublicScore;
    private String description; 
    private Integer statusId;
    private String createPerson;
    private Date createTime;
    private String memo;
    private Integer downloaded;
    private Integer startMode;
    private Integer isStart;
    private Integer hasPaper;
    private String randomExamCode;
    private Integer version;
    private Integer examStyle;
    private Integer isUpload;
    private Integer isComputerexam;
    private String postId;
    private Integer technicianTypeId;
    private Integer isGroupLeader;
    private Integer hasTrainClass;
    private Integer isReset;
    private Integer randomExamModularTypeId;
    private Integer isReduceError;
    private Integer isAllArrange;
    private Date saveDate;
    private Integer saveStatus;  
    
    private String categoryName;  

}
