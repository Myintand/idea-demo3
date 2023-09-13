package com.example.demo3.mapper;

import com.example.demo3.pojo.Orders;
import com.example.demo3.pojo.Temp;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

//二、mapper层
//同类： mapper层 = dao层
//作用： 对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的
@Mapper
public interface TempMapper
{
    /**
     * 查找
     */
    @Select("select * from temp where username = #{username} and storeusername=#{storeusername} and time=#{time}")
    Temp get(@Param("username") String username,@Param("storeusername") String storeusername,@Param("time") String time);

    /**
     * 删除
     */
    @Delete("DELETE FROM temp WHERE username = #{username} AND storeusername = #{storeusername} AND time=#{time}")
    int delete(@Param("username") String username,@Param("storeusername") String storeusername,@Param("time") String time);

    /**
     * 添加
     */
    @Insert("insert into temp(username,storeusername,money,time) " +
            "values(#{username},#{storeusername},#{money},#{time});")
    int add(Temp temp);


}