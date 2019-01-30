package com.rebs.db.domain;

import java.util.Date;

import lombok.Data;

/**
 * 课件实体类

* 描述：

* @author yc  

* @date 2018年10月29日
 */
@Data
public class CourseWare {

	private Integer courseWareId;
	
	private String courseWareName;
	
	private Integer courseWareTypeId;
	
	private Integer provideOrg;
	
	private Date publishDate;
	
	private String authors;
	
	private String revisers;
	
	private String keyword;
	
	private String description;
	
	private String url;
	
	private String memo;
	
	private Integer isGroupLeader;
	
	private Integer technicianTypeId;
	
	private Integer orderIndex;
	
	private String provideOrgName;
}
