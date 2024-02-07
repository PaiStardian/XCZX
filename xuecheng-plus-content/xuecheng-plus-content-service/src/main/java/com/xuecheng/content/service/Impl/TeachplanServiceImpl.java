package com.xuecheng.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description 课程计划service接口实现类
 * @author Mr.M
 * @date 2022/9/9 11:14
 * @version 1.0
 */
 @Service
public class TeachplanServiceImpl implements TeachplanService {

  @Autowired
 TeachplanMapper teachplanMapper;
 @Override
 public List<TeachplanDto> findTeachplanTree(long courseId) {
  return teachplanMapper.selectTreeNodes(courseId);
 }

 @Override
 public void saveTeachplan(SaveTeachplanDto teachplanDto) {
  //根据课程计划id判断是否是新增还是修改
     Long id = teachplanDto.getId();
     if(id == null){
        //新增
      Teachplan teachplan = new Teachplan();
      BeanUtils.copyProperties(teachplanDto, teachplan);
      //确定排序条件 select count(1) from teachplan where course_id = ? and parent_id =?
      Long courseId = teachplanDto.getCourseId();
      Long parentId = teachplanDto.getParentid();
      //取出同父同级别的课程计划数量
      int count = getTeachplanCount(courseId,parentId);
      teachplan.setOrderby(count);
      teachplanMapper.insert(teachplan);
     } else {
      //修改
      Teachplan teachplan = teachplanMapper.selectById(id);
      BeanUtils.copyProperties(teachplanDto, teachplan);
      teachplanMapper.updateById(teachplan);
     }

 }

    @Override
    public void deletePlan(Long id) {

        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,id);
        Integer i = teachplanMapper.selectCount(queryWrapper);
        if(i == 0){
            teachplanMapper.deleteById(id);
        }else {
            XueChengPlusException.cast("有小章节，不能删除");
        }

    }
    @Transactional
    @Override
    public void movedown(Long id) {
            //得到要下移的对象
            Teachplan teachplanNew = teachplanMapper.selectById(id);
            //查找父节点
            Long parentid = teachplanNew.getParentid();
            //判断条件 节点的数量  如果当前节点的ord = 节点的数量 则不能下移
            LambdaQueryWrapper<Teachplan> queryWrapperNew = new LambdaQueryWrapper<>();
            queryWrapperNew.eq(Teachplan::getParentid, parentid).eq(Teachplan::getCourseId,teachplanNew.getCourseId());
            Integer i = teachplanMapper.selectCount(queryWrapperNew);
            if(teachplanNew.getOrderby() == i){
                XueChengPlusException.cast("不能下移，你已经是最后一个了");
            }
            //查找当前对象的ordby+1的对象
            LambdaQueryWrapper<Teachplan> queryWrapperOld = new LambdaQueryWrapper<>();
            queryWrapperOld.eq(Teachplan::getOrderby,teachplanNew.getOrderby()+1).eq(Teachplan::getParentid, parentid).eq(Teachplan::getCourseId,teachplanNew.getCourseId());
            Teachplan teachplanOld = teachplanMapper.selectOne(queryWrapperOld);
            //将当前对象的ordby+1           1       2
            teachplanNew.setOrderby(teachplanNew.getOrderby()+1);
            //将以前的对象的ordby-1          2        1
            teachplanOld.setOrderby(teachplanNew.getOrderby()-1);
            teachplanMapper.updateById(teachplanNew);
            teachplanMapper.updateById(teachplanOld);
    }
    @Transactional
    @Override
    public void moveup(Long id) {
        //得到要上移的对象
        Teachplan teachplanNew = teachplanMapper.selectById(id);
        //查找父节点
        Long parentid = teachplanNew.getParentid();
        //判断条件 节点的数量  如果当前节点的ord = 节点的数量 则不能下移
        LambdaQueryWrapper<Teachplan> queryWrapperNew = new LambdaQueryWrapper<>();
        queryWrapperNew.eq(Teachplan::getParentid, parentid).eq(Teachplan::getCourseId,teachplanNew.getCourseId());
        Integer i = teachplanMapper.selectCount(queryWrapperNew);
        if(teachplanNew.getOrderby() == 1){
            XueChengPlusException.cast("不能上移，你已经是第一个了");
        }
        //查找当前对象的ordby-1的对象
        LambdaQueryWrapper<Teachplan> queryWrapperOld = new LambdaQueryWrapper<>();
        queryWrapperOld.eq(Teachplan::getOrderby,teachplanNew.getOrderby()-1).eq(Teachplan::getParentid, parentid).eq(Teachplan::getCourseId,teachplanNew.getCourseId());
        Teachplan teachplanOld = teachplanMapper.selectOne(queryWrapperOld);
        //将当前对象的ordby+1           1       2
        teachplanNew.setOrderby(teachplanNew.getOrderby()-1);
        //将以前的对象的ordby-1          2        1
        teachplanOld.setOrderby(teachplanNew.getOrderby()+1);
        teachplanMapper.updateById(teachplanNew);
        teachplanMapper.updateById(teachplanOld);

    }

    private int getTeachplanCount(Long courseId, Long parentId) {

      LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Teachplan::getCourseId,courseId).eq(Teachplan::getParentid,parentId);
      Integer i = teachplanMapper.selectCount(queryWrapper);
      return i+1;
 }
}