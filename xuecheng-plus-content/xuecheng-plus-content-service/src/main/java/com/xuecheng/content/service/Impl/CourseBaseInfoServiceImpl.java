package com.xuecheng.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseBaseInfoServiceImpl
 * @Description:
 * @data 2024/2/3 11:03
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    /**
     * @Author 派大星
     * @Description 课程信息查询
     * @Date  2024/2/3
     * @Param
     * @return
     */
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto dto) {

        LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
        //模糊查询课程名称
        wrapper.like(StringUtils.isNotBlank(dto.getCourseName()),CourseBase::getName, dto.getCourseName());
        //查询审核状态
        wrapper.eq(StringUtils.isNotBlank(dto.getAuditStatus()),CourseBase::getAuditStatus,dto.getAuditStatus());
        //查询分布状态
        wrapper.eq(StringUtils.isNotBlank(dto.getPublishStatus()),CourseBase::getStatus,dto.getPublishStatus());
        //分页查询
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, wrapper);
        //查询记录数
        List<CourseBase> records = pageResult.getRecords();
        //查询总数量
        long total = pageResult.getTotal();
        //返回响应类型
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }
}
