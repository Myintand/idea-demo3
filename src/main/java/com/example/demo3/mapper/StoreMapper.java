package com.example.demo3.mapper;

import com.example.demo3.pojo.Store;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

//二、mapper层
//同类： mapper层 = dao层
//作用： 对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的
@Mapper
public interface StoreMapper
{
    /**
     * 查找所有商家ok
     */
    @Select("select * from store")//注解式开发，自动匹配bean中的数据库
    ArrayList<Store> getStoreList();//传入参数，返回sql查询后的数据


    /**
     * 注册商家
     */
    @Insert("insert into store(username,storename,beginning,introduce,picture,storelocation)" +
            "values(#{username},#{storename},#{beginning},#{introduce},#{picture},#{storelocation})")//注解式开发，自动匹配bean中的数据库
    int add(Store store);//传入参数，返回sql查询后的数据



    /**
     * 查找单个用户ok
     */
    @Select("select * from store where username=#{username}")//注解式开发，自动匹配bean中的数据库
    Store getonestore(@Param("username") String username);//传入参数，返回sql查询后的数据

    /**
     * 修改：根据用户名查找ok
     */
    @Update("update store set username=#{username},storename=#{storename},beginning=#{beginning},introduce=#{introduce},picture=#{picture},storelocation=#{storelocation} where username = #{username}")//注解式开发，自动匹配bean中的数据库
    int modify(Store store);//传入参数，返回sql查询后的数据

    /**
     * 查找部分商家ok
     */
    @Select("SELECT * FROM store WHERE storename LIKE CONCAT('%', #{keyword}, '%')")
    ArrayList<Store> findStoreList(@Param("keyword") String keyword);

    /**
     * 店铺评分增加，销量增加1
     */
    @Update("update store set score=score+#{score},number=number+1 where username=#{username}")
    int addscore(@Param("username")String username,@Param("score") double score);

    /**
     * 店铺销量增加1
     */
    @Update("update store set salenumber=salenumber+1 where username=#{username}")
    int salenumberaddone(@Param("username") String username);

}