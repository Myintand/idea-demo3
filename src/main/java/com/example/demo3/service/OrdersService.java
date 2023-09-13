package com.example.demo3.service;

import com.example.demo3.pojo.Orders;
import java.util.ArrayList;

public interface OrdersService
{

    ArrayList<Orders> getmyorders(String username);
    //删除
    int delete(String ussername,String storeusername,String time);

    //添加订单
    int addorders(Orders orders);

    //修改订单状态
    int evaluate(String username, String storeusername, String time,String state);

    //根据店铺用户名获取商品信息
    ArrayList<Orders> getmyacceptorders(String storeusername);

    //完成订单
    int finish(String username, String storeusername, String time);

    //查找订单
    Orders getoneorders(String username, String storeusername, String time);

}
