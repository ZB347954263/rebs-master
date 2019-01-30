package com.rebs.db.domain;

import lombok.Data;

/**
 * 课件类别

* 描述：

* @author yc  

* @date 2018年10月29日
 */
@Data
public class CourseWareType {

	private Integer courseWareTypeId;
	
	private Integer parentId;
	
	private String idPath;
	
	private Integer levelNum;
	
	private Integer orderIndex;
	
	private String courseWareTypeName;
	
	private String description;
	
	private String memo;
}
