package com.rebs.wx.web;

import java.math.BigDecimal;
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
import com.rebs.db.domain.ErrorItem;
import com.rebs.db.domain.Item;
import com.rebs.db.domain.ItemView;
import com.rebs.db.domain.LianXiVo;
import com.rebs.db.service.BookChapterService;
import com.rebs.db.service.ErrorService;
import com.rebs.db.service.ItemService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/item")
@Validated
public class ItemController {

    private final Log logger = LogFactory.getLog(ItemController.class);
    
    @Autowired
    private ItemService itemService;
  
    @Autowired
    private BookChapterService bookChapterService;
    
    @Autowired
    private ErrorService errorService;
    
    @GetMapping("/getItemView")
    public Object getItemView(Integer bookId,Integer chapterId){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        ArrayList<BookChapter> bookChapterList =  bookChapterService.callProcedureUSP_BOOK_CHAPTER_G(chapterId, 0);
        BookChapter bookChapter = null;
        if(bookChapterList != null && bookChapterList.size()>0) {
            bookChapter = bookChapterList.get(0);
        }
        if(bookChapter != null && bookChapter.getIsCannotSeeAnswer() != null && bookChapter.getIsCannotSeeAnswer()) {
            return ResponseUtil.fail(300, "本章节练习题已被屏蔽！");
        }
        else {
            ItemView itemView = itemService.getItemView(bookId, chapterId);
            data.put("itemView", itemView);
            return ResponseUtil.ok(data);
        }
    }
    
    
    
    /**
     * 提交练习
     * @param bookVo
     * @return
     */
    @PostMapping("/commitLianXi")
    public Object commitLianXi(@ApiParam @RequestBody LianXiVo lianXiVo){
        //获取所有的题目
        ArrayList<Item> allLists = lianXiVo.getAllLists();
        // 错题数
        Integer errorItemCount = 0;
        // 正确题数
        Integer rightItemCount = 0;
        //总题数
        Integer totalItem = allLists.size();
        //正确率
        BigDecimal rightRate = BigDecimal.ZERO;

        for(int i=0;i<allLists.size();i++){
            Item item = allLists.get(i);
            //保存错题
            if(item.getIsAnswer() != null && item.getIsAnswer().equals(2)) {
                errorItemCount +=1;
                ErrorItem errorItem = new ErrorItem();
                errorItem.setAnswer(item.getAnswered());
                errorItem.setItemId(item.getItemId());
                errorItem.setType(lianXiVo.getType());
                errorItem.setEmployeeId(lianXiVo.getEmployeeId());
//                errorService.insert(errorItem);
                
               // 根据employeeId,itemId,type 判断错题是否存在
                List<ErrorItem> selectError = errorService.selectError(errorItem);
                // 存在执行修改
                if(selectError != null && selectError.size() > 0){
                    errorItem.setErrorId(selectError.get(0).getErrorId());
                    errorService.updateByPrimaryKey(errorItem);
                }else{
                    errorService.insert(errorItem);
                }
            }
            //正确
            if(item.getIsAnswer() != null && item.getIsAnswer().equals(1)) {
                rightItemCount +=1;
            }
        }
        rightRate = BigDecimal.valueOf(rightItemCount).divide((totalItem == 0 ? BigDecimal.ONE : BigDecimal.valueOf(totalItem)), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
        lianXiVo.setErrorItemCount(errorItemCount);
        lianXiVo.setRightItemCount(rightItemCount);
        lianXiVo.setRightRate(rightRate);
        lianXiVo.setTotalItem(totalItem);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("lianXiVo", lianXiVo);
        return ResponseUtil.ok(data);
    }
}
