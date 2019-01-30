package com.rebs.db.domain;

/**
 * 收藏的题数通过书名分类

* 描述：

* @author yc  

* @date 2018年10月12日
 */
public class CollectionOfBook {
	/**
	 * 书名ID
	 */
	private String bookId;
	/**
	 * 书名
	 */
	private String bookName;
	/**
	 * 收藏数量
	 */
	private Integer collectionCount;
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
	public Integer getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}
}
