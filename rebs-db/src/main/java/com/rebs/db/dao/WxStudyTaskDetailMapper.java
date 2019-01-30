package com.rebs.db.dao;

import java.util.ArrayList;

import com.rebs.db.domain.WxStudyTaskDetail;

public interface WxStudyTaskDetailMapper {

	ArrayList<WxStudyTaskDetail> selectList(WxStudyTaskDetail wxStudyTaskDetail);
}
