package com.rebs.db.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.WxStudyTaskDetailMapper;
import com.rebs.db.domain.WxStudyTaskDetail;


@Service
public class WxStudyTaskDetailService {

    @Autowired
    private WxStudyTaskDetailMapper wxStudyTaskDetailMapper;
    
    public ArrayList<WxStudyTaskDetail> selectList(WxStudyTaskDetail wxStudyTaskDetail){
    	return wxStudyTaskDetailMapper.selectList(wxStudyTaskDetail);
    }
}
