package com.xuecheng.content.test;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: CourseCategoryServiceTest
 * @Description:
 * @data 2024/2/4 15:57
 */
@SpringBootTest
public class CourseCategoryServiceTest {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Test
    public void queryTreeNodesTest(){
        List<CourseCategoryTreeDto> dto= courseCategoryService.queryTreeNodes("1-1");
        System.out.println(dto);
    }
}
