package com.xuecheng.content.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseBaseMapperTest
 * @Description:
 * @data 2024/2/3 1:47
 */
@SpringBootTest
public class CourseBaseMapperTest {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Test
    public void testCourseBaseMapper(){
//        CourseBase courseBase = courseBaseMapper.selectById(18);
//        System.out.println(courseBase);
//        Assertions.assertNotNull(courseBase);

        QueryCourseParamsDto dto = new QueryCourseParamsDto();
        dto.setCourseName("java");
        dto.setAuditStatus("202004");
        LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(dto.getCourseName()),CourseBase::getName, dto.getCourseName());
        wrapper.eq(StringUtils.isNotBlank(dto.getAuditStatus()),CourseBase::getAuditStatus,dto.getAuditStatus());
        PageParams pageParams = new PageParams(1L, 2L);
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, wrapper);
        List<CourseBase> records = pageResult.getRecords();
        long total = pageResult.getTotal();

        PageResult<CourseBase> courseBasePageResult = new PageResult<>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
        System.out.println(courseBasePageResult);

    }

}
