package com.example.demo3.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Insert("INSERT INTO collect(username, recipeName) VALUES (#{username}, #{recipeName})")
    int addCollect(@Param("username") String username, @Param("recipeName") String recipeName);

    @Select("select distinct recipeName from collect where username=#{username} ")
    public List<String> getCollect(String username);

    @Delete("delete from collect where username=#{username} and recipeName=#{recipeName}")
    int delete(@Param("username") String username, @Param("recipeName") String recipeName);
}
