package com.rebs.db.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.WxStudyTaskResultMapper;
import com.rebs.db.domain.WxStudyTaskResult;

@Service
public class WxStudyTaskResultService {

    @Autowired
    private WxStudyTaskResultMapper wxStudyTaskResultMapper;
    
    public int insert(WxStudyTaskResult wxStudyTaskResult){
    	return wxStudyTaskResultMapper.insert(wxStudyTaskResult);
    }
    
    public int update(WxStudyTaskResult wxStudyTaskResult){
    	return wxStudyTaskResultMapper.update(wxStudyTaskResult);
    }
    
    public ArrayList<WxStudyTaskResult>  selectList(WxStudyTaskResult wxStudyTaskResult){
    	return wxStudyTaskResultMapper.selectList(wxStudyTaskResult);
    }
    
}
