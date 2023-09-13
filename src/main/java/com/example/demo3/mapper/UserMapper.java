package com.example.demo3.mapper;

import com.example.demo3.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

//二、mapper层
//同类： mapper层 = dao层
//作用： 对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的
@Mapper
public interface UserMapper
{
    /**
     * 登陆：根据用户名和密码得到一个用户数据okk
     */
    @Select("select * from message where username = #{username} and password = #{password}")//注解式开发，自动匹配bean中的数据库
    User getoneuser(@Param("username") String username, @Param("password") String password);//传入参数，返回sql查询后的数据

    /**
     * 查找所有用户okk
     */
    @Select("select * from message")//注解式开发，自动匹配bean中的数据库
    ArrayList<User> getUserList();//传入参数，返回sql查询后的数据

    /**
     * 忘记密码：根据用户名查找okk
     */
    @Select("select * from message where username = #{username}")//注解式开发，自动匹配bean中的数据库
    User getoneuserbyusername(@Param("username") String username);//传入参数，返回sql查询后的数据

    /**
     * 注册：根据用户名查找okk
     */
    @Insert("insert into message(username,password,sex,education,email,introduce,problem,answer,headpicture,tel,address)" +
            "values(#{username},#{password},#{sex},#{education},#{email},#{introduce},#{problem},#{answer},#{headpicture},#{tel},#{address})")//注解式开发，自动匹配bean中的数据库
    int add(User user);//传入参数，返回sql查询后的数据

    /**
     * 修改：根据用户名查找okk
     */
    @Update("update message set username=#{username},password=#{password}" +
            ",sex=#{sex},education=#{education},email=#{email},introduce=#{introduce}" +
            ",problem=#{problem},answer=#{answer},headpicture=#{headpicture},tel=#{tel},address=#{address}" +
            " where username = #{username}")//注解式开发，自动匹配bean中的数据库
    int modify(User user);//传入参数，返回sql查询后的数据

    /**
     * 修改密码：根据用户名查找okk
     */
    @Update("update message set password=#{password} where username=#{username}")
    int modifypassword(@Param("password") String password,@Param("username") String username);

    /**
     * 更新余额-
     */
    @Update("update message set mymoney=mymoney-#{price} where username=#{username}")
    int reducemymoney(@Param("username")String username,@Param("price") double price);

    /**
     * 更新余额+
     */
    @Update("update message set mymoney=mymoney+#{price} where username=#{username}")
    int addmymoney(@Param("username")String username,@Param("price") double price);


    /**
     * 充值
     */
    @Update("update message set mymoney=mymoney+#{number} where username=#{username}")
    int recharge(@Param("username")String username,@Param("number") double number);
}