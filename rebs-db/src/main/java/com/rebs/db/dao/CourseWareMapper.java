package com.rebs.db.dao;

import java.util.List;

import com.rebs.db.domain.CourseWare;
import com.rebs.db.domain.CourseWareType;

public interface CourseWareMapper {
    
    /**
     * 查询课件类别
     * @param courseWareType
     * @return
     */
    List<CourseWareType> selectCourseWareTypeByLevelNum(CourseWareType courseWareType);

    /**
     * 根据课件类别查询对应的课件信息
     * @param courseWare
     * @return
     */
    List<CourseWare> selectCourseWareById(CourseWare courseWare);
    
    CourseWare getCourseWareByCourseWareId(Integer courseWareId);
}