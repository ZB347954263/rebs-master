package com.rebs.db.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WxStudyTask {
	
	/**
	 * 任务id
	 */
	private Integer taskId;
	
	/**
	 * 任务名称
	 */
	private String taskName;
	
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date beginTime;
	
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date endTime;
	
	/**
	 * 0——未发布 1——已发布 2——已结束
	 */
	private Integer statusId;
	
	
	/**
	 * 员工id
	 */
	private Integer employeeId;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
}
