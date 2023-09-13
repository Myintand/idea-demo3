package com.example.demo3.service;


import com.example.demo3.pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;

//接口类，这个类是声明所有有关数据库的方法用的，我们就把有关数据库查询的函数在这声明，并且在UserServiceimpl类中实现
public interface UserService {

    //忘记密码ok
    User getoneuserbyusername(String username) throws SQLException;

    //用户注册
    void add(User user) throws SQLException;

    int modify(User user);

    //获取所有用户，返回用户链表ok
    ArrayList<User> getUserList();

    //获取单个用户信息ok
    User getoneuser(String username, String password);


    //修改用户密码
    int modifypassword(String password,String username);

    //扣款
    int reducemymoney(String username, double price);

    //收入
    int addmymoney(String username, double price);

    //充值
    int recharge(String username, double number);

}
