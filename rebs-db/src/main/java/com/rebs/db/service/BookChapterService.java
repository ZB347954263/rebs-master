package com.rebs.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.BookChapterMapper;
import com.rebs.db.domain.BookChapter;

@Service
public class BookChapterService {

    @Autowired
    private BookChapterMapper bookChapterMapper;
    
    
    public ArrayList<BookChapter> getBookChapterByBookID(int bookId)
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_book_id", bookId);
        map.put("cur_OUT", new ArrayList<Object>());
        bookChapterMapper.callProcedureUSP_BOOK_CHAPTER_Q(map);
        @SuppressWarnings("unchecked")
        ArrayList<BookChapter> cur_out = (ArrayList<BookChapter>)map.get("cur_OUT");
        return cur_out;
    }
    
    
    public ArrayList<BookChapter> callProcedureUSP_BOOK_CHAPTER_G(Integer chapterId, Integer parentId)
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_CHAPTER_id", chapterId);
        map.put("p_parent_id", parentId);
        map.put("cur_OUT", new ArrayList<BookChapter>());
        bookChapterMapper.callProcedureUSP_BOOK_CHAPTER_G(map);
        @SuppressWarnings("unchecked")
        ArrayList<BookChapter> cur_out = (ArrayList<BookChapter>)map.get("cur_OUT");
        return cur_out;
    }
    
    public BookChapter getBookChapterUrlById(Integer chapterId){
    	return bookChapterMapper.getBookChapterUrlById(chapterId);
    }
   
}
