package com.xuecheng.content.test;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseCategoryMapperTest
 * @Description:
 * @data 2024/2/4 11:37
 */
@SpringBootTest
public class CourseCategoryMapperTest {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Test
    public void testCourseCategoryMapper(){
       List<CourseCategoryTreeDto> dtos=  courseCategoryMapper.selectTreeNodes("1");
        System.out.println(dtos);



    }
}
