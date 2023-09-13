package com.example.demo3.service;

import com.example.demo3.mapper.UserMapper;
import com.example.demo3.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

//这个类是实现接口里面的所有方法用的，当servlet中要使用数据库数据时，先new一个接口类对象UserService userService,然后用对象名称+'.'+函数名的形式调用这边的函数
//这边函数的具体数据库内部的实现函数在userDao里面，这边的函数将实现排错和跳转功能
//这边返回的一般都是类对象，就是数据库查询返回的对象
@Service
public class UserServiceImpl implements UserService
{

    @Resource
    UserMapper mapper;//数据库操作对象


    /**
     * 登陆：根据用户名和密码得到一个用户数据ok
     */
    @Override
    public User getoneuser(String username, String password)
    {
        return mapper.getoneuser(username,password);
    }
    /**
     * 所有用户查找ok
     */
    @Override
    public ArrayList<User> getUserList()
    {
        return mapper.getUserList();
    }
    /**
     * 忘记密码的用户查询(获取单个用户所有信息)ok
     */
    @Override
    public User getoneuserbyusername(String username)
    {
        return mapper.getoneuserbyusername(username);
    }
    /**
     * 用户注册
     */
    @Override
    public void add(User user)
    {
        mapper.add(user);
    }
    /**
     * 更新用户信息（所有）ok
     */
    @Override
    public int modify(User user)
    {
        return mapper.modify(user);
    }

    /**
     * 更新用户密码
     */
    @Override
    public int modifypassword(String password,String username)
    {
        return mapper.modifypassword(password,username);
    }

    @Override
    public int reducemymoney(String username, double price) {
        return mapper.reducemymoney(username,price);
    }

    @Override
    public int addmymoney(String username, double price) {
        return mapper.addmymoney(username,price);
    }

    @Override
    public int recharge(String username, double number) {
        return mapper.recharge(username,number);
    }


}
