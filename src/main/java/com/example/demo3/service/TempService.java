package com.example.demo3.service;


import com.example.demo3.pojo.Temp;
import com.example.demo3.pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;

//接口类，这个类是声明所有有关数据库的方法用的，我们就把有关数据库查询的函数在这声明，并且在UserServiceimpl类中实现
public interface TempService {

    //增
    int add(Temp temp);

    //删
    int delete(String username,String storeusername,String time);

    //查
    Temp get(String username,String storeusername,String time);
}
