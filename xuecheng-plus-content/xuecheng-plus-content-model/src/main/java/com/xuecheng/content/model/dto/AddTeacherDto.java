package com.xuecheng.content.model.dto;

import lombok.Data;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: AddTeacherDto
 * @Description:
 * @data 2024/2/7 22:13
 */
@Data
public class AddTeacherDto {
    /**
     * 教师标识
     */
    private String teacherName;

    /**
     * 教师职位
     */
    private String position;

    /**
     * 教师简介
     */
    private String introduction;

    /**
     * 课程标识
     */
    private Long courseId;

}
