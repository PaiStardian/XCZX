package com.xuecheng.content.test;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseBaseMapperTest
 * @Description:
 * @data 2024/2/3 1:47
 */
@SpringBootTest
public class CourseBaseServiceTest {
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;
    @Test
    public void testCourseBaseMapper(){

        QueryCourseParamsDto dto = new QueryCourseParamsDto();
        dto.setCourseName("java");
        dto.setAuditStatus("202004");
        PageParams pageParams = new PageParams(1L, 2L);
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, dto);
        System.out.println(courseBasePageResult);


    }

}
