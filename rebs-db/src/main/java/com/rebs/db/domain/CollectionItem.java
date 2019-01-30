package com.rebs.db.domain;

/**
 * 试题收藏实体类

* 描述：

* @author yc  

* @date 2018年10月11日
 */
public class CollectionItem {

	private Integer collectionId;
	
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

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

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
}
