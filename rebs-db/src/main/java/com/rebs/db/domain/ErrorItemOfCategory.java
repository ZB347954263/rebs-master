package com.rebs.db.domain;

/**
 * 错误的题数通过书名分类

* 描述：

* @author yc  

* @date 2018年10月12日
 */
public class ErrorItemOfCategory {
	/**
	 * 书名ID
	 */
	private String bookId;
	/**
	 * 书名
	 */
	private String bookName;
	
	/**
	 * 考试id
	 */
	private Integer examId;
	
	/**
	 * 考试名
	 */
	private String examName;
	/**
	 * 错误数量
	 */
	private Integer errorCount;
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
}
