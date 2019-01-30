package com.rebs.wx.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebs.core.util.ResponseUtil;
import com.rebs.db.domain.WxStudyTask;
import com.rebs.db.service.WxStudyTaskService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/studyTask")
@Validated
public class WxStudyTaskController {

    private final Log logger = LogFactory.getLog(WxStudyTaskController.class);
    
    @Autowired
    private WxStudyTaskService wxStudyTaskService;
  
    @PostMapping("/getStudyTaskList")
    public Object getStudyTaskList(@ApiParam @RequestBody WxStudyTask wxStudyTask){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        if(wxStudyTask.getEmployeeId() == null){
        	return ResponseUtil.fail(300,"员工不能为空！");
        }
        ArrayList<WxStudyTask> WxStudyTaskList =  wxStudyTaskService.selectList(wxStudyTask);
        data.put("wxStudyTaskList", WxStudyTaskList);
        return ResponseUtil.ok(data);
    }
}
