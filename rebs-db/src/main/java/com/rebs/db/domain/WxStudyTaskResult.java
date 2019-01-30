package com.rebs.db.domain;

import java.util.Date;

public class WxStudyTaskResult {

	private Integer taskResultId;
	
	/**
	 * 员工id
	 */
	private Integer employeeId;
	
	/**
	 * 任务id
	 */
	private Integer taskId;
	
	/**
	 * 任务详情id
	 */
	private Integer taskDetailId;
	
	
	/**
	 * 学习时间
	 */
	private Integer hasTime;
	
	private Date beginTime;
	
	private Date endTime;


	public Integer getTaskResultId() {
		return taskResultId;
	}


	public void setTaskResultId(Integer taskResultId) {
		this.taskResultId = taskResultId;
	}


	public Integer getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}


	public Integer getTaskId() {
		return taskId;
	}


	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}


	public Integer getTaskDetailId() {
		return taskDetailId;
	}


	public void setTaskDetailId(Integer taskDetailId) {
		this.taskDetailId = taskDetailId;
	}


	public Integer getHasTime() {
		return hasTime;
	}


	public void setHasTime(Integer hasTime) {
		this.hasTime = hasTime;
	}


	public Date getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
