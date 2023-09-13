package com.example.demo3.config;



import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

//数据源实现
@Configuration
@ComponentScan({"com.example.demo3.service","com.example.demo3.controller"})
//自动扫描包，让指定包下的@RequestMapping注解生效,加入到iIOC容器，由IOC容器统一管理
@MapperScan("com.example.demo3.mapper")
//自动扫描包
public class WebConfiguration
{
        @Bean(name = "multipartResolver")
        public CommonsMultipartResolver multipartResolver()//处理文件上传
        {
                CommonsMultipartResolver resolver = new CommonsMultipartResolver();
                // 设置默认编码
                resolver.setDefaultEncoding("UTF-8");
                // 设置最大上传文件大小（单位为字节）
                resolver.setMaxUploadSize(10485760);
                // 设置上传文件总大小100m
                resolver.setMaxUploadSizePerFile(10485760);
                return resolver;
        }

        @Bean
        public DataSource dataSource()//将其存入容器
        {
                HikariDataSource dataSource = new HikariDataSource();
                dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/users");
                dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                dataSource.setUsername("root");
                dataSource.setPassword("zqy287189");
                return dataSource;
        }

        @Bean
        public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource)//取出容器内的数据作为参数
        {
                SqlSessionFactoryBean bean = new SqlSessionFactoryBean();//在bean中new参数
                bean.setDataSource(dataSource);
                return bean;
        }

}
