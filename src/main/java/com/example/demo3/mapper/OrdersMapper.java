package com.example.demo3.mapper;

import com.example.demo3.pojo.Orders;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

//二、mapper层
//同类： mapper层 = dao层
//作用： 对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的
@Mapper
public interface OrdersMapper
{
    /**
     * 查找我的进行中订单okk
     */
    @Select("select * from orders where username = #{username} and state!='已完成'")
    ArrayList<Orders> getmyorders(@Param("username") String username);//传入参数，返回sql查询后的数据

    /**
     * 设置订单状态状态okk
     */
    @Update("update orders set state=#{state} where username=#{username} and storeusername=#{storeusername} and time=#{time}")
    int evaluate(@Param("username") String username,@Param("storeusername") String storeusername,@Param("time") String time,@Param("state") String state);

    /**
     * 删除订单okk
     */
    @Delete("DELETE FROM orders WHERE username = #{username} AND storeusername = #{storeusername} AND time=#{time};")
    int delete(@Param("username") String username,@Param("storeusername") String storeusername,@Param("time") String time);


    /**
     * 查找订单
     */
    @Select("select * from orders WHERE username = #{username} AND storeusername = #{storeusername} AND time=#{time};")
    Orders getoneorders(@Param("username") String username,@Param("storeusername") String storeusername,@Param("time") String time);

    /**
     * 添加订单okk
     */
    @Insert("insert into orders(username,tel,content,price,address,storeusername,storename,picture,introduce,time,state,theroll) " +
            "values(#{username},#{tel},#{content},#{price},#{address},#{storeusername},#{storename},#{picture},#{introduce},#{time},#{state},#{theroll});")
    int addorders(Orders orders);


    /**
     * 获取店铺的所有接单okk
     */
    @Select("select * from orders where storeusername = #{storeusername} and state='取消订单'")
    ArrayList<Orders> getmyacceptorders(@Param("storeusername") String storeusername);

    /**
     * 完成订单okk
     */
    @Update("update orders set state='不评价' where username=#{username} and storeusername=#{storeusername} and time=#{time}")
    int finish(@Param("username") String username,@Param("storeusername") String storeusername,@Param("time") String time);

//
//    /**
//     * 修改商品信息ok
//     */
//    @Update("update product" +
//            " set productname=#{product.productname},price=#{product.price},introduce=#{product.introduce}" +
//            " where username=#{product.username} and productname=#{lastproductname}")
//    int modify(@Param("product") Product product, @Param("lastproductname") String lastproductname);



}