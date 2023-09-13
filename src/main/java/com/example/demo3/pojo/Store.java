package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Store
{
    //用户名（类的一个对象对应表的一列）
    String username;
    //店名
    String storename;
    //起送
    int beginning;
    //简介
    String introduce;
    //评分
    double score;//默认0
    //销售量
    int number;//默认0
    //评价人数
    int salenumber;//默认0
    //图片
    String picture;
    //店铺经纬度地址
    String storelocation;
}

