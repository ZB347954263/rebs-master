package com.rebs.db.domain;

public class WxStudyTaskDetail {
	
	/**
	 * 任务详情id
	 */
	private Integer taskDetailId;
	
	/**
	 * 任务id
	 */
	private Integer taskId;
	
	/**
	 * 教材id
	 */
	private Integer bookId;
	
	/**
	 * 课件id
	 */
	private Integer courseWareId;
	
	/**
	 * 所需时间  分钟
	 */
	private Integer needTime;
	
	/**
	 * 教材名称
	 */
	private String bookName;
	
	/**
	 * 课件名称
	 */
	private String courseWareName;
	
	/**
	 * 已学习时间  分钟
	 */
	private String hasTime;
	
	/**
	 * 类别  1--教材   2--课件
	 */
	private Integer type;

	public Integer getTaskDetailId() {
		return taskDetailId;
	}

	public void setTaskDetailId(Integer taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getCourseWareId() {
		return courseWareId;
	}

	public void setCourseWareId(Integer courseWareId) {
		this.courseWareId = courseWareId;
	}

	public Integer getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Integer needTime) {
		this.needTime = needTime;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getCourseWareName() {
		return courseWareName;
	}

	public void setCourseWareName(String courseWareName) {
		this.courseWareName = courseWareName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHasTime() {
		return hasTime;
	}

	public void setHasTime(String hasTime) {
		this.hasTime = hasTime;
	}
}
