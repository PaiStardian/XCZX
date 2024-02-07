package com.xuecheng.content.service.Impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseCategoryServiceImpl
 * @Description:
 * @data 2024/2/4 15:36
 */
@Service
@Slf4j
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> dto = courseCategoryMapper.selectTreeNodes(id);
        // 将list转换成map以备使用
        Map<String, CourseCategoryTreeDto> map = dto.stream().filter(item -> !id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        // 最终返回list
        List<CourseCategoryTreeDto> categoryTreeDto = new ArrayList<>();
        // 依次遍历每个元素 排除根节点
        dto.stream().filter(item -> !id.equals(item.getId())).forEach(item -> {
            if(item.getParentid().equals(id)){
                categoryTreeDto.add(item);
            }
            // 找到当前父节点
            CourseCategoryTreeDto courseCategoryTreeDto = map.get(item.getParentid());
            if(courseCategoryTreeDto !=null){
                if (courseCategoryTreeDto.getChildrenTreeNodes() == null){
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);

            }
        });

        return categoryTreeDto;
    }
}
