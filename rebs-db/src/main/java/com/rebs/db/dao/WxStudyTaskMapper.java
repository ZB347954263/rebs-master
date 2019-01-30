package com.rebs.db.dao;

import java.util.ArrayList;

import com.rebs.db.domain.WxStudyTask;

public interface WxStudyTaskMapper {

	ArrayList<WxStudyTask> selectList(WxStudyTask wxStudyTask);
}
