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
import com.rebs.db.domain.WxStudyTaskDetail;
import com.rebs.db.service.WxStudyTaskDetailService;
import com.rebs.db.service.WxStudyTaskService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/studyTaskDetail")
@Validated
public class WxStudyTaskDetailController {

    private final Log logger = LogFactory.getLog(WxStudyTaskDetailController.class);
    
    @Autowired
    private WxStudyTaskDetailService wxStudyTaskDetailService;
    @Autowired
    private WxStudyTaskService wxStudyTaskService;
  
    @PostMapping("/getStudyTaskDetailList")
    public Object getStudyTaskDetailList(@ApiParam @RequestBody WxStudyTaskDetail wxStudyTaskDetail){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        if(wxStudyTaskDetail.getTaskId() == null){
        	return ResponseUtil.fail(300,"任务不能为空！");
        }
        ArrayList<WxStudyTaskDetail> wxStudyTaskDetailList =  wxStudyTaskDetailService.selectList(wxStudyTaskDetail);
        WxStudyTask wxStudyTask = new WxStudyTask();
        wxStudyTask.setTaskId(wxStudyTaskDetail.getTaskId());
        ArrayList<WxStudyTask> selectList = wxStudyTaskService.selectList(wxStudyTask);
        data.put("wxStudyTaskDetailList", wxStudyTaskDetailList);
        data.put("wxStudyTaskList", selectList.get(0));
        return ResponseUtil.ok(data);
    }
}
