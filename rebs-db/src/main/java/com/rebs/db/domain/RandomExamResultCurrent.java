package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 考试人的信息
 * @author wdd
 *
 */
public class RandomExamResultCurrent {
	private Integer randomExamResultId;
    private Integer orgid;
    private Integer randomExamId;
    private Integer examineeId;
    private Date beginTime;
    private Date currentTime;
    private Date endTime;
    private Integer examTime;
    private String examUseTime;
    private BigDecimal autoScore;
    private BigDecimal score;
    private BigDecimal correctRate;
    private Integer isPass;        
    private Integer statusId;
    private String memo;
    private Integer examSeqNo;
    
    private String orgName;
    private String examName;
    private String examineeName;
    private String statusName;
    private String workNo;
    private String postName;
    
    private Date currentDateTime;
    private Date endDateTime;
    private Integer organizationId;
    private Date beginDateTime;
    
    private Integer canSeeAnswer;
    private Integer canSeeScore;
    
	/**
	 * 获取 randomExamResultId
	 * @return randomExamResultId randomExamResultId
	 */
	public Integer getRandomExamResultId() {
		return randomExamResultId;
	}
	/**
	 * 设置 randomExamResultId
	 * @param randomExamResultId randomExamResultId
	 */
	public void setRandomExamResultId(Integer randomExamResultId) {
		this.randomExamResultId = randomExamResultId;
	}
	/**
	 * 获取 orgid
	 * @return orgid orgid
	 */
	public Integer getOrgid() {
		return orgid;
	}
	/**
	 * 设置 orgid
	 * @param orgid orgid
	 */
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	/**
	 * 获取 randomExamId
	 * @return randomExamId randomExamId
	 */
	public Integer getRandomExamId() {
		return randomExamId;
	}
	/**
	 * 设置 randomExamId
	 * @param randomExamId randomExamId
	 */
	public void setRandomExamId(Integer randomExamId) {
		this.randomExamId = randomExamId;
	}
	/**
	 * 获取 examineeId
	 * @return examineeId examineeId
	 */
	public Integer getExamineeId() {
		return examineeId;
	}
	/**
	 * 设置 examineeId
	 * @param examineeId examineeId
	 */
	public void setExamineeId(Integer examineeId) {
		this.examineeId = examineeId;
	}
	/**
	 * 获取 beginTime
	 * @return beginTime beginTime
	 */
	@JsonFormat(pattern = "yyyy年MM月dd日",timezone="GMT+8")
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置 beginTime
	 * @param beginTime beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取 currentTime
	 * @return currentTime currentTime
	 */
	public Date getCurrentTime() {
		return currentTime;
	}
	/**
	 * 设置 currentTime
	 * @param currentTime currentTime
	 */
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	/**
	 * 获取 endTime
	 * @return endTime endTime
	 */
	@JsonFormat(pattern = "yyyy年MM月dd日",timezone="GMT+8")
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置 endTime
	 * @param endTime endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取 examTime
	 * @return examTime examTime
	 */
	public Integer getExamTime() {
		return examTime;
	}
	/**
	 * 设置 examTime
	 * @param examTime examTime
	 */
	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}
	/**
	 * 获取 autoScore
	 * @return autoScore autoScore
	 */
	public BigDecimal getAutoScore() {
		return autoScore;
	}
	/**
	 * 设置 autoScore
	 * @param autoScore autoScore
	 */
	public void setAutoScore(BigDecimal autoScore) {
		this.autoScore = autoScore;
	}
	/**
	 * 获取 score
	 * @return score score
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置 score
	 * @param score score
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取 correctRate
	 * @return correctRate correctRate
	 */
	public BigDecimal getCorrectRate() {
		return correctRate;
	}
	/**
	 * 设置 correctRate
	 * @param correctRate correctRate
	 */
	public void setCorrectRate(BigDecimal correctRate) {
		this.correctRate = correctRate;
	}
	/**
	 * 获取 isPass
	 * @return isPass isPass
	 */
	public Integer getIsPass() {
		return isPass;
	}
	/**
	 * 设置 isPass
	 * @param isPass isPass
	 */
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	/**
	 * 获取 statusId
	 * @return statusId statusId
	 */
	public Integer getStatusId() {
		return statusId;
	}
	/**
	 * 设置 statusId
	 * @param statusId statusId
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	/**
	 * 获取 memo
	 * @return memo memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 设置 memo
	 * @param memo memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 获取 examSeqNo
	 * @return examSeqNo examSeqNo
	 */
	public Integer getExamSeqNo() {
		return examSeqNo;
	}
	/**
	 * 设置 examSeqNo
	 * @param examSeqNo examSeqNo
	 */
	public void setExamSeqNo(Integer examSeqNo) {
		this.examSeqNo = examSeqNo;
	}
	/**
	 * 获取 orgName
	 * @return orgName orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * 设置 orgName
	 * @param orgName orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * 获取 examName
	 * @return examName examName
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置 examName
	 * @param examName examName
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取 examineeName
	 * @return examineeName examineeName
	 */
	public String getExamineeName() {
		return examineeName;
	}
	/**
	 * 设置 examineeName
	 * @param examineeName examineeName
	 */
	public void setExamineeName(String examineeName) {
		this.examineeName = examineeName;
	}
	/**
	 * 获取 statusName
	 * @return statusName statusName
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 statusName
	 * @param statusName statusName
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取 workNo
	 * @return workNo workNo
	 */
	public String getWorkNo() {
		return workNo;
	}
	/**
	 * 设置 workNo
	 * @param workNo workNo
	 */
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	/**
	 * 获取 postName
	 * @return postName postName
	 */
	public String getPostName() {
		return postName;
	}
	/**
	 * 设置 postName
	 * @param postName postName
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	/**
	 * 获取 currentDateTime
	 * @return currentDateTime currentDateTime
	 */
	public Date getCurrentDateTime() {
		return currentDateTime;
	}
	/**
	 * 设置 currentDateTime
	 * @param currentDateTime currentDateTime
	 */
	public void setCurrentDateTime(Date currentDateTime) {
		this.currentDateTime = currentDateTime;
	}
	/**
	 * 获取 endDateTime
	 * @return endDateTime endDateTime
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}
	/**
	 * 设置 endDateTime
	 * @param endDateTime endDateTime
	 */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	/**
	 * 获取 organizationId
	 * @return organizationId organizationId
	 */
	public Integer getOrganizationId() {
		return organizationId;
	}
	/**
	 * 设置 organizationId
	 * @param organizationId organizationId
	 */
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * 获取 beginDateTime
	 * @return beginDateTime beginDateTime
	 */
	public Date getBeginDateTime() {
		return beginDateTime;
	}
	/**
	 * 设置 beginDateTime
	 * @param beginDateTime beginDateTime
	 */
	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}
	/**
	 * 获取 examUseTime
	 * @return examUseTime examUseTime
	 */
	public String getExamUseTime() {
		return examUseTime;
	}
	/**
	 * 设置 examUseTime
	 * @param examUseTime examUseTime
	 */
	public void setExamUseTime(String examUseTime) {
		this.examUseTime = examUseTime;
	}
	/**
	 * 获取 canSeeAnswer
	 * @return canSeeAnswer canSeeAnswer
	 */
	public Integer getCanSeeAnswer() {
		return canSeeAnswer;
	}
	/**
	 * 设置 canSeeAnswer
	 * @param canSeeAnswer canSeeAnswer
	 */
	public void setCanSeeAnswer(Integer canSeeAnswer) {
		this.canSeeAnswer = canSeeAnswer;
	}
	/**
	 * 获取 canSeeScore
	 * @return canSeeScore canSeeScore
	 */
	public Integer getCanSeeScore() {
		return canSeeScore;
	}
	/**
	 * 设置 canSeeScore
	 * @param canSeeScore canSeeScore
	 */
	public void setCanSeeScore(Integer canSeeScore) {
		this.canSeeScore = canSeeScore;
	}

}
