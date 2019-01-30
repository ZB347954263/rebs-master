package com.rebs.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.WxStudyTaskMapper;
import com.rebs.db.domain.WxStudyTask;

@Service
public class WxStudyTaskService {

	@Autowired
	private WxStudyTaskMapper wxStudyTaskMapper;
	
	public ArrayList<WxStudyTask> selectList(WxStudyTask wxStudyTask){
		return wxStudyTaskMapper.selectList(wxStudyTask);
	}
}
