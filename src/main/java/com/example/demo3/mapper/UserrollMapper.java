package com.example.demo3.mapper;

import com.example.demo3.pojo.Roll;
import com.example.demo3.pojo.Userroll;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface UserrollMapper {

    /**
     * 根据用户id获取我的所有优惠卷
     */
    @Select("select * from roll where number in (select number from userroll where username=#{username})")
    ArrayList<Roll> getallrollbyusername(@Param("username") String username);//传入参数，返回sql查询后的数据

    /**
     * 获取当前订单可使用的所有优惠卷
     */
    @Select("select * from roll where state=1 and conditions<=#{conditions} and number in (select number from userroll where username=#{username})")
    ArrayList<Roll> getuserableroll(@Param("username") String username,@Param("number") String number,@Param("conditions") double conditions);

    /**
     * 修改优惠卷状态为已使用
     */
    @Update("update userroll" +
            " set state = 2" +
            " where username=#{username} and number=#{number}")
    int alterrolltoalreadyuse(@Param("username") String username,@Param("number") String number);

    /**
     * 修改优惠卷状态为未使用
     */
    @Update("update userroll" +
            " set state=1" +
            " where number=#{number}")
    int alterrolltonouse(@Param("username") String username,@Param("number") String number);


    /**
     * 添加优惠卷ok
     */
    @Insert("insert into userroll(username,number,state) " +
            "values(#{username},#{number},#{state});")
    void addoneuserroll(Userroll userroll);

    /**
     * 根据用户名和优惠卷兑换码获取我的唯一优惠卷
     */
    @Select("select * from userroll where username=#{username} and number=#{number}")
    Userroll getonerollbyusernameandnumber(@Param("username") String username,@Param("number") String number );
}
