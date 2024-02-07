package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: EditCourseDto
 * @Description:
 * @data 2024/2/5 16:59
 */
@Data
public class EditCourseDto extends CourseBaseInfoDto {
    @ApiModelProperty(value = "课程id", required = true)
    private Long Id;
}
