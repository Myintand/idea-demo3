package com.example.demo3.service;

import com.example.demo3.mapper.OrdersMapper;
import com.example.demo3.pojo.Orders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

//这个类是实现接口里面的所有方法用的，当servlet中要使用数据库数据时，先new一个接口类对象UserService userService,然后用对象名称+'.'+函数名的形式调用这边的函数
//这边函数的具体数据库内部的实现函数在userDao里面，这边的函数将实现排错和跳转功能
//这边返回的一般都是类对象，就是数据库查询返回的对象
@Service
public class OrdersServiceImpl implements OrdersService
{

    @Resource
    OrdersMapper mapper;//数据库操作对象

    /**
     * 商品查找ok
     */
    @Override
    public ArrayList<Orders> getmyorders(String username)
    {
        return mapper.getmyorders(username);
    }

//    /**
//     * 商品查找ok
//     */
//    @Override
//    public Product findProductList(String username,String productname)
//    {
//        return mapper.findProductList(username,productname);
//    }
//
//
    /**
     * 删除订单ok
     */
    @Override
    public int delete(String username, String storeusername,String time)
    {
        return mapper.delete(username,storeusername,time);
    }
//
    /**
     * 添加订单ok
     */
    @Override
    public int addorders(Orders orders)
    {
        return mapper.addorders(orders);
    }

    /**
     * 修改订单状态
     */
    @Override
    public int evaluate(String username, String storeusername, String time,String state)
    {
        return mapper.evaluate(username,storeusername,time,state);
    }

    /**
     * 根据店铺用户名获取订单
     */
    @Override
    public ArrayList<Orders> getmyacceptorders(String storeusername) {
        return mapper.getmyacceptorders(storeusername);
    }


    /**
     * 完成订单
     */
    @Override
    public int finish(String username, String storeusername, String time) {
        return mapper.finish(username,storeusername,time);
    }


    /**
     * 查找订单
     */
    @Override
    public Orders getoneorders(String username, String storeusername, String time) {
        return mapper.getoneorders(username,storeusername,time);
    }


}
