package com.rebs.wx.web;

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
import com.rebs.db.domain.WxLoginTime;
import com.rebs.db.domain.WxLoginTimeVo;
import com.rebs.db.service.WxLoginTimeService;

import io.swagger.annotations.ApiParam;

/**
 * @author zb
 * 记录登录次数和练习时长
 */
@RestController
@RequestMapping("/wx/loginTime")
@Validated
public class WxLoginTimeController {

    private final Log logger = LogFactory.getLog(WxLoginTimeController.class);
    
    @Autowired
    private WxLoginTimeService wxLoginTimeService;
  
    
    /**
     * 记录练习时长
     * @param wxLoginTimeVo
     * @return
     */
    @PostMapping("/setPricticeTime")
    public Object setPricticeTime(@ApiParam @RequestBody  WxLoginTimeVo wxLoginTimeVo){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        WxLoginTime wxLoginTime = wxLoginTimeService.selectByPrimaryKey(wxLoginTimeVo.getEmployeeId());
        if(wxLoginTimeVo.getBeginTime() == null || wxLoginTimeVo.getEndTime() == null){
            return ResponseUtil.fail(300, "记录练习时长，开始时间和结束时间不能为空！");
        }
        if(wxLoginTime == null) {
            return ResponseUtil.fail(300, "您还没有登录记录！");
        }
        else {
            long practiceTime = wxLoginTimeVo.getEndTime().getTime() - wxLoginTimeVo.getBeginTime().getTime();
            int pr = (int)(practiceTime/1000);
            if(wxLoginTime.getPracticeTime() == null) {
                wxLoginTime.setPracticeTime((int)pr);
            }
            else {
                wxLoginTime.setPracticeTime(wxLoginTime.getPracticeTime() + ((int)pr));
            }
            wxLoginTimeService.updateByPrimaryKeySelective(wxLoginTime);
            data.put("wxLoginTime", wxLoginTime);
            return ResponseUtil.ok(data);
        }
    }
    /**
     * 记录课件时长
     * @param wxLoginTimeVo
     * @return
     */
    @PostMapping("/setCourseWareTime")
    public Object setCourseWareTime(@ApiParam @RequestBody  WxLoginTimeVo wxLoginTimeVo){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        WxLoginTime wxLoginTime = wxLoginTimeService.selectByPrimaryKey(wxLoginTimeVo.getEmployeeId());
        if(wxLoginTimeVo.getBeginTime() == null || wxLoginTimeVo.getEndTime() == null){
            return ResponseUtil.fail(300, "记录练习时长，开始时间和结束时间不能为空！");
        }
        if(wxLoginTime == null) {
            return ResponseUtil.fail(300, "您还没有登录记录！");
        }
        else {
            long courseWareTime = wxLoginTimeVo.getEndTime().getTime() - wxLoginTimeVo.getBeginTime().getTime();
            int pr = (int)(courseWareTime/1000);
            if(wxLoginTime.getCourseWareTime() == null) {
                wxLoginTime.setCourseWareTime((int)pr);
            }
            else {
                wxLoginTime.setCourseWareTime(wxLoginTime.getCourseWareTime() + ((int)pr));
            }
            wxLoginTimeService.updateByPrimaryKeySelective(wxLoginTime);
            data.put("wxLoginTime", wxLoginTime);
            return ResponseUtil.ok(data);
        }
    }
    
    /**
     * 记录教材
     * @param wxLoginTimeVo
     * @return
     */
    @PostMapping("/setBookTime")
    public Object setBookTime(@ApiParam @RequestBody  WxLoginTimeVo wxLoginTimeVo){
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        WxLoginTime wxLoginTime = wxLoginTimeService.selectByPrimaryKey(wxLoginTimeVo.getEmployeeId());
        if(wxLoginTimeVo.getBeginTime() == null || wxLoginTimeVo.getEndTime() == null){
            return ResponseUtil.fail(300, "记录练习时长，开始时间和结束时间不能为空！");
        }
        if(wxLoginTime == null) {
            return ResponseUtil.fail(300, "您还没有登录记录！");
        }
        else {
            long bookTime = wxLoginTimeVo.getEndTime().getTime() - wxLoginTimeVo.getBeginTime().getTime();
            int pr = (int)(bookTime/1000);
            if(wxLoginTime.getBookTime() == null) {
                wxLoginTime.setBookTime((int)pr);
            }
            else {
                wxLoginTime.setBookTime(wxLoginTime.getBookTime() + ((int)pr));
            }
            wxLoginTimeService.updateByPrimaryKeySelective(wxLoginTime);
            data.put("wxLoginTime", wxLoginTime);
            return ResponseUtil.ok(data);
        }
    }
}
