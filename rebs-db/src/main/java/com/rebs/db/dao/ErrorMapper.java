package com.rebs.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rebs.db.domain.CollectionItem;
import com.rebs.db.domain.CollectionOfBook;
import com.rebs.db.domain.ErrorItem;
import com.rebs.db.domain.ErrorItemOfCategory;
import com.rebs.db.domain.Item;

public interface ErrorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RANDOM_EXAM_EMPLOYEE_ERROR
     *
     * @mbggenerated Fri Sep 28 16:36:33 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RANDOM_EXAM_EMPLOYEE_ERROR
     *
     * @mbggenerated Fri Sep 28 16:36:33 CST 2018
     */
    int insert(ErrorItem error);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RANDOM_EXAM_EMPLOYEE_ERROR
     *
     * @mbggenerated Fri Sep 28 16:36:33 CST 2018
     */
    int insertSelective(ErrorItem error);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RANDOM_EXAM_EMPLOYEE_ERROR
     *
     * @mbggenerated Fri Sep 28 16:36:33 CST 2018
     */
    ErrorItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RANDOM_EXAM_EMPLOYEE_ERROR
     *
     * @mbggenerated Fri Sep 28 16:36:33 CST 2018
     */
    int updateByPrimaryKeySelective(ErrorItem error);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RANDOM_EXAM_EMPLOYEE_ERROR
     *
     * @mbggenerated Fri Sep 28 16:36:33 CST 2018
     */
    int updateByPrimaryKey(ErrorItem error);
    
    
    List<ErrorItem> selectError(ErrorItem error);
    
    /**
	 * 按教材分类查询错题数量
	 * @param errorItem
	 * @return
	 */
	List<ErrorItemOfCategory> selectErrorCountOfBook(ErrorItem errorItem);
	
	/**
	 * 按考试分类查询错题数量
	 * @param errorItem
	 * @return
	 */
	List<ErrorItemOfCategory> selectErrorCountOfExam(ErrorItem errorItem);
	
	/**
	 * 根据employeeId，typeId，bookId ，type查询所有错误题目收藏题目详情
	 * @param map
	 * @return
	 */
	ArrayList<Item> selectErrorItemDetail(Map<Object, Object> map);

	int removeErrorItem(ErrorItem errorItem);
}