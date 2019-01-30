package com.rebs.db.dao;

import java.util.Map;

import com.rebs.db.domain.Book;

public interface BookMapper {

    //查询存储过程的方法,根据knowledgeId 和orgId 查询教材
    public void callProcedureUSP_BOOK_Q_KnowledgeID(Map<Object, Object> map);
    
    //查询存储过程的方法 根据knowledgeId 和orgId 以及教材名称 查询教材
    public void callProcedureUSP_BOOK_ByCondition_Q(Map<Object, Object> map);
    
    
    //查询存储过程的方法 根据bookId章节
    public void callProcedureUSP_BOOK_G(Map<Object, Object> map);
    
    
    public void callProcedureUSP_BOOK_RANGE_ORG_S(Map<Object, Object> map);
    
    public void callProcedureUSP_BOOK_RANGE_POST_S(Map<Object, Object> map);
    
    public void callProcedureUSP_BOOK_TRAIN_TYPE_S(Map<Object, Object> map);
    
    public Book getBookUrlById(Integer bookId);
    
}
