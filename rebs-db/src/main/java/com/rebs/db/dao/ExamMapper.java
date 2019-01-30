package com.rebs.db.dao;

import java.util.Map;

import com.rebs.db.domain.Employee;
import com.rebs.db.domain.RandomExamResultCurrent;
import com.rebs.db.domain.SystemExam;

public interface ExamMapper {

	/**
	 * 根据userId得到考试列表
	 * @param map
	 * @return
	 */
	public void callProcedureWX_USP_Exam_By_user(Map<Object, Object> map);
	
	/**
	 * 查询考试规则
	 * @param systemExamId
	 * @return
	 */
	public SystemExam getExamTips(Integer systemExamId);
	
	/**
	 * 获取考试人的信息
	 * @param employeeID
	 * @return
	 */
	public Employee getExamUserInfo(Integer employeeID);
	
	/**
	 * 考试基本信息
	 * @param map
	 */
	public void getExamInfoUSP_Random_Exam_G(Map<Object, Object> map);
	
	/**
	 * 获取考试题数和分数
	 * @param map
	 * @return
	 */
	public void callUSP_Random_Exam_SUBJECT_Q(Map<Object, Object> map);
	
	/**
	 * 获取考试题目
	 * @param map
	 */
	public void callUSP_random_exam_ITEM_G_Cur(Map<Object, Object> map);
	
	/**
	 * 获取考试题目的参数
	 * @param map
	 */
	public void callUSP_Random_EXAM_RESULT_Cur_N(Map<Object, Object> map);
	
	/**
	 * 执行每题的结果正确方法
	 * @param map
	 */
	public void callUSP_Random_EXAM_ANSWER_Cur_U(Map<Object, Object> map);
	
	/**
	 * 更新实时考试记录
	 * @param map
	 */
	public void callUSP_Random_EXAM_RESULT_Cur_U(Map<Object, Object> map);
	
	/**
	 * 将实时考试记录（临时表）转存到中间考试成绩表和答卷表
	 * @param map
	 */
	public void callUSP_Random_EXAM_Result_Re_C(Map<Object, Object> map);
	
	/**
	 * 修改考试信息
	 * @param map
	 */
	public void upDateRandomExamResultCurrent(RandomExamResultCurrent randomExamResultCurrent);
	
	/**
	 * 获取考试结果
	 * @param map
	 */
	public void callUSP_Random_EXAM_RESULT_s_Temp(Map<Object, Object> map);
	
	/**
	 * 获取每一题
	 * @param map
	 */
	public void callUSP_Random_EXAM_ANSWER_Cur_G1(Map<Object, Object> map);
	
	/**
	 * 获取当前考生的考试
	 * @param map
	 */
	public void callUSP_Random_EXAM_RESULT_Cur_G(Map<Object, Object> map);
}
