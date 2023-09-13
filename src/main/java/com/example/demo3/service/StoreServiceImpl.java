package com.example.demo3.service;

import com.example.demo3.mapper.StoreMapper;
import com.example.demo3.pojo.Store;
import com.example.demo3.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

//这个类是实现接口里面的所有方法用的，当servlet中要使用数据库数据时，先new一个接口类对象UserService userService,然后用对象名称+'.'+函数名的形式调用这边的函数
//这边函数的具体数据库内部的实现函数在userDao里面，这边的函数将实现排错和跳转功能
//这边返回的一般都是类对象，就是数据库查询返回的对象
@Service
public class StoreServiceImpl implements StoreService
{

    @Resource
    StoreMapper mapper;//数据库操作对象


    /**
     * 所有用户查找ok
     */
    @Override
    public ArrayList<Store> getStoreList()
    {
        return mapper.getStoreList();
    }

    /**
     * 查找用户ok
     */
    @Override
    public ArrayList<Store> findStoreList(String keyword)
    {
        return mapper.findStoreList(keyword);
    }

    /**
     * 注册商家ok
     */
    @Override
    public int add(Store store)
    {
        return mapper.add(store);
    }

    /**
     * 评价
     */
    @Override
    public int addscore(String username, double score)
    {
        return mapper.addscore(username,score);
    }

    /**
     * 销量+1
     */
    @Override
    public int salenumberaddone(String username) {
        return mapper.salenumberaddone(username);
    }

    /**
     * 查找单个用户ok
     */
    @Override
    public Store getonestore(String username)
    {
        return mapper.getonestore(username);
    }

    /**
     * 更新商家信息ok
     */
    @Override
    public int modify(Store store)
    {
        return mapper.modify(store);
    }

}
