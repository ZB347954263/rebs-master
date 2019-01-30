package com.rebs.db.domain;

public class WxIndex {
	
	/**
	 * 员工Id
	 */
	private Integer employeeId;
	
	/**
	 * 登录时长
	 */
	private Integer practiceTime;
	/**
	 * 登录次数
	 */
	private Integer loginTime;
	/**
	 * 考试最高成绩
	 */
	private Integer examMaxScore;
	
	/**
	 * 收藏题数
	 */
	private Integer collectionCount;
	
	/**
	 * 错误题数
	 */
	private Integer errorCount;
	
	/**
	 * 课件学习时长
	 */
	private Integer courseWareTime;
	
	/**
	 * 教材学习时长
	 */
	private Integer bookTime;
	
	/**
	 * 任务个数
	 */
	private Integer taskCount;
	
	/**
	 * 考试个数
	 */
	private Integer examCount;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getPracticeTime() {
		return practiceTime;
	}

	public void setPracticeTime(Integer practiceTime) {
		this.practiceTime = practiceTime;
	}

	public Integer getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Integer loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getExamMaxScore() {
		return examMaxScore;
	}

	public void setExamMaxScore(Integer examMaxScore) {
		this.examMaxScore = examMaxScore;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public Integer getCourseWareTime() {
		return courseWareTime;
	}

	public void setCourseWareTime(Integer courseWareTime) {
		this.courseWareTime = courseWareTime;
	}

	public Integer getBookTime() {
		return bookTime;
	}

	public void setBookTime(Integer bookTime) {
		this.bookTime = bookTime;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public Integer getExamCount() {
		return examCount;
	}

	public void setExamCount(Integer examCount) {
		this.examCount = examCount;
	}
}
