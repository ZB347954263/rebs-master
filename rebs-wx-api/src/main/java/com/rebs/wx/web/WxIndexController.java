package com.rebs.wx.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebs.core.util.ResponseUtil;
import com.rebs.db.domain.Exam;
import com.rebs.db.domain.WxIndex;
import com.rebs.db.domain.WxLoginTime;
import com.rebs.db.domain.WxStudyTask;
import com.rebs.db.service.ExamService;
import com.rebs.db.service.WxIndexService;
import com.rebs.db.service.WxLoginTimeService;
import com.rebs.db.service.WxStudyTaskService;

@RestController
@RequestMapping("/wx/index")
public class WxIndexController {
    private final Log logger = LogFactory.getLog(WxIndexController.class);

    @Autowired
    private WxIndexService wxIndexService;
    @Autowired
    private WxLoginTimeService wxLoginTimeService;
    @Autowired
    private ExamService examService;
    @Autowired
    private WxStudyTaskService wxStudyTaskService;
    
    @RequestMapping("/index")
    public Object index(){
        return ResponseUtil.ok("hello world, this is wx service");
    }

    @RequestMapping("/getIndexData")
    public Object getIndexData(Integer employeeId,Integer orgId,Integer serverNo){
    	WxIndex indexData = wxIndexService.selectIndexData(employeeId);
    	WxLoginTime selectByPrimaryKey = wxLoginTimeService.selectByPrimaryKey(employeeId);
    	indexData.setLoginTime(selectByPrimaryKey != null && selectByPrimaryKey.getLoginTime() != null ? selectByPrimaryKey.getLoginTime():0);
    	indexData.setPracticeTime(selectByPrimaryKey.getPracticeTime() == null?0:selectByPrimaryKey.getPracticeTime());
    	indexData.setCourseWareTime(selectByPrimaryKey.getCourseWareTime()==null?0:selectByPrimaryKey.getCourseWareTime());
    	indexData.setBookTime(selectByPrimaryKey.getBookTime()==null?0:selectByPrimaryKey.getBookTime());
    	
    	//获取考试数量
    	ArrayList<Exam> examList = examService.callProcedureWX_USP_Exam_By_user(employeeId, orgId, serverNo);
    	indexData.setExamCount(examList.size());
    	
    	//获取任务数量
    	WxStudyTask wxStudyTask = new WxStudyTask();
    	wxStudyTask.setEmployeeId(employeeId);
    	ArrayList<WxStudyTask> WxStudyTaskList =  wxStudyTaskService.selectList(wxStudyTask);
    	indexData.setTaskCount(WxStudyTaskList.size());
    	
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put("indexData", indexData);
        return ResponseUtil.ok(data);
    }
}