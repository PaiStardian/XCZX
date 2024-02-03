package com.xuecheng.content.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :派大星
 * @PackageName: IntelliJ IDEA
 * @ClassName: MybatisPlusConfig
 * @Description: 分页插件
 * @data 2024/2/3 1:42
 */
@Configuration
@MapperScan("com.xuecheng.content.mapper")
public class MybatisPlusConfig {

    /**
     * @Author 派大星
     * @Description 定义分页拦截器
     * @Date  2024/2/3
     * @Param
     * @return
     */

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
