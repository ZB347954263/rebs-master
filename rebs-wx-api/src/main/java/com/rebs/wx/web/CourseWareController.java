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
import com.rebs.db.domain.CourseWare;
import com.rebs.db.domain.CourseWareType;
import com.rebs.db.service.CourseWareService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/courseWare")
@Validated
public class CourseWareController {

    private final Log logger = LogFactory.getLog(CourseWareController.class);
    
    @Autowired
    private CourseWareService courseWareService;
    
    @GetMapping("/getCourseWareType")
    public Object getCourseWareType(){
        // 所有一级分类目录
    	CourseWareType courseWareType = new CourseWareType();
    	courseWareType.setLevelNum(1);
        List<CourseWareType> courseWareTypeList = courseWareService.selectCourseWareTypeByLevelNum(courseWareType);

        // 当前一级分类目录
        CourseWareType currentCourseWareType = courseWareTypeList.get(0);

        // 当前一级分类目录对应的二级分类列表
        CourseWare courseWare = new CourseWare();
        courseWare.setCourseWareTypeId(currentCourseWareType.getCourseWareTypeId());
        List<CourseWare> courseWareList = courseWareService.selectCourseWareById(courseWare);
        
        //创建返回值
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("courseWareTypeList", courseWareTypeList);
        data.put("currentCourseWareType", currentCourseWareType);
        data.put("courseWareList", courseWareList);
        return ResponseUtil.ok(data);
    }
    
    
    @GetMapping("/getCourseWare")
    public Object getCourseWare(Integer courseWareTypeId){
        // 当前一级分类目录对应的二级分类目录
    	CourseWare courseWare = new CourseWare();
    	courseWare.setCourseWareTypeId(courseWareTypeId);
        List<CourseWare> courseWareList = courseWareService.selectCourseWareById(courseWare);
        Map<String, Object> data = new HashMap<String, Object>();
        CourseWareType currentCourseWareType = new CourseWareType();
        currentCourseWareType.setCourseWareTypeId(courseWareTypeId);
        data.put("currentCourseWareType", currentCourseWareType);
        data.put("courseWareList", courseWareList);
        return ResponseUtil.ok(data);
    }
    
    @PostMapping("/searchCourseWare")
    public Object getCurrent(@ApiParam @RequestBody CourseWare courseWare){
        //根据课件的名称查询
        List<CourseWare> courseWareList = courseWareService.selectCourseWareById(courseWare);
        Map<String, Object> data = new HashMap<String, Object>();
        CourseWareType currentCourseWareType = new CourseWareType();
        currentCourseWareType.setCourseWareTypeId(courseWare.getCourseWareTypeId());
        data.put("currentCourseWareType", currentCourseWareType);
        data.put("courseWareList", courseWareList);
        return ResponseUtil.ok(data);
    }
    
    @GetMapping("/getCourseWareByCourseWareId")
    public Object getBook(Integer courseWareId){
        // 当前一级分类目录对应的二级分类目录
    	Map<String, Object> data = new HashMap<String, Object>();
    	CourseWare courseWare = courseWareService.getCourseWareByCourseWareId(courseWareId);
    	data.put("courseWare", courseWare);
        return ResponseUtil.ok(data);
    }
}
