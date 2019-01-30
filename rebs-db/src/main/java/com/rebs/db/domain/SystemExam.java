package com.rebs.db.domain;

import lombok.Data;


@Data
public class SystemExam {
	
	private Integer systemExamId;
	
	private String examMessage;
	
	private Integer examTime;
	
	private Integer examNumber;
}