package com.rebs.wx.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebs.core.util.ResponseUtil;
import com.rebs.db.domain.Book;
import com.rebs.db.domain.BookChapter;
import com.rebs.db.domain.BookVo;
import com.rebs.db.domain.Knowledge;
import com.rebs.db.service.BookChapterService;
import com.rebs.db.service.BookService;
import com.rebs.db.service.KnowledgeService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/knowledge")
@Validated
public class KnowledgeController {

    private final Log logger = LogFactory.getLog(KnowledgeController.class);
    
    @Autowired
    private KnowledgeService KnowledgeService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookChapterService bookChapterService;
    
    @GetMapping("/getKnowledge")
    public Object getKnowledgeListLevelFirst(Integer orgId){
        // 所有一级分类目录
        Knowledge knowledge = new Knowledge();
        knowledge.setLevelNum(1);
        List<Knowledge> knowledgeList1 = KnowledgeService.getKonwledgeList(knowledge);

        // 当前一级分类目录
        Knowledge currentKnowledge = knowledgeList1.get(0);

        // 当前一级分类目录对应的二级分类目录
        ArrayList<Book> bookList = bookService.callProcedure(currentKnowledge.getKnowledgeId(),orgId);
        
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("knowledgeList", knowledgeList1);
        data.put("currentKnowledge", currentKnowledge);
        data.put("bookList", bookList);
        return ResponseUtil.ok(data);
    }
    
    
    @GetMapping("/current")
    public Object getCurrent(Integer knowledgeId,Integer orgId){
        // 当前一级分类目录对应的二级分类目录
        ArrayList<Book> bookList = bookService.callProcedure(knowledgeId,orgId);
        Map<String, Object> data = new HashMap<String, Object>();
        Knowledge currentKnowledge = new Knowledge();
        currentKnowledge.setKnowledgeId(knowledgeId);
        data.put("currentKnowledge", currentKnowledge);
        data.put("bookList", bookList);
        return ResponseUtil.ok(data);
    }
    
    @PostMapping("/searchBook")
    public Object getCurrent(@ApiParam @RequestBody BookVo bookVo){
        //,Integer orgId,String bookName,String keyWords,String authors
        //根据教材的名称查询
        ArrayList<Book> bookList = bookService.callProcedureUSP_BOOK_ByCondition_Q(bookVo);
        Map<String, Object> data = new HashMap<String, Object>();
        Knowledge currentKnowledge = new Knowledge();
        currentKnowledge.setKnowledgeId(bookVo.getKnowledgeId());
        data.put("currentKnowledge", currentKnowledge);
        data.put("bookList", bookList);
        return ResponseUtil.ok(data);
    }
    
    @GetMapping("/getBook")
    public Object getBook(Integer bookId){
        // 当前一级分类目录对应的二级分类目录
        Book book = bookService.getBook(bookId);
        ArrayList<BookChapter> bookChapterList = bookChapterService.getBookChapterByBookID(bookId);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("book", book);
        data.put("bookChapterList", bookChapterList);
        return ResponseUtil.ok(data);
    }
}
