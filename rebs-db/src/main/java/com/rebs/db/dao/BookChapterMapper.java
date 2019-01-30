package com.rebs.db.dao;

import java.util.Map;

import com.rebs.db.domain.BookChapter;

public interface BookChapterMapper {

    
    public void callProcedureUSP_BOOK_CHAPTER_Q(Map<Object, Object> map);
    
    public void callProcedureUSP_BOOK_CHAPTER_G(Map<Object, Object> map);
    
    public BookChapter getBookChapterUrlById(Integer chapterId);
    
}
