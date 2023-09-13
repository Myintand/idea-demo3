package com.example.demo3.service;

import com.example.demo3.pojo.Store;

import java.sql.SQLException;
import java.util.ArrayList;

//接口类，这个类是声明所有有关数据库的方法用的，我们就把有关数据库查询的函数在这声明，并且在UserServiceimpl类中实现
public interface StoreService {

    //获取所有商家，返回商家链表ok
    ArrayList<Store> getStoreList();

    //查找部分商家
    ArrayList<Store> findStoreList(String keyword);

    //根据商家用户名获取所有信息
    Store getonestore(String username);

    //修改商家信息
    int modify(Store store);

    //添加商家
    int add(Store store);

    //添加分数
    int addscore(String username, double score);

    //添加销量
    int salenumberaddone(String username);
}

