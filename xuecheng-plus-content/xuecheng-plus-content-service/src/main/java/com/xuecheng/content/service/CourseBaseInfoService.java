package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

public interface CourseBaseInfoService {
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto dto);
    public CourseBaseInfoDto  createCourseBase(Long companyId, AddCourseDto dto);
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);
    public CourseBaseInfoDto updateCourseBaseInfo(Long companyId, EditCourseDto dto);
}
