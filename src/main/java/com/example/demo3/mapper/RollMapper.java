package com.example.demo3.mapper;

import com.example.demo3.pojo.Product;
import com.example.demo3.pojo.Roll;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface RollMapper {

    /**
     * 查找所有满足条件的优惠卷ok
     */
    @Select("select * from roll where username=#{username} and storeusername=#{storeusername} and conditions<=#{conditions} and state=0")//注解式开发，自动匹配bean中的数据库
    ArrayList<Roll> getuserableroll(@Param("username") String username,@Param("storeusername") String storeusername,@Param("conditions") double conditions);


    /**
     * 根据编号获取优惠卷
     */
    @Select("select * from roll where number=#{number}")
    Roll getonerollbynumber(@Param("number") String number);


    /**
     * 添加优惠卷ok
     */
    @Insert("insert into roll(number,storeusername,storename,rate,reduce,conditions,introduce,isalluseable,begintime,endtime,nownumber,maxnumber,state) " +
            "values(#{number},#{storeusername},#{storename},#{rate},#{reduce},#{conditions},#{introduce},#{isalluseable},#{begintime},#{endtime},#{nownumber},#{maxnumber},#{state});")
    void addoneroll(Roll roll);


    /**
     * 优惠卷已兑换次数+1
     */
    @Update("update roll" +
            " set nownumber=nownumber+1" +
            " where number=#{number}")
    void nownumberaddone(@Param("number")String number);
}
