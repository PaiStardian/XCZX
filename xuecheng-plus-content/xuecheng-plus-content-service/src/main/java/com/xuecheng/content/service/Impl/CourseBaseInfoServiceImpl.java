package com.xuecheng.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.*;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.*;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseBaseInfoServiceImpl
 * @Description:
 * @data 2024/2/3 11:03
 */
@Slf4j
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
    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
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

    @Override
    @Transactional
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

        //向course_base表添加信息
        CourseBase courseBase = new CourseBase();
        //将dto赋值到base对象中
        BeanUtils.copyProperties(dto, courseBase);
        //设置审核状态码
        courseBase.setAuditStatus("202002");
        //设置发布状态
        courseBase.setStatus("203001");
        //设置机构id
        courseBase.setCompanyId(companyId);
        //添加时间
        courseBase.setCreateDate(LocalDateTime.now());
        //插入base表
        int insert = courseBaseMapper.insert(courseBase);
        if(insert <=0){
            throw  new RuntimeException("新增课程基本信息失败");
        }
        //向course_market表添加信息
        CourseMarket courseMarketNew = new CourseMarket();
        Long courseId = courseBase.getId();
        BeanUtils.copyProperties(dto,courseMarketNew);
        courseMarketNew.setId(courseId);
        //保存或更新课程营销信息
        int i= saveCourseMarket(courseMarketNew);
        if(i<=0){
            throw new RuntimeException("保存课程营销信息失败");
        }
        //查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(courseId);


    }

    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {

        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;



    }

    /**
     * @Author 派大星
     * @Description 更新课表
     * @Date  2024/2/5
     * @Param
     * @return
     */

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBaseInfo(Long companyId, EditCourseDto dto) {
        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            XueChengPlusException.cast("课程不存在");
        }
        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }
        //赋值
        BeanUtils.copyProperties(dto,courseBase);
        //修改时间
        courseBase.setCreateDate(LocalDateTime.now());
        //更新
        int i = courseBaseMapper.updateById(courseBase);
        if(i <=0 ){
            XueChengPlusException.cast("更新失败");
        }
        //封装营销信息时间
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        BeanUtils.copyProperties(dto,courseMarket);
        saveCourseMarket(courseMarket);
        return getCourseBaseInfo(courseId);
    }

    private int saveCourseMarket(CourseMarket courseMarketNew) {
        //收费规则
        String charge = courseMarketNew.getCharge();
        if(StringUtils.isBlank(charge)){
            throw new RuntimeException("收费规则没有选择");
        }
        //收费规则为收费
        if(charge.equals("201001")){
            if(courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue()<=0){
                XueChengPlusException.cast("课程的价格不能为空并且必须大于0");
            }
        }
        // 根据id从课程表查询信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseMarketNew.getId());
        if(courseMarket == null){
            return courseMarketMapper.insert(courseMarketNew);
        }else{
            BeanUtils.copyProperties(courseMarketNew,courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            return courseMarketMapper.updateById(courseMarket);
        }



    }

    //删除课程
    @Transactional
    @Override
    public void deleteCourse(Long companyId,Long courseId){
        //判断当前课程的状态
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (!companyId.equals(courseBase.getCompanyId()))
            XueChengPlusException.cast("只允许删除本机构的课程");
        //删除课程基本信息表
        courseBaseMapper.deleteById(courseId);
        //删除营销信息表
        courseMarketMapper.deleteById(courseId);
        //删除课程计划表
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        teachplanMapper.delete(queryWrapper);
        //课程教师表
        LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseTeacher::getCourseId,courseId);
        courseTeacherMapper.delete(wrapper);

    }
}
