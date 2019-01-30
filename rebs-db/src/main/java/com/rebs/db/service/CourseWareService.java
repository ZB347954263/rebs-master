package com.rebs.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebs.db.dao.CourseWareMapper;
import com.rebs.db.domain.CourseWare;
import com.rebs.db.domain.CourseWareType;


@Service
@Transactional
public class CourseWareService {

    @Autowired
    private CourseWareMapper courseWareMapper;
    
    /**
     * 查询课件类别
     * @param levelNum
     * @return
     */
    public List<CourseWareType> selectCourseWareTypeByLevelNum(CourseWareType courseWareType){
        return courseWareMapper.selectCourseWareTypeByLevelNum(courseWareType);
    }
    
    /**
     * 根据课件类别查询对应的课件信息
     * @param courseWareTypeId
     * @return
     */
    public List<CourseWare> selectCourseWareById(CourseWare courseWare){
        return courseWareMapper.selectCourseWareById(courseWare);
    }
    
    public CourseWare getCourseWareByCourseWareId(Integer courseWareId){
    	return courseWareMapper.getCourseWareByCourseWareId(courseWareId);
    }
}
