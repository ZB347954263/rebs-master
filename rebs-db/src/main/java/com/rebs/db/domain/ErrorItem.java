package com.rebs.db.domain;

/**
 * 错题实体类

* 描述：

* @author yc  

* @date 2018年10月11日
 */
public class ErrorItem {

	/**
	 * 主键id
	 */
	private Integer errorId;
	
	/**
	 * 题目id
	 */
	private Integer itemId;
	
	/**
	 * 考试人员id
	 */
	private Integer employeeId;
	
	/**
	 * 类型  1：练习  2：考试
	 */
	private Integer type;

	/**
	 * 答案
	 */
	private String answer;
	
	private Integer examId;

	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	
}
