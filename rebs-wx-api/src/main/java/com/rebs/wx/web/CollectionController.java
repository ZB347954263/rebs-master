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
import com.rebs.db.domain.BookChapter;
import com.rebs.db.domain.CollectionItem;
import com.rebs.db.domain.CollectionOfBook;
import com.rebs.db.domain.ItemView;
import com.rebs.db.service.CollectionService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/collection")
@Validated
public class CollectionController {

    private final Log logger = LogFactory.getLog(CollectionController.class);
    
    @Autowired
    private CollectionService collectionService;
    
    @GetMapping("/getCollectionById")
    public Object getCollectionById(Integer collectionId){
    	
        CollectionItem collection = collectionService.selectByPrimaryKey(collectionId);
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("collection", collection);
        return ResponseUtil.ok(data);
    }
    @GetMapping("/delete")
    public Object delete(Integer collectionId){
    	collectionService.deleteByPrimaryKey(collectionId);
        return ResponseUtil.ok();
    }
    /**
     * 取消收藏
     * @param itemId
     * @param employeeId
     * @return
     */
    @GetMapping("/cancelConllect")
    public Object cancelConllect(Integer itemId,Integer employeeId,Integer type){
    	collectionService.cancelConllect(itemId,employeeId,type);
        return ResponseUtil.ok();
    }
    /**
     * 收藏
     * @param collection (itemId,employeeId,type)
     * @return
     */
    @PostMapping("/insert")
    public Object insert(@ApiParam @RequestBody CollectionItem collection){
    	List<CollectionItem> selectCollection = collectionService.selectCollection(collection);
    	if(selectCollection != null && selectCollection.size() > 0){
    		collection.setCollectionId(selectCollection.get(0).getCollectionId());
        }else{
        	collectionService.insert(collection);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("collectionId", collection.getCollectionId());
        return ResponseUtil.ok(data);
    }
    @PostMapping("/update")
    public Object update(@ApiParam @RequestBody CollectionItem collection){
    	collectionService.updateByPrimaryKey(collection);
        return ResponseUtil.ok();
    }
    /**
     * 判断是否收藏
     * @param collection (itemId,employeeId,type)
     * @return
     */
    @PostMapping("/isCollection")
    public Object isCollection(@ApiParam @RequestBody CollectionItem collection){
    	List<CollectionItem> selectCollection = collectionService.selectCollection(collection);
        Map<String, Object> data = new HashMap<String, Object>();
        if(selectCollection != null && selectCollection.size() > 0){
        	data.put("collectionId", selectCollection.get(0).getCollectionId());
        }
        return ResponseUtil.ok(data);
    }
    
    /**
     * 根据员工ID查询收藏的题数通过书名分类
     * @param collection (employeeId)
     * @return
     */
    @PostMapping("/getCollectionCountOfBook")
    public Object getCollectionCountOfBook(@ApiParam @RequestBody CollectionItem collection){
    	List<CollectionOfBook> collectionOfBookList = collectionService.selectCollectionCountOfBook(collection);
        Map<String, Object> data = new HashMap<String, Object>();
        // 总的收藏数
        int total = 0;
        if(collectionOfBookList != null && collectionOfBookList.size() > 0){
        	for(CollectionOfBook collectionOfBook: collectionOfBookList){
        		total += collectionOfBook.getCollectionCount();
        	}
        }
        data.put("total", total);
        data.put("collectionOfBookList", collectionOfBookList);
        return ResponseUtil.ok(data);
    }
    
    @GetMapping("/getCollectionItem")
    public Object getItemView(Integer bookId,Integer employeeId){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        ItemView itemView = collectionService.getItemView(bookId, employeeId);
        data.put("itemView", itemView);
        return ResponseUtil.ok(data);
    }
}
