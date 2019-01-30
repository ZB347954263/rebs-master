package com.rebs.wx.web;

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
import com.rebs.db.service.ErrorService;
import com.rebs.db.domain.ErrorItem;
import com.rebs.db.domain.ErrorItemOfCategory;
import com.rebs.db.domain.ItemView;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/error")
@Validated
public class ErrorController {

    private final Log logger = LogFactory.getLog(ErrorController.class);
    
    @Autowired
    private ErrorService errorService;
    
    @GetMapping("/getErrorById")
    public Object getErrorById(Integer errorId){
    	
        ErrorItem error = errorService.selectByPrimaryKey(errorId);
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("error", error);
        return ResponseUtil.ok(data);
    }
    @GetMapping("/delete")
    public Object delete(Integer errorId){
    	errorService.deleteByPrimaryKey(errorId);
        return ResponseUtil.ok();
    }
    /**
     * 添加错题
     * @param error
     * @return
     */
    @PostMapping("/insert")
    public Object insert(@ApiParam @RequestBody ErrorItem error){
    	// 根据employeeId,itemId,type 判断错题是否存在
    	List<ErrorItem> selectError = errorService.selectError(error);
    	// 存在执行修改
    	if(selectError != null && selectError.size() > 0){
    		error.setErrorId(selectError.get(0).getErrorId());
    		errorService.updateByPrimaryKey(error);
        }else{
        	errorService.insert(error);
        }
    	Map<String, Object> data = new HashMap<String, Object>();
        data.put("errorId", error.getErrorId());
        return ResponseUtil.ok(data);
    }
    @PostMapping("/update")
    public Object update(@ApiParam @RequestBody ErrorItem error){
    	errorService.updateByPrimaryKey(error);
        return ResponseUtil.ok();
    }
    @PostMapping("/isError")
    public Object isError(@ApiParam @RequestBody ErrorItem error){
    	List<ErrorItem> selectError = errorService.selectError(error);
        Map<String, Object> data = new HashMap<String, Object>();
        if(selectError != null && selectError.size() > 0){
        	data.put("errorId", selectError.get(0).getErrorId());
        }
        return ResponseUtil.ok(data);
    }
    
    /**
     * 根据员工ID查询错误的题数通过书名分类
     * @param errorItem (employeeId,type)
     * @return
     */
    @PostMapping("/getErrorCountOfCategory")
    public Object getErrorCountOfCategory(@ApiParam @RequestBody ErrorItem errorItem){
    	// 查询练习分类
    	List<ErrorItemOfCategory> lianxiOfBookList = errorService.selectErrorCountOfBook(errorItem);
    	
    	// 查询考试分类
    	List<ErrorItemOfCategory> eaxmOfExamList = errorService.selectErrorCountOfExam(errorItem);
    	
        Map<String, Object> data = new HashMap<String, Object>();
        // 练习总错误数
        int lianxiTotal = 0;
        if(lianxiOfBookList != null && lianxiOfBookList.size() > 0){
        	for(ErrorItemOfCategory errorItemOfCategory: lianxiOfBookList){
        		lianxiTotal += errorItemOfCategory.getErrorCount();
        	}
        }
        
        // 考试总错误数
        int examTotal = 0;
        if(eaxmOfExamList != null && eaxmOfExamList.size() > 0){
        	for(ErrorItemOfCategory errorItemOfCategory: eaxmOfExamList){
        		examTotal += errorItemOfCategory.getErrorCount();
        	}
        }
        data.put("lianxiOfBookList", lianxiOfBookList);
        data.put("eaxmOfExamList", eaxmOfExamList);
        data.put("lianxiTotal", lianxiTotal);
        data.put("examTotal", examTotal);
        return ResponseUtil.ok(data);
    }
    
    @GetMapping("/getErrorItem")
    public Object getItemView(Integer id,Integer employeeId,Integer type){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        ItemView itemView = errorService.getItemView(id, employeeId, type);
        data.put("itemView", itemView);
        return ResponseUtil.ok(data);
    }
    
    /**
     * 移除错题
     * @param itemId 
     * @param employeeId
     * @param type
     * @return
     */
    @GetMapping("/removeErrorItem")
    public Object removeErrorItem(Integer itemId,Integer employeeId,Integer type){
    	int removeErrorItem = errorService.removeErrorItem(itemId,employeeId,type);
    	if(removeErrorItem > 0){
    		return ResponseUtil.ok();
    	}
    	return ResponseUtil.fail(300,"移除失败");
    }
}
