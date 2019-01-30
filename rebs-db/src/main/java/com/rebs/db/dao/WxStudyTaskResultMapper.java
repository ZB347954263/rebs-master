package com.rebs.db.dao;


import java.util.ArrayList;

import com.rebs.db.domain.WxStudyTaskResult;

public interface WxStudyTaskResultMapper {

    int insert(WxStudyTaskResult wxStudyTaskResult);
    
    int update(WxStudyTaskResult wxStudyTaskResult);
    
    ArrayList<WxStudyTaskResult>  selectList(WxStudyTaskResult wxStudyTaskResult);
     
}
