package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.AddTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseTeacherController
 * @Description:
 * @data 2024/2/7 21:24
 */
@RestController
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> courseTeacherList(@PathVariable Long courseId){
        return courseTeacherService.courseTeacherList(courseId);

    }
    @PostMapping("courseTeacher")
    public void addTeacher(@RequestBody AddTeacherDto dto){
        courseTeacherService.addOrUpdateTeacher(dto);

    }
    @DeleteMapping("courseTeacher/course/{courseId}/{id}")
    public void deleteTeacher(@PathVariable Long courseId, @PathVariable Long id){
        courseTeacherService.deleteTeacher(courseId, id);

    }
}
