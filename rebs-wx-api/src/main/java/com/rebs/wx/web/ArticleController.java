package com.rebs.wx.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebs.core.util.ResponseUtil;
import com.rebs.db.domain.Book;
import com.rebs.db.domain.BookChapter;
import com.rebs.db.domain.WxSystem;
import com.rebs.db.service.BookChapterService;
import com.rebs.db.service.BookService;
import com.rebs.db.service.WxSystemService;
import com.rebs.wx.util.GetHttpContentUtil;

@RestController
@RequestMapping("/wx/article")
@Validated
public class ArticleController {

    private final Log logger = LogFactory.getLog(ArticleController.class);
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookChapterService bookChapterService;
    
    @Autowired
    private WxSystemService wxSystemService;
    
    @GetMapping("/getArticleContent")
    public Object getArticleContent(Integer bookId,Integer bookType){
        String content;
        String fileUrl = "";
        String fileApi = "";
        try {
        	if(bookType != null && bookType == 1){
        	   // 根据bookId获取url
	        	Book book = bookService.getBookUrlById(bookId);
	        	if(book != null && StringUtils.hasText(book.getUrl())){
	        		fileUrl = book.getUrl().toString().substring(2);
	        	}
	        	List<WxSystem> selectByKeyName = wxSystemService.selectByKeyName("BOOK_URL");
	            if(selectByKeyName != null && selectByKeyName.size() > 0){
	            	fileApi = selectByKeyName.get(0).getKeyValue();
	            }
        	}
        	else if(bookType != null && bookType == 2){
        		 // 根据bookId获取url
	        	BookChapter bookChapter = bookChapterService.getBookChapterUrlById(bookId);
	        	if(bookChapter != null && StringUtils.hasText(bookChapter.getUrl())){
	        		fileUrl = bookChapter.getUrl().toString().substring(2);
	        	}
	        	List<WxSystem> selectByKeyName = wxSystemService.selectByKeyName("BOOK_URL");
	            if(selectByKeyName != null && selectByKeyName.size() > 0){
	            	fileApi = selectByKeyName.get(0).getKeyValue();
	            }
        	}
        	if(!StringUtils.hasText(fileApi) || !StringUtils.hasText(fileUrl)){
        		return ResponseUtil.fail(300, "文件不存在");
        	}
        	content = GetHttpContentUtil.getURLInfo(fileApi + fileUrl, "utf-8");
        	//创建返回值
        	Map<String, Object> data = new HashMap<String, Object>();
            data.put("content", content);
            return ResponseUtil.ok(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseUtil.fail(300, "内容获取失败");
        }
      
    }
   
}
