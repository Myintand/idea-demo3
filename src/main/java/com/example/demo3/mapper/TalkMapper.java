package com.example.demo3.mapper;

import com.example.demo3.pojo.Talk;
import com.example.demo3.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface TalkMapper {

    /**
     * 查找我和商家的所有对话
     */
    @Select("select * from talk" +
            " where (username=#{username} and storeusername=#{storeusername} and storename=#{storename}) " +
            "or" +
            " (username=#{storeusername} and storeusername=#{username} and storename=#{storename})")//注解式开发，自动匹配bean中的数据库
    ArrayList<Talk> getalltalkList(@Param("username") String username,@Param("storeusername") String storeusername,@Param("storename") String storename);//传入参数，返回sql查询后的数据

    /**
     * 插入新数据
     */
    @Insert("insert into talk(username,storeusername,storename,flag,talking,picture)" +
            "values(#{username},#{storeusername},#{storename},#{flag},#{talking},#{picture})")//注解式开发，自动匹配bean中的数据库
    void addtalk(Talk talk);//传入参数，返回sql查询后的数据


    /**
     * 商家查找有过对话的用户
     */
    @Select("select distinct * from message where username in (select username from talk where storeusername=#{storeusername})")
    ArrayList<User> findtalk(@Param("storeusername")String storeusername);
}
