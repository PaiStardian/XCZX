package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.AddTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseTeacherService
 * @Description:
 * @data 2024/2/7 21:23
 */
public interface CourseTeacherService {
    public List<CourseTeacher> courseTeacherList(Long courseId);
    public void addOrUpdateTeacher(AddTeacherDto dto);
    public void deleteTeacher( Long courseId,  Long id);
}
