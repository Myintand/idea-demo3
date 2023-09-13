package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders
{
    //用户名（类的一个对象对应表的一列）
    String username;
    //电话号码
    String tel;
    //购买内容
    String content;
    //价格
    double price;
    //地址
    String address;
    //商户用户名
    String storeusername;
    //商户店铺名
    String storename;
    //商户头像图片
    String picture;
    //简介
    String introduce;
    //下单时间
    String time;
    //状态
    String state;
    //该单使用的优惠卷编号
    String theroll;
}

