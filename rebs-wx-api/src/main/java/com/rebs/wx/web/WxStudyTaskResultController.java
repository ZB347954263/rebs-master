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
import com.rebs.db.domain.WxStudyTaskResult;
import com.rebs.db.service.WxStudyTaskResultService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/studyTaskResult")
@Validated
public class WxStudyTaskResultController {

    private final Log logger = LogFactory.getLog(WxStudyTaskResultController.class);
    
    @Autowired
    private WxStudyTaskResultService wxStudyTaskResultService;

    @PostMapping("/insert")
    public Object insert(@ApiParam @RequestBody WxStudyTaskResult wxStudyTaskResult){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        if(wxStudyTaskResult.getEmployeeId() == null){
        	return ResponseUtil.fail(300,"员工不能为空！");
        }
        if(wxStudyTaskResult.getTaskId() == null){
        	return ResponseUtil.fail(300,"任务不能为空！");
        }
        if(wxStudyTaskResult.getTaskDetailId() == null){
        	return ResponseUtil.fail(300,"任务详情不能为空！");
        }
        if(wxStudyTaskResult.getBeginTime() == null || wxStudyTaskResult.getEndTime() == null){
        	return ResponseUtil.fail(300,"时间不能为空！");
        }
        long time = wxStudyTaskResult.getEndTime().getTime() - wxStudyTaskResult.getBeginTime().getTime();
        Integer hasTime = (int)(time/1000/60);
        wxStudyTaskResult.setHasTime(hasTime);
        ArrayList<WxStudyTaskResult> selectList = wxStudyTaskResultService.selectList(wxStudyTaskResult);
        if(selectList != null && selectList.size() > 0){
        	//执行更新
        	selectList.get(0).setHasTime(selectList.get(0).getHasTime() + wxStudyTaskResult.getHasTime());
        	wxStudyTaskResultService.update(selectList.get(0));
        }else{
        	wxStudyTaskResultService.insert(wxStudyTaskResult);
        }
        return ResponseUtil.ok(data);
    }
}
