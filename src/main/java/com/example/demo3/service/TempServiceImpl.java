package com.example.demo3.service;

import com.example.demo3.mapper.TempMapper;
import com.example.demo3.mapper.UserMapper;
import com.example.demo3.pojo.Temp;
import com.example.demo3.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

//这个类是实现接口里面的所有方法用的，当servlet中要使用数据库数据时，先new一个接口类对象UserService userService,然后用对象名称+'.'+函数名的形式调用这边的函数
//这边函数的具体数据库内部的实现函数在userDao里面，这边的函数将实现排错和跳转功能
//这边返回的一般都是类对象，就是数据库查询返回的对象
@Service
public class TempServiceImpl implements TempService
{

    @Resource
    TempMapper mapper;//数据库操作对象


    @Override
    public int add(Temp temp) {
        return mapper.add(temp);
    }

    @Override
    public int delete(String username, String storeusername, String time) {
        return mapper.delete(username, storeusername, time);
    }

    @Override
    public Temp get(String username, String storeusername, String time) {
        return mapper.get(username,storeusername,time);
    }
}
