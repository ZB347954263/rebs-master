package com.rebs.db.dao;

import java.util.ArrayList;
import java.util.Map;

import com.rebs.db.domain.PastExamIN;
import com.rebs.db.domain.RandomExamResultCurrent;

public interface PastExamMapper {

	/**
	 * 根据userId得到已考试列表
	 * @param map
	 * @return
	 */
	public ArrayList<RandomExamResultCurrent> getPastExamByUserId(PastExamIN pastExamIn);
	
	/**
	 * 根据考试randomExamResultId获取题目
	 * @param map
	 */
	public void callUSP_random_exam_ITEM_G(Map<Object, Object> map);
	
}
