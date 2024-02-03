package com.xuecheng;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: ContentApplication
 * @Description: 内容管理服务启动类
 * @data 2024/2/2 16:48
 */
@EnableSwagger2Doc
@SpringBootApplication
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
