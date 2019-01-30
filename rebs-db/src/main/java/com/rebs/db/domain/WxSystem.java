package com.rebs.db.domain;

import lombok.Data;

@Data
public class WxSystem {

	private String id;
	
	private String keyName;
	
	private String keyValue;
	
	private Integer deleted;
	
	private Integer version;
}
