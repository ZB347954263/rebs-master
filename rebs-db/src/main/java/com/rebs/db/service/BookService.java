package com.rebs.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.BookMapper;
import com.rebs.db.domain.Book;
import com.rebs.db.domain.BookRangeOrg;
import com.rebs.db.domain.BookRangePost;
import com.rebs.db.domain.BookTrainType;
import com.rebs.db.domain.BookVo;
import com.rebs.db.domain.Knowledge;

@Service
public class BookService {

    @Autowired
    private BookMapper BookMapper;
    
    @Autowired
    private KnowledgeService knowledgeService;
    
    
    public ArrayList<Book> callProcedure(Integer knowledgeId,Integer orgId) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_knowledge_id", knowledgeId);
        map.put("p_org_id", orgId);
        map.put("cur_out", new ArrayList<Book>());
        BookMapper.callProcedureUSP_BOOK_Q_KnowledgeID(map);
        @SuppressWarnings("unchecked")
        ArrayList<Book> cur_out = (ArrayList<Book>)map.get("cur_out");
        return cur_out;
    }
    
    public ArrayList<Book> callProcedureUSP_BOOK_ByCondition_Q(BookVo bookVo) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_knowledge_id", bookVo.getKnowledgeId());
        map.put("p_org_id", bookVo.getOrgId());
        map.put("p_book_Name", bookVo.getBookName());
        map.put("p_keyWords", bookVo.getKeyWords());
        map.put("p_authors", bookVo.getAuthors());
        map.put("cur_out", new ArrayList<Book>());
        BookMapper.callProcedureUSP_BOOK_ByCondition_Q(map);
        @SuppressWarnings("unchecked")
        ArrayList<Book> cur_out = (ArrayList<Book>)map.get("cur_out");
        return cur_out;
    }
    
    public ArrayList<BookRangeOrg> callProcedureUSP_BOOK_RANGE_ORG_S(Integer bookId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_book_id", bookId);
        map.put("cur_OUT", new ArrayList<Object>());
        BookMapper.callProcedureUSP_BOOK_RANGE_ORG_S(map);
        @SuppressWarnings("unchecked")
        ArrayList<BookRangeOrg> cur_out = (ArrayList<BookRangeOrg>)map.get("cur_OUT");
        return cur_out;
    }
    
    public  ArrayList<BookRangePost> callProcedureUSP_BOOK_RANGE_POST_S(Integer bookId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_book_id", bookId);
        map.put("cur_OUT", new ArrayList<BookRangePost>());
        BookMapper.callProcedureUSP_BOOK_RANGE_POST_S(map);
        @SuppressWarnings("unchecked")
        ArrayList<BookRangePost> cur_out = (ArrayList<BookRangePost>)map.get("cur_OUT");
        return cur_out;
    }
    
    public ArrayList<BookTrainType> callProcedureUSP_BOOK_TRAIN_TYPE_S(Integer bookId){
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_book_id", bookId);
        map.put("cur_OUT", new ArrayList<BookTrainType>());
        BookMapper.callProcedureUSP_BOOK_TRAIN_TYPE_S(map);
        @SuppressWarnings("unchecked")
        ArrayList<BookTrainType> cur_out = (ArrayList<BookTrainType>)map.get("cur_OUT");
        return cur_out;
    }
    
    
    public ArrayList<Object> callProcedureUSP_BOOK_G(Integer bookId) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("p_book_id", bookId);
        map.put("p_org_id", 0);
        map.put("cur_OUT", new ArrayList<Object>());
        BookMapper.callProcedureUSP_BOOK_G(map);
        @SuppressWarnings("unchecked")
        ArrayList<Object> cur_out = (ArrayList<Object>)map.get("cur_OUT");
        return cur_out;
    }
    
    
    public Book getBook(Integer bookId){
        Book book = new Book();
        ArrayList<Object> orgidAL = new ArrayList<Object>();
        ArrayList<Object> postidAL = new ArrayList<Object>();
        ArrayList<Object> trainTypeidAL = new ArrayList<Object>();
        String strTrainTypeNames = "";
        
        ArrayList<Object> dataReader = this.callProcedureUSP_BOOK_G(bookId);
        for (Object object : dataReader) {
            book = (Book)object;
        }
        ArrayList<Knowledge> knowledgeList = this.knowledgeService.callProcedureUSP_KNOWLEDGE_G(book.getKnowledgeId());
        if(knowledgeList != null && knowledgeList.size()>0) {
            book.setKnowledgeName(knowledgeList.get(0).getKnowledgeName());
        }
        ArrayList<BookRangeOrg> dataReader1 = this.callProcedureUSP_BOOK_RANGE_ORG_S(bookId);
        for (BookRangeOrg bookRangeOrg : dataReader1) {
            orgidAL.add(bookRangeOrg.getOrgId().toString());
        }
        ArrayList<BookRangePost> dataReader2 = this.callProcedureUSP_BOOK_RANGE_POST_S(bookId);
        for (BookRangePost bookRangePost : dataReader2) {
            postidAL.add(bookRangePost.getPostId().toString());
        }
        ArrayList<BookTrainType> dataReader3 = this.callProcedureUSP_BOOK_TRAIN_TYPE_S(bookId);
        for (BookTrainType bookTrainType : dataReader3) {
            trainTypeidAL.add(bookTrainType.getTrainTypeId().toString());
            strTrainTypeNames += bookTrainType.getTypeName();
        }
        book.setOrgidAL(orgidAL);
        book.setPostidAL(postidAL);
        book.setTrainTypeidAL(trainTypeidAL);
        book.setStrTrainTypeNames(strTrainTypeNames);
        
        return book;
    }
    
    private String GetTrainTypeNames(String strName, int nID)
    {
        String strTrainTypeName = "";
//        if (nID != 0)
//        {
//            TrainTypeDAL trainTypeDAL = new TrainTypeDAL();
//            TrainType trainType = trainTypeDAL.GetTrainTypeInfo(nID);
//
//            if (trainType.ParentID != 0)
//            {
//                strTrainTypeName = GetTrainTypeNames("/" + trainType.TypeName, trainType.ParentID) + strName;
//            }
//            else
//            {
//                strTrainTypeName = trainType.TypeName + strName;
//            }
//        }
//        else
//        {
//            strTrainTypeName = strName.replace("/", "");
//        }
        return strTrainTypeName;
    }
    
    public Book getBookUrlById(Integer bookId) {
        return BookMapper.getBookUrlById(bookId);
    }
   
}
